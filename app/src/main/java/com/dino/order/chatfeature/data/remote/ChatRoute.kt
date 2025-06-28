package com.dino.order.chatfeature.data.remote

import com.dino.order.corefeature.data.remote.BaseRoute

sealed class ChatRoute(url: String) : BaseRoute(url){
    object GetInbox : ChatRoute("api/auth/inbox")
}