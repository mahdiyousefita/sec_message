package com.dino.order.corefeature.data.remote

/**
 * A sealed class representing core routes in the application.
 * Each route is associated with a specific URL.
 *
 * @param url The URL associated with the route.
 */
sealed class CoreRoute(url: String) : BaseRoute(url) {
    /**
     * Represents the route for retrieving a token.
     */
    object GetToken : CoreRoute("api/refreshToken")

}