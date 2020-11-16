package com.reemsib.shadowreader.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
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
import com.reemsib.shadowreader.utils.Constants
import com.reemsib.shadowreader.utils.URLs
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.custom_dialog_school_fragment.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CustomDialogSchoolFragment : DialogFragment() {

    private var mSchoolList = ArrayList<School>()
    private var mSchoolAdapter: SchoolAdapter? = null

    var mCustomDIa: CountryCustomDialogInterface? = null
    var avi_dialog:AVLoadingIndicatorView ?=null
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
            buildCountryRecy()
        }
        listenerEdit()
        getSchool()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.custom_dialog_school_fragment, container, false)

        avi_dialog=root.findViewById(R.id.avi) as (AVLoadingIndicatorView)
        mSearch=root.findViewById(R.id.et_search) as (EditText)

//        Hawk.init(requireContext()).build()
//
//        if(Hawk.contains(Constants.SCHOOL)){
//            mSchoolAdapter = SchoolAdapter(requireActivity(), Hawk.get(Constants.SCHOOL))
//            buildCountryRecy()
//        }
//        listenerEdit()
//        getSchool()

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
        val filteredList: ArrayList<School> = ArrayList()
        for (item: School in mSchoolList) {
            if (item.title.toLowerCase(Locale.getDefault()).contains(text.toLowerCase())) {
                filteredList.add(item)
            }
            mSchoolAdapter!!.filterList(filteredList)
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun getSchool() {
        avi_dialog!!.show()

        val stringRequest = object : StringRequest(Method.GET,
            URLs.URL_SCHOOLS,
            Response.Listener { response ->
                avi_dialog!!.hide()
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("status")) {

                        val jsonArray = obj.getJSONArray("items")
                        if (jsonArray.length() != null) {
                            for (i in 0 until jsonArray.length()) {

                                val jsObj = jsonArray.getJSONObject(i)
                                val mJson = JsonParser().parse(jsObj.toString())
                                val school =
                                    Gson().fromJson<Any>(mJson, School::class.java) as School

                                mSchoolList.add(school)
                            }
                            Hawk.put(Constants.SCHOOL, mSchoolList)
                            mSchoolAdapter = SchoolAdapter(requireActivity(), mSchoolList)
                            buildCountryRecy()

                            Toast.makeText(
                                requireContext(),
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()

                        }


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
                avi_dialog!!.hide()
                //   Toast.makeText(requireContext(), R.string.failed_internet, Toast.LENGTH_SHORT)
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
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