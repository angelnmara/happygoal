package com.example.happygoaldemo.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happygoaldemo.R
import com.example.happygoaldemo.data.TestRepository

class TestViewModel(private val testRepository: TestRepository):ViewModel(){
    private val _testForm = MutableLiveData<TestFormState>()
    val testFormState: LiveData<TestFormState> = _testForm

    fun txtEmocionChanged(txtEmocion: String){
        if(!isEmocionValid(txtEmocion)){
            _testForm.value= TestFormState(emocionError = R.string.informacionEmocion)
        }else{
            _testForm.value= TestFormState(isDataValid = true)
        }
    }
    private fun isEmocionValid(txtEmocion: String): Boolean{
        return txtEmocion.length>15
    }
}