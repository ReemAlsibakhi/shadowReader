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
import com.orhanobut.hawk.Hawk
import com.r.foodproject.ui.model.DatabaseHelper
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.LessonAdapter
import com.reemsib.shadowreader.adapter.SchoolAdapter
import com.reemsib.shadowreader.model.Lesson
import com.reemsib.shadowreader.model.Paragraph
import com.reemsib.shadowreader.setting.MySingleton
import com.reemsib.shadowreader.utils.Constants
import com.reemsib.shadowreader.utils.URLs
import kotlinx.android.synthetic.main.activity_semester.*
import org.json.JSONException
import org.json.JSONObject

class LessonsActivity : AppCompatActivity() {

    var lessonsList=ArrayList<Lesson>()
    var mLessonAdapter:LessonAdapter ?=null
    var id_semester :Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_semester)

       val db=DatabaseHelper(applicationContext)

       Hawk.init(applicationContext).build()

         val i=intent
        if(i != null){
            id_semester=i.getIntExtra("id",-1)
           Log.e("id_semester","$id_semester")
        }
        if(id_semester==1) {
            tv_semester.text=getString(R.string.lessons_first_semester)

        }else{
            tv_semester.text=getString(R.string.lessons_second_semester)
        }

        ic_back.setOnClickListener { finish() }

        lessonsList=db.getLesson(id_semester!!)

        rv_lessons.layoutManager=LinearLayoutManager(this)
        mLessonAdapter= LessonAdapter(this,lessonsList)
        rv_lessons.adapter=mLessonAdapter

            mLessonAdapter!!.setOnItemClickListener(object :LessonAdapter.OnItemClickListener{
            override fun onClicked(clickedItemPosition: Int, id: Int,lesson: String?) {
                 val i=Intent(applicationContext,ParagraphsActivity::class.java)
                  i.putExtra("id_lesson",id)
                  i.putExtra("name_lesson",lesson)
                 startActivity(i)
                //  startActivity(Intent(applicationContext,ParagraphDetailActivity::class.java))
            }

        })

    }

//    private fun getLessons(id:Int) {
//        avi.show()
//
//        val stringRequest = object : StringRequest(Method.GET, URLs.URL_LESSONS+id, Response.Listener { response ->
//                avi.hide()
//                try {
//                    val obj = JSONObject(response)
//
//                    if (obj.getBoolean("status")) {
//
//                        val jsonArray = obj.getJSONArray("items")
//                        var lesson: Lesson
//                        for (i in 0 until jsonArray.length()) {
//
//                            val jsObj = jsonArray.getJSONObject(i)
//                            val mJson = JsonParser().parse(jsObj.toString())
//                             lesson = Gson().fromJson<Any>(mJson, Lesson::class.java) as Lesson
//
//                        }
//                        if (id==1){
//                           // lessonsList2.add(lesson)
//                            Log.e("lesson_1","$lessonsList")
//                            Hawk.put(Constants.LESSONS_1, lessonsList)
//                        }else{
//                        //    lessonsList2.add(lesson)
//                            Hawk.put(Constants.LESSONS_2, lessonsList2)
//                            Log.e("lesson_2","$lessonsList2")
//
//                        }
//
//                       // Hawk.put(Constants.LESSONS_1, lessonsList)
//                       //  Hawk.put(Constants.LESSONS_2, lessonsList2)
//                        if (id==1){
//                            mLessonAdapter= LessonAdapter(this,lessonsList)
//                            buildLessonRecy()
//                        }else{
//                            mLessonAdapter= LessonAdapter(this,lessonsList2)
//                            buildLessonRecy()
//                        }
//
//                        //   mCityAdapter!!.notifyDataSetChanged()
//                        Toast.makeText(applicationContext,obj.getString("message"), Toast.LENGTH_SHORT).show()
//
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            },
//            Response.ErrorListener { error ->
//                avi.hide()
//               Toast.makeText(applicationContext,error.message.toString(), Toast.LENGTH_SHORT).show()
//            }) {
//
//            override fun getHeaders(): MutableMap<String, String> {
//                val map = HashMap<String, String>()
//                map["Accept"] = "application/json"
//                map["Accept-Language"] = "en"
//                return map
//            }
//
//
//        }
//        MySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
//
//    }
    private fun buildLessonRecy() {
        rv_lessons.adapter=mLessonAdapter


    }

}