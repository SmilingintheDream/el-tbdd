package com.example.bttuan4_library.uy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bttuan4_library.data.FakeRepo
import com.example.bttuan4_library.model.Book
import com.example.bttuan4_library.model.Student

class LibraryViewModel : ViewModel() {
    private val _books = FakeRepo.books
    private val _students = mutableStateListOf<Student>().also {
        it.addAll(FakeRepo.students)
    }

    var selectedStudentIndex by mutableIntStateOf(0)
        private set

    val books: List<Book> get() = _books
    val students: List<Student> get() = _students

    val selectedStudent: Student
        get() = _students[selectedStudentIndex]

    fun changeStudentByName(name: String) {
        val idx = _students.indexOfFirst { it.name == name }
        if (idx >= 0) selectedStudentIndex = idx
    }

    fun selectStudent(index: Int) {
        if (index in _students.indices) selectedStudentIndex = index
    }

    fun toggleBorrow(bookId: Int, checked: Boolean) {
        val st = _students[selectedStudentIndex]
        if (checked) st.borrowedBookIds.add(bookId) else st.borrowedBookIds.remove(bookId)
        _students[selectedStudentIndex] = st.copy(borrowedBookIds = st.borrowedBookIds)
    }

    fun addFirstAvailableBook() {
        val st = _students[selectedStudentIndex]
        val first = _books.firstOrNull { it.id !in st.borrowedBookIds } ?: return
        st.borrowedBookIds.add(first.id)
        _students[selectedStudentIndex] = st.copy(borrowedBookIds = st.borrowedBookIds)
    }
}