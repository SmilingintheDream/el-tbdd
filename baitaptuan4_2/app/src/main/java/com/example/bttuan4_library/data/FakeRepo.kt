package com.example.bttuan4_library.data

import com.example.bttuan4_library.model.Book
import com.example.bttuan4_library.model.Student

object FakeRepo {
    val books = listOf(
        Book(1, "Sách 01"),
        Book(2, "Sách 02"),
        Book(3, "Sách 03"),
        Book(4, "Sách 04")
    )

    val students = mutableListOf(
        Student(1, "Nguyen Van A", borrowedBookIds = mutableSetOf(1,2)),
        Student(2, "Nguyen Thi B", borrowedBookIds = mutableSetOf(1)),
        Student(3, "Nguyen Van C", borrowedBookIds = mutableSetOf())
    )
}
