package com.dino.message.chatfeature.data.remote

import com.dino.message.corefeature.data.remote.BaseRoute

sealed class ChatRoute(url: String) : BaseRoute(url){
    object GetInbox : ChatRoute("api/auth/inbox")

    object SendMessage : ChatRoute("api/auth/send")

    data class GetReceiverPublicKey(val username: String) : ChatRoute("api/auth/public-key/$username")
}