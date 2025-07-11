package com.dino.message.chatfeature.presentation.viewmodel

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dino.message.DinoOrderApplication
import com.dino.message.chatfeature.domain.model.MessageEntity
import com.dino.message.chatfeature.domain.usecase.ClearMessageFromDBUseCase
import com.dino.message.chatfeature.domain.usecase.GetInboxUseCase
import com.dino.message.chatfeature.domain.usecase.GetMessageFromDBUseCase
import com.dino.message.chatfeature.domain.usecase.GetReceiverPublicKeyUseCase
import com.dino.message.chatfeature.domain.usecase.SaveMessageInDbUseCase
import com.dino.message.chatfeature.domain.usecase.SendMessageUseCase
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.corefeature.presentation.util.CryptoUtil
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.corefeature.presentation.util.decodePrivateKey
import com.dino.message.corefeature.presentation.util.decodePublicKey
import com.dino.message.corefeature.presentation.util.decryptMessage
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEventImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val spref: SPrefManager,
    private val getInboxUseCase: GetInboxUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getReceiverPublicKeyUseCase: GetReceiverPublicKeyUseCase,
    private val getMessageFromDBUseCase: GetMessageFromDBUseCase,
    private val saveMessageInDbUseCase: SaveMessageInDbUseCase,
    private val clearMessageFromDBUseCase: ClearMessageFromDBUseCase,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {


    private val chatId: String? = savedStateHandle.get<String>("chatId")!!

    private val receiverPublicKey = mutableStateOf<String?>(null)

    private val _messages = mutableStateListOf<MessageEntity>()
    val messages: List<MessageEntity> = _messages

    // State to indicate if a refresh is in progress
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var getInbox = false
    private var getPublicKey = false

    init {
        getReceiverPublicKey()

        viewModelScope.launch {
            while (isActive) {
                loadMessages()       // Load from DB initially
                getInbox()           // Get from server
                delay(2000) // 2 seconds
            }
        }
    }


    private fun getInbox() = viewModelScope.launch {
        getInboxUseCase.execute().collect { res ->
            when (res) {
                is Resource.Success -> {
                    res.data?.forEach { inbox ->
                        try {
                            val decryptedMsg = decryptMessage(
                                encryptedMessage = inbox.message,
                                encryptedAESKey = inbox.encrypted_key,
                                privateKey = decodePrivateKey(spref.getPrivateKey()!!)
                            )

                            val entity = MessageEntity(
                                senderOrReceiver = chatId!!,
                                content = decryptedMsg,
                                timestamp = inbox.timestamp
                            )
                            saveMessage(entity)
                        } catch (e: Exception) {
//                            Log.e("ChatViewModel", "Decrypt failed", e)
                        }
                    }
                    getInbox = false
                    showRefreshing()
                }

                is Resource.Loading -> {
                    getInbox = true
                    showRefreshing()
                }

                is Resource.Error -> {
                    getInbox = false
                    showRefreshing()
                }
            }
        }
    }

    private fun sendMessageRequest(to: String, message: String, encryptedKey: String) =
        viewModelScope.launch {
            sendMessageUseCase.execute(
                to = to,
                message = message,
                encryptedKey = encryptedKey
            ).collect { res ->
                when (res) {
                    is Resource.Success -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {
                    }
                }
            }
        }

    private fun getReceiverPublicKey() = viewModelScope.launch {
        getReceiverPublicKeyUseCase.execute(chatId!!).collect { res ->
            when (res) {
                is Resource.Success -> {
                    receiverPublicKey.value = res.data.public_key
                    getPublicKey = false
                    showRefreshing()
                }

                is Resource.Loading -> {
                    getPublicKey = true
                    showRefreshing()
                }

                is Resource.Error -> {
                    getPublicKey = false
                    showRefreshing()
                }
            }
        }
    }

    fun sendMessage(message: String) {
        if (receiverPublicKey.value != null) {
            val (encryptedKey, encryptedMessage) = CryptoUtil.encryptMessage(
                message = message,
                receiverPublicKey = decodePublicKey(receiverPublicKey.value!!)
            )

            sendMessageRequest(chatId!!, encryptedMessage, encryptedKey)

            // Save message locally as "sent"
            saveMessage(
                MessageEntity(
                    senderOrReceiver = getUsernameFromPrefs().toString(),
                    content = message,
                    timestamp = getCurrentTimestamp()
                )
            )
        } else {
            getReceiverPublicKey()
        }
    }

    fun loadMessages() = viewModelScope.launch {
        _messages.clear()
        getMessageFromDBUseCase.execute().collect {
            _messages.addAll(it)
//            Log.i("messages", "loadMessages: ${_messages}")
        }
    }

    fun saveMessage(message: MessageEntity) = viewModelScope.launch {
        saveMessageInDbUseCase.execute(message).collect {
            _messages.add(message)
        }
    }

    fun clearMessages() = viewModelScope.launch {
        clearMessageFromDBUseCase.execute().collect {
            _messages.clear()
        }
    }

    // get login username
    private fun getUsernameFromPrefs(): String? = spref.getUsername()

    // get iso time
    private fun getCurrentTimestamp(): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.time.Instant.now().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

    fun navigateUp() {
        navigateUp(viewModelScope)
    }

    private fun showRefreshing() {
        _isRefreshing.value = getPublicKey || getInbox
    }
}