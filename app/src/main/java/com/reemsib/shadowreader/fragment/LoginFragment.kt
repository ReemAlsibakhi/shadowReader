package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.reemsib.shadowreader.activity.MainActivity
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONException
import org.json.JSONObject


class LoginFragment : Fragment(),View.OnClickListener {
    lateinit var dialog: ACProgressFlower


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_login, container, false)
        progressDialog()

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

        showDialog()

        val stringRequest = object : StringRequest(Request.Method.POST,
            URLs.URL_LOGIN,
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
                hideDialog()
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["mobile"] = mobile
                params["fcm_token"] = "sadasd"

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
}