package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.orhanobut.hawk.Hawk
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.LessonsActivity
import com.reemsib.shadowreader.model.Student
import com.reemsib.shadowreader.model.User
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var manager: PreferencesManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        MobileAds.initialize(requireActivity()) {}
        val adRequest = AdRequest.Builder().build()
        root.adView.loadAd(adRequest)

        root.first_semester.setOnClickListener {
            val i= Intent(requireContext(),LessonsActivity::class.java)
            i.putExtra("id",1)
            startActivity(i)
        }
        root.second_semester.setOnClickListener {
           val i= Intent(requireContext(),LessonsActivity::class.java)
            i.putExtra("id",2)
            startActivity(i)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     manager=PreferencesManager(requireContext())
    //    getUserInfo()

      if (manager.isLoggedIn){
          getUserInfo()
          tv_name.text=manager.getUser().user.name
          tv_mobile.text=manager.getUser().user.mobile

          if (manager.getUser().user.school!=null)
              tv_school.text= manager.getUser().user.school.title
         }else{
         // tv_school.text=""
      }

    }

    private fun getUserInfo() {
        val stringRequest = object : StringRequest(
            Method.GET, URLs.URL_USER_INFO, Response.Listener { response ->
                Log.e("user_obj",response)

                try {
                val obj = JSONObject(response)

                if (obj.getBoolean("status")) {

                    val mJson = JsonParser().parse(obj.toString())
                    val stu = Gson().fromJson<Any>(mJson, User::class.java) as User
                     manager.userLogin(stu)


                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            Response.ErrorListener { error ->
         //       Toast.makeText(requireContext(), R.string.failed_internet, Toast.LENGTH_SHORT).show()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["accept"] = "application/json"
                map["Accept-Language"] = "en"
                map["Authorization"]="Bearer " + manager.gettAccessToken()

                return map
            }


        }
        MySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)

    }

}