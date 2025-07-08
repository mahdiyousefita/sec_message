package com.dino.order.chatfeature.data.remote

import com.dino.order.corefeature.data.remote.BaseRoute
import com.dino.order.mainpage.data.remote.MainRoute

sealed class ChatRoute(url: String) : BaseRoute(url){
    object GetInbox : ChatRoute("api/auth/inbox")

    object SendMessage : ChatRoute("api/auth/send")

    data class GetReceiverPublicKey(val username: String) : ChatRoute("api/auth/public-key/$username")
}