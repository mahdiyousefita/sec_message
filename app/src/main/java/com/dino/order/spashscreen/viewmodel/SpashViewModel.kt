package com.dino.order.spashscreen.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import com.dino.order.DinoOrderApplication
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
import com.dino.order.destinations.ChatScreenDestination
import com.dino.order.destinations.LoginScreenDestination
import com.dino.order.destinations.MainScreenDestination
import com.dino.order.destinations.RegisterScreenDestination
import javax.inject.Inject

@HiltViewModel
class SpashViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val sPrefManager: SPrefManager,
    ) : AndroidViewModel(app as Application),
HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {


    fun navigateToNextScreen() {
        val userLogIn = sPrefManager.getUserLogIn()

        if (userLogIn){
            navigateWithPopBackStackToDestination(
                direction = MainScreenDestination,
                scope = viewModelScope,
                level = PopBackStackLevel.ALL
            )
        } else {
            navigateWithPopBackStackToDestination(
                direction = RegisterScreenDestination,
                scope = viewModelScope,
                level = PopBackStackLevel.ALL
            )
        }

    }
}