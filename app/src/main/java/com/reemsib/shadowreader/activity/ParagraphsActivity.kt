package com.reemsib.shadowreader.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.r.foodproject.ui.model.DatabaseHelper
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.ParagraphAdapter
import com.reemsib.shadowreader.model.Paragraph
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.activity_paragraph.*
import kotlinx.android.synthetic.main.activity_paragraph.ic_back
import kotlinx.android.synthetic.main.activity_semester.*
import org.json.JSONException
import org.json.JSONObject

class ParagraphsActivity : AppCompatActivity() {
    var lessonId :Int ?= null
    var lessonName :String ?= null
    var paragList=ArrayList<Paragraph>()
    var paragAdapter:ParagraphAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paragraph)

        val db= DatabaseHelper(applicationContext)
        val i=intent
        if(i != null){
            lessonId=i.getIntExtra("id_lesson",-1)
            lessonName=i.getStringExtra("name_lesson")
            Log.e("id_semester","$lessonId")
            Log.e("name_lesson","$lessonName")
        }

        tv_lesson.text=lessonName

        paragList=db.getParag(lessonId!!)

        rv_paragraph.layoutManager= LinearLayoutManager(this)
        paragAdapter= ParagraphAdapter(this,paragList)
        rv_paragraph.adapter=paragAdapter
        rv_paragraph.layoutManager= LinearLayoutManager(this)

        paragAdapter!!.setOnItemClickListener(object :ParagraphAdapter.OnItemClickListener{
            override fun onClicked(clickedItemPosition: Int, id: Int, title: String?, video: String) {
                val i=Intent(applicationContext,DetailActivity::class.java)
                i.putExtra("video_id",id)
                i.putExtra("lesson",tv_lesson.text.toString())
                i.putExtra("title",title)
                i.putExtra("video",video)
                Log.e("video","$id, $title,$video")
                startActivity(i)
            }

        })
        ic_back.setOnClickListener { finish() }

    }

    private fun getParagraph(id:Int) {
        //avi.show()

        val stringRequest = object : StringRequest(Method.GET, URLs.URL_GET_VIDEOS+id, Response.Listener { response ->
          //      avi.hide()
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("status")) {

                        val jsonArray = obj.getJSONArray("items")

                        for (i in 0 until jsonArray.length()) {

                            val jsObj = jsonArray.getJSONObject(i)
                            val mJson = JsonParser().parse(jsObj.toString())
                            val paragraph: Paragraph  = Gson().fromJson<Any>(mJson, Paragraph::class.java) as Paragraph
                            paragList.add(paragraph)
                        }
                          paragAdapter= ParagraphAdapter(this,paragList)
                          rv_paragraph.adapter=paragAdapter
                           buildVideoRecy()
                        //   mCityAdapter!!.notifyDataSetChanged()
                        Toast.makeText(applicationContext,obj.getString("message"), Toast.LENGTH_SHORT).show()

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
            //    avi.hide()
               Toast.makeText(applicationContext,error.message.toString(), Toast.LENGTH_SHORT).show()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Accept"] = "application/json"
                map["Accept-Language"] = "en"
                return map
            }


        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)

    }

    private fun buildVideoRecy() {

        paragAdapter!!.setOnItemClickListener(object :ParagraphAdapter.OnItemClickListener{
            override fun onClicked(clickedItemPosition: Int, id: Int, title: String?, video: String) {
                val i=Intent(applicationContext,DetailActivity::class.java)
                i.putExtra("lesson",tv_lesson.text.toString())
                i.putExtra("title",title)
                i.putExtra("video",video)
                startActivity(i)
            }

        })
    }
}