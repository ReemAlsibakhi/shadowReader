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
import androidx.fragment.app.Fragment
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.orhanobut.hawk.Hawk
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.MainActivity
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_register.*
import org.json.JSONException
import org.json.JSONObject


class RegisterFragment : Fragment(),View.OnClickListener {

    private val regexStr: String = "^05[0-9]{10,13}$"
    lateinit var dialog: ACProgressFlower
    var schoolId: String?= ""
    private var customDialogCountry = CustomDialogSchoolFragment()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        tv_skip.setOnClickListener(this)
//        btn_register.setOnClickListener(this)
//        relative_school.setOnClickListener(this)
//        progressDialog()
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_skip.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        relative_school.setOnClickListener(this)
        progressDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_register, container, false)
//        v.tv_skip.setOnClickListener(this)
//        v.btn_register.setOnClickListener(this)
//        v.linear_school.setOnClickListener(this)
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
        val mobile=et_mobile.text.toString()
        val school=et_school.text.toString()
        if(!validateForm(username, mobile, school)){
            return
        }

       showDialog()

        val stringRequest = object : StringRequest(Request.Method.POST,
            URLs.URL_REGISTER,
            Response.Listener { response ->
              hideDialog()
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("status")) {

                        val userJson = obj.getJSONObject("user")
                        val schoolObj =userJson.getJSONObject("school")
                        val schoolName =schoolObj.getString("title")
                        val token = obj.getString("token")

                        PreferencesManager(requireContext()).setLogin(true)
                        PreferencesManager(requireContext()).userLogin(
                            User(
                                userJson.getInt("id"),
                                userJson.getString("name"),
                                userJson.getString(
                                    "mobile"
                                ),
                                token,
                                schoolName)
                        )

                        Toast.makeText(
                            requireContext(),
                            getString(R.string.register_success),
                            Toast.LENGTH_SHORT
                        ).show()

                        //  Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
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
               hideDialog()
                //  Toast.makeText(applicationContext, R.string.failed_internet, Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = username
                params["fcm_token"] = "asd"
                params["mobile"] = mobile
                params["device_type"] = "android"
                params["school_id"] = schoolId.toString()
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
            mobile == regexStr -> {
                et_mobile.error=getString(R.string.enter_valid_mobile)
                et_mobile.requestFocus()
                valid = false

            }
            schoolId!!.isEmpty()->{
                et_school.error=getString(R.string.enter_school)
                et_school.requestFocus()
                valid = false
            }


        }
        return valid
    }
    private fun progressDialog() {
        dialog = ACProgressFlower.Builder(requireContext())
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .fadeColor(Color.DKGRAY).build()
    }

    fun hideDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
    fun  showDialog(){
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
    fun showFragment(){
        if (customDialogCountry.dialog == null){
            customDialogCountry.setCustomDialogInterface(object :
                CustomDialogSchoolFragment.CountryCustomDialogInterface {
                override fun sendData(id: Int, schoolName: String) {
                    et_school.setText(schoolName)
                    schoolId = id.toString()
                    Toast.makeText(requireContext(), "$schoolName", Toast.LENGTH_LONG).show() } })
            customDialogCountry.show(requireActivity().supportFragmentManager, "CustomDialogSchoolFragment")

        }

    }

}