
package com.example.compose.quizapp.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.quizapp.Screen
import com.example.compose.quizapp.Screen.SignIn
import com.example.compose.quizapp.Screen.Survey
import com.example.compose.quizapp.util.Event

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    /**
     * Consider all sign ups successful
     */
    fun signUp(email: String, password: String) {
        userRepository.signUp(email, password)
        _navigateTo.value = Event(Survey)
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Survey)
    }

    fun signIn() {
        _navigateTo.value = Event(SignIn)
    }
}

class SignUpViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
