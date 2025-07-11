package com.dino.message.authfeature.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dino.message.DinoOrderApplication
import com.dino.message.authfeature.domain.usecase.RegisterUseCase
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.corefeature.presentation.util.RSAUtil
import com.dino.message.corefeature.presentation.util.Resource
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.message.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val registerUseCase: RegisterUseCase,
    private val sPrefManager: SPrefManager,
    ) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {

    val userName = mutableStateOf("")
    val password = mutableStateOf("")

    val registerLoadingState = mutableStateOf(false)


    private fun register(username: String, pass: String, publicKey: String) = viewModelScope.launch {
        registerUseCase.execute(
            username,
            pass,
            publicKey
        ).collect { res ->
            when(res){
                is Resource.Success -> {
                    registerLoadingState.value = false
                    navigateToLoginScreen()
                }
                is Resource.Loading -> {
                    registerLoadingState.value = true

                }
                is Resource.Error -> {
                    registerLoadingState.value = false
                }
            }
        }
    }


    fun onClickBtnRegister(){
        val keyPair = RSAUtil.generateKeyPair()
        val publicKeyBase64 = RSAUtil.getPublicKeyBase64(keyPair.public)
        val privateKeyBase64 = RSAUtil.getPrivateKeyBase64(keyPair.private)
        sPrefManager.savePublicKey(publicKeyBase64)
        sPrefManager.savePrivateKey(privateKeyBase64)
        register(
            userName.value,
            password.value,
            publicKeyBase64
        )
    }

    fun navigateToLoginScreen(){
        navigateToDestination(LoginScreenDestination, viewModelScope)
    }
}