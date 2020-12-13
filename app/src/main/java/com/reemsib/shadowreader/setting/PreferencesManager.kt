package com.reemsib.shadowreader.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import com.reemsib.shadowreader.activity.LoginActivity
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.utils.Constants

class PreferencesManager(context: Context) {

    private val preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var ctx: Context? = null
 //   private var token:String ?=null

    init {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        editor = preferences.edit()
        ctx=context
        Hawk.init(ctx).build()
    }
    fun isFirstRun() = preferences.getBoolean(FIRST_TIME, true)
    //this method will checker whether user is already logged in or not
    val isLoggedIn = preferences.getBoolean(Constants.ISLoggedIn, false)
    fun setLogin(login: Boolean) {
        editor.putBoolean(Constants.ISLoggedIn, login).commit()
      //  editor.commit()
    }
    fun setFcmToken(fcm:String){
        editor.putString(Constants.FCM_TOKEN, fcm).commit()
        editor.commit()
    }
    fun gettFcmToken():String{
       return preferences.getString(Constants.FCM_TOKEN,"null")!!
    }
    fun setAccessToken(fcm:String){
        editor.putString(Constants.TOKEN, fcm).commit()
        editor.commit()
    }
    fun gettAccessToken():String{
       return preferences.getString(Constants.TOKEN,"null")!!
    }
    fun Logout() {
        Hawk.deleteAll()
        setLogin(false)
        val intent = Intent(ctx, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx!!.startActivity(intent)
    }
    fun setFirstRun() {
        editor.putBoolean(FIRST_TIME, false).commit()
        editor.commit()
    }

    //this method will store the user data in shared preferences
    fun userLogin(user: User) {
        val gson = Gson()
        val json = gson.toJson(user)
        editor.putString("MyObject", json)
        editor.commit()
    }

    //this method will give the logged in user
   fun getUser():User{
        val gson = Gson()
        val json: String = preferences.getString("MyObject", "a")!!
        val obj: User = gson.fromJson(json, User::class.java)
        return obj
        }

     fun isNightModeEnabled(): Boolean {
        return preferences.getBoolean(Constants.NIGHT_MODE, false)
    }
    fun isNotificationEnabled(): Boolean {
        return preferences.getBoolean(Constants.NOTIFICATION, false)
    }

     fun setIsNightModeEnabled(state: Boolean) {
        editor.putBoolean(Constants.NIGHT_MODE, state)
        editor.apply()
    }
    fun setIsNotificationEnabled(state: Boolean) {
        editor.putBoolean(Constants.NOTIFICATION, state)
        editor.apply()
    }
    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_NAME = "configuration"
        private const val FIRST_TIME = "isFirstRun"
    }

}