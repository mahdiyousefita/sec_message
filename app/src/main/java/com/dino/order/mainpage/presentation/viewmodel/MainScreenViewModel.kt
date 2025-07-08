package com.dino.order.mainpage.presentation.viewmodel

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
import com.dino.order.DinoOrderApplication
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.order.mainpage.domain.model.MessageList
import com.dino.order.mainpage.domain.usecase.AddContactUseCase
import com.dino.order.mainpage.domain.usecase.GetContactsUseCase
import com.ramcosta.composedestinations.generated.destinations.ChatScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
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


    private val context: Context = app.applicationContext

    init {
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
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getContacts(username: String) = viewModelScope.launch {
        getContactsUseCase.execute(username).collect { res ->
            when(res){
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
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

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

}