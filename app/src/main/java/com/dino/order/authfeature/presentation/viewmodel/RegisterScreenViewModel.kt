package com.dino.order.authfeature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dino.order.DinoOrderApplication
import com.dino.order.authfeature.domain.usecase.RegisterUseCase
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.RSAUtil
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
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

    fun navigateToLoginScreen(){
        navigateToDestination(LoginScreenDestination, viewModelScope)
    }

    private fun register(username: String, pass: String, publicKey: String) = viewModelScope.launch {
        registerUseCase.execute(
            username,
            pass,
            publicKey
        ).collect { res ->
            when(res){
                is Resource.Success -> {
                    navigateToLoginScreen()
                }
                is Resource.Loading -> {
//                    Log.i("??????", "Loading: ")

                }
                is Resource.Error -> {
//                    Log.e("??????", "register: " + res.message)
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
}