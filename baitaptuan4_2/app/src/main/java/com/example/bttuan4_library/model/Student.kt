package com.example.bttuan4_library.model

data class Student(
    val id: Int,
    val name: String,
    val borrowedBookIds: MutableSet<Int> = mutableSetOf()
)