package com.dino.message.authfeature.data.remote

import com.dino.message.corefeature.data.remote.BaseRoute

/**
 * AuthRoute
 *
 * Sealed class that represents different routes related to authentication.
 *
 * @property url The URL associated with the route.
 */
sealed class AuthRoute(url: String) : BaseRoute(url) {
    /**
     * Login
     *
     * Object representing the login route.
     */
    object Login : AuthRoute("api/auth/login")

    /**
     * SignUp
     *
     * Object representing the sign-up route.
     */
    object Register : AuthRoute("api/auth/register")
}