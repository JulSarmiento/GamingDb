package com.julhdev.gamingdb.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Search

@Serializable
data class Detail (
    val id: Int
)
