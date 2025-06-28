package com.dino.order.chatfeature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dino.order.DinoOrderApplication
import com.dino.order.chatfeature.domain.usecase.GetInboxUseCase
import com.dino.order.corefeature.data.spref.SPrefManager
import com.dino.order.corefeature.presentation.util.Resource
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext app: Context,
    private val spref: SPrefManager,
    private val getInboxUseCase: GetInboxUseCase

) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {


    init {
        getInbox()
    }

    private fun getInbox() = viewModelScope.launch {
        getInboxUseCase.execute().collect { res ->
            when (res) {
                is Resource.Success -> {
                    Log.i("????", "getInbox: s")
                }

                is Resource.Loading -> {
                    Log.i("????", "getInbox: l")

                }

                is Resource.Error -> {
                    Log.e("????", "getInbox: e")

                }
            }
        }
    }
}