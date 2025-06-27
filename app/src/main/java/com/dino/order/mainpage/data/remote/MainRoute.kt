package com.dino.order.mainpage.data.remote

import com.dino.order.corefeature.data.remote.BaseRoute

sealed class MainRoute(url: String) : BaseRoute(url) {

    object AddContact : MainRoute("api/auth/contacts")

    data class GetContacts(val username: String) : MainRoute("api/auth/contacts/$username")
}