package com.dino.message.mainpage.data.remote

import com.dino.message.corefeature.data.remote.BaseRoute

sealed class MainRoute(url: String) : BaseRoute(url) {

    object AddContact : MainRoute("api/auth/contacts")

    data class GetContacts(val username: String) : MainRoute("api/auth/contacts/$username")
}