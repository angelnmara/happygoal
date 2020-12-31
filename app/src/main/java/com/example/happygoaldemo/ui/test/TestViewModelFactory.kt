package com.example.happygoaldemo.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happygoaldemo.data.LoginDataSource
import com.example.happygoaldemo.data.LoginRepository
import com.example.happygoaldemo.data.TestDataSource
import com.example.happygoaldemo.data.TestRepository

class TestViewModelFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(
                testRepository = TestRepository(
                    dataSource = TestDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}