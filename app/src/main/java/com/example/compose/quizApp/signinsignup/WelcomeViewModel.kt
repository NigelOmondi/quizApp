

package com.example.compose.quizapp.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.quizapp.Screen
import com.example.compose.quizapp.Screen.SignIn
import com.example.compose.quizapp.Screen.SignUp
import com.example.compose.quizapp.Screen.Survey
import com.example.compose.quizapp.util.Event

class WelcomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    fun handleContinue(email: String) {
        if (userRepository.isKnownUserEmail(email)) {
            _navigateTo.value = Event(SignIn)
        } else {
            _navigateTo.value = Event(SignUp)
        }
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Survey)
    }
}

class WelcomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
