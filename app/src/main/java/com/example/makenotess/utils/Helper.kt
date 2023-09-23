package com.example.makenotess.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception
import java.util.regex.Matcher

class Helper {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun hideKeyboard(view: View){
            try {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }catch (e: Exception){

            }
        }

        fun validateCredential( username:String,email:String,password:String,isLogin:Boolean):Pair<Boolean,String>{

            var result=Pair(true,"")

            if (!isLogin&&TextUtils.isEmpty(username)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                result=Pair(false,"please provide credentials")
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                result=Pair(false,"please provide valid email")
            }else if (password.length<=5){
                result=Pair(false,"password length should be greater than 5")
            }

            return result

        }
    }

}