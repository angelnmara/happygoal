package com.example.happygoaldemo.data.model

import java.util.*

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val token:String,
    val dateLogged:Date,
    val isLogged:Boolean
)