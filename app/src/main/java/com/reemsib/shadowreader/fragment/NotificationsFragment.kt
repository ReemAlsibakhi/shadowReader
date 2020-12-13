package com.reemsib.shadowreader.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.orhanobut.hawk.Hawk
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.NotificationAdapter
import com.reemsib.shadowreader.adapter.SchoolAdapter
import com.reemsib.shadowreader.model.Notification
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.BaseActivity
import com.reemsib.shadowreader.utils.Constants
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.tv_mobile
import kotlinx.android.synthetic.main.fragment_notifications.tv_name
import kotlinx.android.synthetic.main.fragment_notifications.tv_school
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class NotificationsFragment : Fragment() {
    private lateinit var manager: PreferencesManager
    var notificatonsList=ArrayList<Notification>()
    var mNotifiAdapter: NotificationAdapter?=null
    private lateinit var mNotFound:TextView;
    lateinit var myDialog: AlertDialog
    companion object{
        public val TAG = "NotificationsFragment"

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        mNotFound= root.findViewById<TextView>(R.id.not_found) as TextView
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager= PreferencesManager(requireContext())
        Hawk.init(requireContext()).build();
        myDialog= BaseActivity.loading(requireContext())
        if (manager.isLoggedIn){
            tv_name.text=manager.getUser().user.name
            tv_mobile.text=manager.getUser().user.mobile
            if (manager.getUser().user.school!=null){
             tv_school.text=manager.getUser().user.school.title
          }
            getNotification()
            if (Hawk.contains(Constants.NOTIFICATIONS)){
                mNotifiAdapter=NotificationAdapter(requireActivity(), Hawk.get(Constants.NOTIFICATIONS))
                buildRecyNotification()

            }
        }else{
            not_found.visibility=View.VISIBLE
        }
       Log.e("access_token ", PreferencesManager(requireActivity()).gettAccessToken()+"")
    }

    private fun getNotification() {
        myDialog.show()
        val stringRequest = object : StringRequest(Method.GET,
            URLs.URL_GET_NOTIFICATION,
            Response.Listener { response ->
                myDialog.hide()
                try {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("status")) {
                        val jsonArray = obj.getJSONArray("notifications")
                        for (i in 0 until jsonArray.length()) {

                            val jsObj = jsonArray.getJSONObject(i)
                            val mJson = JsonParser().parse(jsObj.toString())
                            val noti: Notification = Gson().fromJson<Any>(mJson, Notification::class.java) as Notification
                            notificatonsList.add(noti)
                        }
                        if (isAdded && notificatonsList.isNotEmpty()){
                            mNotFound.visibility = View.GONE
                            Hawk.put(Constants.NOTIFICATIONS, notificatonsList)
                            mNotifiAdapter = NotificationAdapter(requireActivity(), notificatonsList)
                            buildRecyNotification()
                        }else{
                            mNotFound.visibility = View.VISIBLE
                        } }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                myDialog.hide()
               // Toast.makeText(requireContext(), getString(R.string.connect_internet), Toast.LENGTH_SHORT).show()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["accept"] = "application/json"
                map["Accept-Language"] = "en"
                map["Authorization"]="Bearer " + PreferencesManager(requireActivity()).gettAccessToken()
                return map
            }


        }
        MySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }
    private fun buildRecyNotification() {
        rv_notify.adapter=mNotifiAdapter
        rv_notify.layoutManager=LinearLayoutManager(requireContext())
        mNotifiAdapter!!.setOnItemClickListener(object : NotificationAdapter.OnItemClickListener {
            override fun onClicked(
                clickedItemPosition: Int, id: String, rate: String, pos: String, neg: String
            ) {
                showPopUpWindow(rate, pos, neg)
            }

        })

    }
    @SuppressLint("InflateParams", "ClickableViewAccessibility", "SetTextI18n")
    private fun showPopUpWindow(rate: String, pos: String, neg: String) {

        val factory = LayoutInflater.from(requireContext())
        val mDialogView = factory.inflate(R.layout.custom_dialog_rate, null)
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(mDialogView)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))


        var rate0= mDialogView.findViewById<RatingBar>(R.id.rating) as RatingBar
        var rateNum= mDialogView.findViewById<TextView>(R.id.tv_num) as TextView
        var posirive= mDialogView.findViewById<TextView>(R.id.tv_positive) as TextView
        var negative= mDialogView.findViewById<TextView>(R.id.tv_negatives) as TextView
        rate0!!.rating =rate.toFloat()
        rateNum.text= "($rate)"
        posirive.text=pos
        negative.text=neg

        mDialogView.findViewById<View>(R.id.btn_ok).setOnClickListener { //your business logic
            dialog.dismiss()
        }
        dialog.show()
    }

}