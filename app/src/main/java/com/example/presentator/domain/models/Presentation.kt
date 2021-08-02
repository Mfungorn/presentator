package com.example.presentator.domain.models

data class Presentation(
    val id: Int,
    val location: String,
    val startAtUtc: String,
    val title: String,
    val topics: List<String>
)