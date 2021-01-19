package com.example.happygoaldemo.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happygoaldemo.api.Repo

class VMFactory(private val repo: Repo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}