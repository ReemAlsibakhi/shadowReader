package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.system.ErrnoException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.MainActivity
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.BaseActivity
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_register.*
import org.json.JSONException
import org.json.JSONObject


class RegisterFragment : Fragment(),View.OnClickListener {
    private var fcmToken: String? = null
    private val regexStr = "^05[0-9]{8}$".toRegex()
    private lateinit var myDialog: AlertDialog
    var schoolId: String? = ""
    private var customDialogCountry = CustomDialogSchoolFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    myDialog= BaseActivity.loading(requireContext())
    tv_skip.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        relative_school.setOnClickListener(this)
        fcmToken=PreferencesManager(requireContext()).gettFcmToken()
        Log.e("fcm_token",fcmToken!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_register, container, false)
        return v

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_skip -> {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
            R.id.btn_register -> {
                registerUser()
            }
            R.id.relative_school -> {
                showFragment()
            }

        }
    }

    private fun  registerUser() {

        val username=et_fullName.text.toString()
        val mobile=et_mobile.text.toString().trim()
        val school=et_school.text.toString()
        if(!validateForm(username, mobile, school)){
            return
        }
      myDialog.show()
        val stringRequest = object : StringRequest(Request.Method.POST, URLs.URL_REGISTER, Response.Listener { response ->
            Log.e("register",response)
          myDialog.hide()
                try {
                    val obj = JSONObject(response)
                    Log.e("register_obj",response)

                    if (obj.getBoolean("status")) {

                        val mJson = JsonParser().parse(obj.toString())
                        val stu = Gson().fromJson<Any>(mJson, User::class.java) as User
                        Log.e("token","${stu.token}")
                        PreferencesManager(requireContext()).setAccessToken(stu.token)
                        PreferencesManager(requireContext()).userLogin(stu)
                        PreferencesManager(requireContext()).setLogin(true)

                        Toast.makeText(requireContext(), getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                        Log.e("register",obj.getString("message"))
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    } else {
                        Toast.makeText(requireContext(), obj.getString("msg"), Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
             myDialog.hide()
                Toast.makeText(requireContext(), getString(R.string.connect_internet), Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = username
                params["fcm_token"] =fcmToken!!
                params["mobile"] = mobile
                params["device_type"] = "android"
                params["school_id"] = schoolId.toString()
               params["other_school"] = school
                return params
            }

            override fun getPriority(): Priority {
                return Priority.HIGH
            }

            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Accept"] = "application/json"
                map["Accept-Language"] = "en"
                return map
            }
        }
        MySingleton.getInstance(requireActivity()).addToRequestQueue(stringRequest)
        
    }

    private fun validateForm(username: String, mobile: String, school: String): Boolean {
        var valid = true
        when{
            username.isEmpty() -> {
                et_fullName.error = getString(R.string.enter_username)
                et_fullName.requestFocus()
                valid = false
            }
            username.length<6-> {
                et_fullName.error = getString(R.string.enter_username_valid)
                et_fullName.requestFocus()
                valid = false
            }
            mobile.isEmpty() -> {
                et_mobile.error=getString(R.string.enter_mobile)
                et_mobile.requestFocus()
                valid = false


            }
            !mobile.matches(regexStr) -> {
                et_mobile.error=getString(R.string.enter_valid_mobile)
                et_mobile.requestFocus()
                valid = false

            }
             school.isEmpty()->{
                et_school.error=getString(R.string.enter_school)
                et_school.requestFocus()
                valid = false
            }
//            schoolId!!.isEmpty() || school.isEmpty()->{
//                et_school.error=getString(R.string.enter_school)
//                et_school.requestFocus()
//                valid = false
//            }


        }
        return valid
    }
    fun showFragment(){
        if (customDialogCountry.dialog == null){
            customDialogCountry.setCustomDialogInterface(object :
                CustomDialogSchoolFragment.CountryCustomDialogInterface {
                override fun sendData(id: Int, schoolName: String) {
                    if(id==0){
                        et_school.setText("")

                        et_school.isEnabled=true

                        Log.e("school","$id + $schoolName")
                    }else{
                        et_school.setText(schoolName)
                        schoolId = id.toString()
                    }
                }
            })
            customDialogCountry.show(requireActivity().supportFragmentManager, "CustomDialogSchoolFragment")

        }

    }

}