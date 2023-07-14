package com.example.module_3_lesson_5_hw_2_compose

data class AppUiState(
    val inputSeconds: String = "",
    val currentHours: String = "",
    val currentMinutes: String = "",
    val currentSeconds: String = "",
    val timerCompleted: Boolean = false
)
