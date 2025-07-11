package com.dino.message.mainpage.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import com.dino.message.DinoOrderApplication
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.message.mainpage.domain.model.MessageList
import com.dino.message.mainpage.domain.usecase.AddContactUseCase
import com.dino.message.mainpage.domain.usecase.GetContactsUseCase
import com.ramcosta.composedestinations.generated.destinations.ChatScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val addContactUseCase: AddContactUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val spref: SPrefManager,
) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {


    var isError = mutableStateOf(false)

    private val _isBottomSheetVisible = MutableStateFlow(false)
    val isBottomSheetVisible = _isBottomSheetVisible.asStateFlow()

    private val _addUserTextField = mutableStateOf("")
    val addUserTextField = _addUserTextField

    private var _contactList = mutableStateListOf<MessageList?>(null)
    val contactList = _contactList

    // State to indicate if a refresh is in progress
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var isGettingContacts = false

    private var isAddingContacts = false


    private val context: Context = app.applicationContext

    init {
        getContacts(
            spref.getUsername().toString()
        )
    }

    fun refresh() {
        getContacts(
            spref.getUsername().toString()
        )
    }


    private fun addContact(contact: String) = viewModelScope.launch {
        addContactUseCase.execute(contact).collect { res ->
            when (res) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    hideBottomSheet()
                    getContacts(
                        spref.getUsername().toString()
                    )
                    isAddingContacts = false
                }

                is Resource.Loading -> {
                    isAddingContacts = true
                }

                is Resource.Error -> {
                    Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show()
                    isAddingContacts = false
                }
            }
        }
    }

    private fun getContacts(username: String) = viewModelScope.launch {
        getContactsUseCase.execute(username).collect { res ->
            when (res) {
                is Resource.Success -> {
                    _contactList.clear()
                    _contactList.addAll(
                        res.data.map { name ->
                            MessageList(
                                name = name,
                                time = "00:00",
                                newMessage = false
                            )
                        }
                    )

                    isGettingContacts = false
                    showRefreshing()
                }

                is Resource.Loading -> {
                    isGettingContacts = true
                    showRefreshing()
                }

                is Resource.Error -> {
                    isGettingContacts = false
                    showRefreshing()
                }
            }
        }
    }


    // Check Internet Connection
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun navigateToProfileScreen() {
        navigateToDestination(ProfileScreenDestination, viewModelScope)
    }

    fun navigateToChatScreen(name: String) {
        navigateToDestination(ChatScreenDestination(name), viewModelScope)
    }

    fun showBottomSheet() {
        _isBottomSheetVisible.value = true
    }

    fun hideBottomSheet() {
        _isBottomSheetVisible.value = false
        _addUserTextField.value = ""
    }

    fun onAddNewUserBtnClicked() {
        addContact(
            _addUserTextField.value
        )
    }

    private fun showRefreshing() {
        _isRefreshing.value = isGettingContacts || isAddingContacts
    }
}