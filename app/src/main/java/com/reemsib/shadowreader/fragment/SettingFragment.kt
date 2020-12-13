package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.IntroSliderActivity
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.BaseActivity
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_setting.view.*
import org.json.JSONException
import org.json.JSONObject


class SettingFragment : Fragment() ,View.OnClickListener{

   private lateinit var manager: PreferencesManager
   lateinit var myDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        manager=PreferencesManager(requireContext())
        myDialog=BaseActivity.loading(requireContext())
        if (manager.isLoggedIn){
            root.tv_name.text=manager.getUser().user.name
            root.tv_mobile.text=manager.getUser().user.mobile
            if (manager.getUser().user.school!=null){
                root.tv_school.text=manager.getUser().user.school.title
            }
        }

        root.linear_logout.setOnClickListener(this)
        root.student_instruction.setOnClickListener(this)
        root.student_practice.setOnClickListener(this)
       // root.shareIt.setOnClickListener(this)
        root.connect_us.setOnClickListener(this)

        if (PreferencesManager(requireContext()).isLoggedIn){
            root.linear_logout.visibility=View.VISIBLE
        } else{
            root.linear_logout.visibility=View.GONE
        }

        root.switch_mode.isChecked = manager.isNightModeEnabled()
        root.switch_notif.isChecked=manager.isNotificationEnabled()

        root.switch_mode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (PreferencesManager(requireContext()).isNightModeEnabled()) {
                manager.setIsNightModeEnabled(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                manager.setIsNightModeEnabled(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
            // Recreate activity
            //    recreate(requireActivity())
        })
        root.switch_notif.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (PreferencesManager(requireContext()).isNotificationEnabled()) {
                manager.setIsNotificationEnabled(false)
             //   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                manager.setIsNotificationEnabled(true)
            //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
            // Recreate activity
            //    recreate(requireActivity())
        })

        return root
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.linear_logout -> {
                logout()
            }
            R.id.shareIt -> {
                shareApp()
            }
            R.id.student_instruction -> {
                val i = Intent(requireContext(), IntroSliderActivity::class.java)
                i.putExtra("flag", "setting")
                startActivity(i)
            }
            R.id.student_practice -> {
                val uri: Uri =
                    Uri.parse("https://www.youtube.com/channel/UCLCL2dIFqmlimeDiSFmO-Bg")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.connect_us->{
            val uri: Uri =
                Uri.parse("https://www.facebook.com/Online-Kids-Club-514741062323214/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
              }
            }

    }

    private fun shareApp() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android Studio Pro")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=com.tutorial.personal.androidstudiopro "
        )
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun logout() {
        myDialog.show()
        val stringRequest = object : StringRequest(Method.POST,
            URLs.URL_LOGOUT,
            Response.Listener { response ->
                myDialog.hide()
                try {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("status")) {

                        PreferencesManager(requireContext()).Logout()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.logout_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
               myDialog.hide()
                Toast.makeText(requireContext(), getString(R.string.connect_internet), Toast.LENGTH_SHORT).show()

            }) {


            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Accept"] = "application/json"
                map["Accept-Language"] = "en"
                map["Authorization"]="Bearer " + PreferencesManager(requireContext()).gettAccessToken()
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