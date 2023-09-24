package com.example.makenotess.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.MultiAutoCompleteTextView.Tokenizer
import com.example.makenotess.utils.Constants.PREFS_TOKEN_FILE
import com.example.makenotess.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//SharedPreference --> Ye ek local database hai jo kam ata hai locally data ko store krne ke liye
                 //Jab app Uninstall hoti hai to data locally delete ho jata hai
class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var pref=context.getSharedPreferences(PREFS_TOKEN_FILE,Context.MODE_PRIVATE)

    fun saveToken(token:String){

        val editor=pref.edit()
            editor.putString(USER_TOKEN,token)
            editor.apply()
    }

    fun getToken():String?{
         //token hai to token return karega ni to null value return karega
        return pref.getString(USER_TOKEN,null)
    }
}