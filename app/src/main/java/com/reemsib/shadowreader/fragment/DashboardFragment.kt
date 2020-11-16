package com.reemsib.shadowreader.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.IntroSliderActivity
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.Constants
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.json.JSONException
import org.json.JSONObject


class DashboardFragment : Fragment() ,View.OnClickListener{

//    private val NIGHT_MODE = "night_mode"
//    private var mSharedPref: SharedPreferences? = null
   private lateinit var manager: PreferencesManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // init shared preferences
//        mSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        manager=PreferencesManager(requireContext())

        root.linear_logout.setOnClickListener(this)
        root.student_instruction.setOnClickListener(this)
        if (PreferencesManager(requireContext()).isLoggedIn){
            root.linear_logout.visibility=View.VISIBLE
        } else{
            root.linear_logout.visibility=View.GONE
        }

        root.switch_mode.isChecked = manager.isNightModeEnabled()


        root.switch_mode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (PreferencesManager(requireContext()).isNightModeEnabled()) {
                manager.setIsNightModeEnabled(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                manager.setIsNightModeEnabled(true)
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
            // Recreate activity
            recreate(requireActivity())
        })

        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.linear_logout -> {
                logout()
            }
            R.id.student_instruction->{
               val i=Intent(requireContext(),IntroSliderActivity::class.java)
                i.putExtra("flag","setting")
                startActivity(i)
            }
        }
    }

    private fun logout() {

        //CustomProgressDialog.getInstance(requireContext()).showDialog()

        val stringRequest = object : StringRequest(Method.POST, URLs.URL_LOGOUT, Response.Listener { response ->

            //   CustomProgressDialog.getInstance(requireContext()).hideDialog()

            try {
                val obj = JSONObject(response)

                if (obj.getBoolean("status")) {

                    PreferencesManager(requireContext()).Logout()

                    Toast.makeText(requireContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show()

                    // val intent = Intent(requireContext(), LoginActivity::class.java)
                    // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
                    // startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            Response.ErrorListener { error ->

                //      CustomProgressDialog.getInstance(requireContext()).hideDialog()

                Toast.makeText(requireContext(),error.message.toString(), Toast.LENGTH_SHORT).show()

            }) {


            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Accept"] = "application/json"
                map["Accept-Language"] = "en"
                map["Authorization"]="Bearer " + PreferencesManager(requireContext()).getUser().token
                return map
            }

        }

        MySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)

    }
//    private fun isNightModeEnabled(): Boolean {
//        return mSharedPref!!.getBoolean(NIGHT_MODE, false)
//    }
//    private fun setIsNightModeEnabled(state: Boolean) {
//        val mEditor = mSharedPref!!.edit()
//        mEditor.putBoolean(NIGHT_MODE, state)
//        mEditor.apply()
//    }
}
//        if (manager.isNightModeEnabled()) {
//            root.switch_mode.isChecked=true
//      //      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            root.switch_mode.isChecked=false
//        //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }