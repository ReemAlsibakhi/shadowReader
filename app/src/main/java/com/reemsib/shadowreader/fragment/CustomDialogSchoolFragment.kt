package com.reemsib.shadowreader.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.orhanobut.hawk.Hawk
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.SchoolAdapter
import com.reemsib.shadowreader.model.School
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.utils.BaseActivity
import com.reemsib.shadowreader.utils.Constants
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.custom_dialog_school_fragment.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CustomDialogSchoolFragment : DialogFragment() {
    private var mSchoolList = ArrayList<School>()
    private var mSchoolAdapter: SchoolAdapter? = null
    lateinit var myDialog: AlertDialog
    var mCustomDIa: CountryCustomDialogInterface? = null
    var mSearch:EditText ?=null

    interface CountryCustomDialogInterface {
        fun sendData(id: Int, schoolName: String)
    }
    fun setCustomDialogInterface(customDI: CountryCustomDialogInterface) {
        mCustomDIa = customDI
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Hawk.init(requireContext()).build()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        rv_school.layoutManager = layoutManager

        if(Hawk.contains(Constants.SCHOOL)){
            mSchoolAdapter = SchoolAdapter(requireActivity(), Hawk.get(Constants.SCHOOL))
            buildCountryRecy() }
        getSchool()
        listenerEdit()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.custom_dialog_school_fragment, container, false)
        myDialog=BaseActivity.loading(requireContext())
        mSearch=root.findViewById(R.id.et_search) as (EditText)
        return root
    }

    private fun listenerEdit() {
        mSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun filter(text: String) {
        if (Hawk.contains(Constants.SCHOOL)) {
            val filteredList: ArrayList<School> = ArrayList()

            for (item: School in Hawk.get(Constants.SCHOOL) as ArrayList<School>) {
                if (item.title.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item)
                }
                mSchoolAdapter!!.filterList(filteredList)
            }
        } else {
            val filteredList1: ArrayList<School> = ArrayList()
            for (item: School in mSchoolList) {
                if (item.title.toLowerCase().contains(text.toLowerCase())) {
                    filteredList1.add(item)
                }
                mSchoolAdapter!!.filterList(filteredList1)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun getSchool() {
       myDialog.show()
       mSchoolList.clear()
        val stringRequest = object : StringRequest(Method.GET,
            URLs.URL_SCHOOLS,
            Response.Listener { response ->
                myDialog.hide()
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("status")) {

                        val jsonArray = obj.getJSONArray("items")
                            for (i in 0 until jsonArray.length()) {

                                val jsObj = jsonArray.getJSONObject(i)
                                val mJson = JsonParser().parse(jsObj.toString())
                                val school = Gson().fromJson<Any>(mJson, School::class.java) as School
                                mSchoolList.add(school)
                            }
                          mSchoolList.add(0,School(0,"Other"))

                        if (isAdded && mSchoolList.isNotEmpty()){
                                Hawk.put(Constants.SCHOOL, mSchoolList)
                                mSchoolAdapter = SchoolAdapter(requireActivity(), mSchoolList)
                                buildCountryRecy()
                            }
                      Log.e("message_success", obj.getString("message"))
                   //   Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()

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
                   Toast.makeText(requireContext(), R.string.connect_internet, Toast.LENGTH_SHORT)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["accept"]= "application/json"
                map["Accept-Language"]= "en"
                return map
            }
        }
        MySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }
    private fun buildCountryRecy() {
        rv_school.adapter = mSchoolAdapter
        mSchoolAdapter!!.setOnItemClickListener(object : SchoolAdapter.OnItemClickListener {
            override fun onClicked(clickedItemPosition: Int, id: Int, schoolName: String) {
                dialog!!.dismiss()
                if (mCustomDIa != null) {
                    mCustomDIa!!.sendData(id, schoolName)
                }
            }

        })
    }


}