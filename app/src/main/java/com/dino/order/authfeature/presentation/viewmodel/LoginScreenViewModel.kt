package com.dino.order.authfeature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dino.order.DinoOrderApplication
import com.dino.order.authfeature.domain.usecase.LoginUseCase
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.order.destinations.MainScreenDestination
import com.dino.order.destinations.RegisterScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val sPrefManager: SPrefManager,
    private val loginUseCase: LoginUseCase,
    ) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication){

    val userName = mutableStateOf("")
    val password = mutableStateOf("")


    private fun login(username: String, pass: String) = viewModelScope.launch {
        loginUseCase.execute(
            username, pass
        ).collect { data ->
            when(data){
                is Resource.Success -> {
                    sPrefManager.setUserLogIn(true)
                    sPrefManager.setUsername(username)
                    sPrefManager.setToken(data.data.accessToken)
                    sPrefManager.setRefreshToken(data.data.refreshToken)
                    navigateToMainScreen()
                }
                is Resource.Loading -> {
//                    Log.i("??????", "login: loading")
                }
                is Resource.Error -> {
//                    Log.e("??????", "login: " + data.message.toString())
                }
            }
        }
    }

    fun onLoginBtnClicked(){
        login(userName.value, password.value)
    }

    fun navigateToRegisterScreen(){
        navigateToDestination(RegisterScreenDestination, viewModelScope)
    }

    fun navigateToMainScreen(){
        navigateWithPopBackStackToDestination(
            MainScreenDestination,
            viewModelScope,
            PopBackStackLevel.ALL
        )
    }
}