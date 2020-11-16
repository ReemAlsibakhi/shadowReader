package com.reemsib.shadowreader.utils

class URLs {
    companion object{

        const val ROOT_URL = "http://learnenglish2020.herokuapp.com/api/"

        const val URL_REGISTER =  ROOT_URL + "register"
        const val URL_LOGIN = ROOT_URL + "login"
        const val URL_LOGOUT = ROOT_URL + "logout"
        const val URL_USER_INFO = ROOT_URL + "getUserInfo"
        //const val URL_RESET_PASS = ROOT_URL + "reset-password/create"

        const val URL_SCHOOLS = ROOT_URL + "getSchools"
        const val URL_LESSONS = ROOT_URL + "getLessons/"
        const val URL_GET_VIDEOS = ROOT_URL + "getVideos/"

        const val URL_SEND_VOICE = ROOT_URL + "sendVoice"

    }
}