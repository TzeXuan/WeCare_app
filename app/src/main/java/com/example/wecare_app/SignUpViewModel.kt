package com.example.wecare_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    private var _email = MutableLiveData<String>()
    var email: LiveData<String> = _email

    fun saveEmail(newEmail: String){
        _email.value = newEmail
    }

    private var _password = MutableLiveData<String>()
    var password : LiveData<String> = _password

    fun savePassword(newPassword: String){
        _password.value = newPassword
    }

    private var _confirmPassword = MutableLiveData<String>()
    var confirmPassword : LiveData<String> = _confirmPassword

    fun saveConfirmPassword(newConfirmPassword : String){
        _confirmPassword.value = newConfirmPassword
    }

    private var _name = MutableLiveData<String>()
    var name : LiveData<String> = _name

    fun saveName(newName: String){
        _name.value = newName
    }

    private var _phoneNumber = MutableLiveData<String>()
    var phoneNumber : LiveData<String> = _phoneNumber

    fun savePhoneNumber(newPhoneNumber:String){
        _phoneNumber.value = newPhoneNumber
    }

    fun dataPhoneNumber() : String{
        return phoneNumber.value.toString()
    }

    private var _gender = MutableLiveData<String>()
    var gender : LiveData<String> = _gender

    fun saveGender(newGender:String){
        _gender.value = newGender
    }

    private var _caregiver = MutableLiveData<String>()
    var caregiver : LiveData<String> = _caregiver

    fun saveCaregiver(newCaregiver:String){
        _caregiver.value = newCaregiver
    }

}