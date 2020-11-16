package com.reemsib.shadowreader.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.reemsib.shadowreader.activity.LoginActivity
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.utils.Constants

class PreferencesManager(context: Context) {

    private val preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var ctx: Context? = null

    init {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        editor = preferences.edit()
        ctx=context
    }

    fun isFirstRun() = preferences.getBoolean(FIRST_TIME, true)

    //this method will checker whether user is already logged in or not
    val isLoggedIn = preferences.getBoolean(Constants.ISLoggedIn, false)

    fun setLogin(login: Boolean) {
        editor.putBoolean(Constants.ISLoggedIn, login).commit()
      //  editor.commit()
    }

    fun Logout() {
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
        editor.putInt(Constants.KEY_ID, user.id)
        editor.putString(Constants.USERNAME, user.name)
        editor.putString(Constants.MOBILE, user.mobile)
        editor.putString(Constants.SCHOOL, user.school)
        editor.putString(Constants.TOKEN, user.token)
        editor.apply()
    }

    //this method will give the logged in user
   fun getUser():User{
            return User(preferences.getInt(Constants.KEY_ID, -1), preferences.getString(Constants.USERNAME, null)!!,
                preferences.getString(Constants.MOBILE, null)!!,preferences.getString(Constants.TOKEN,null)!!,preferences.getString(Constants.SCHOOL,null)!!)
        }

     fun isNightModeEnabled(): Boolean {
        return preferences.getBoolean(Constants.NIGHT_MODE, false)
    }
     fun setIsNightModeEnabled(state: Boolean) {
        editor.putBoolean(Constants.NIGHT_MODE, state)
        editor.apply()
    }
    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_NAME = "configuration"
        private const val FIRST_TIME = "isFirstRun"
    }
}