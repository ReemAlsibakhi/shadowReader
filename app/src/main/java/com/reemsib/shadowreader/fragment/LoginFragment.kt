package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.reemsib.shadowreader.activity.MainActivity
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.BaseActivity
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONException
import org.json.JSONObject


class LoginFragment : Fragment(),View.OnClickListener {
    private lateinit var myDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_login, container, false)
        myDialog= BaseActivity.loading(requireContext())
        v.tv_skip.setOnClickListener(this)
        v.btn_login.setOnClickListener(this)
        return v
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
          R.id.tv_skip->{startActivity(Intent(requireContext(), MainActivity::class.java))}
          R.id.btn_login->{loginUser()}
        }
    }
    private fun  loginUser() {

        val mobile=et_mobile.text.toString()
        if(mobile.isEmpty()){
            et_mobile.error = getString(R.string.enter_mobile)
            et_mobile.requestFocus()
            return
        }
        myDialog.show()
        val stringRequest = object : StringRequest(Request.Method.POST,
            URLs.URL_LOGIN,
            Response.Listener { response ->
               myDialog.hide()
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("status")) {
                        val mJson = JsonParser().parse(obj.toString())
                        val stu = Gson().fromJson<Any>(mJson, User::class.java) as User
                        Log.e("student","${stu.token}")
                        PreferencesManager(requireContext()).setAccessToken(stu.token)
                        PreferencesManager(requireContext()).userLogin(stu)
                        PreferencesManager(requireContext()).setLogin(true)
                        Toast.makeText(requireContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    } else {
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
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
                params["mobile"] = mobile
                params["fcm_token"] = PreferencesManager(requireContext()).gettFcmToken()

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

}