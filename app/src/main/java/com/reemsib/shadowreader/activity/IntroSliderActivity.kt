package com.reemsib.shadowreader.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.IntroSliderAdapter
import com.reemsib.shadowreader.fragment.Intro1Fragment
import com.reemsib.shadowreader.fragment.Intro2Fragment
import com.reemsib.shadowreader.fragment.Intro3Fragment
import com.reemsib.shadowreader.setting.PreferencesManager
import com.reemsib.shadowreader.utils.Constants
import kotlinx.android.synthetic.main.activity_intro_slider.*

class IntroSliderActivity : AppCompatActivity() {

    private lateinit var manager: PreferencesManager
    private val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_intro_slider)
        val i=intent
        if(i != null){
            if(i.getStringExtra("flag")=="setting"){
                Log.e("flag","setting")
                btn_skip.visibility=View.GONE
                btn_getStarted.visibility=View.GONE
                registerListenerSetting()
            }else{
              //  btn_skip.visibility=View.VISIBLE
               // btn_getStarted.visibility=View.VISIBLE
                registerListeners()
            }
        }
        manager = PreferencesManager(this)
        manager.setFirstRun()

        val adapter = IntroSliderAdapter(this)
        vpIntroSlider.adapter = adapter
        fragmentList.addAll(listOf(Intro1Fragment(), Intro2Fragment(), Intro3Fragment()))
        adapter.setFragmentList(fragmentList)
        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)

    }
    private fun registerListeners() {
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    indicatorLayout.visibility = View.VISIBLE
                    btn_skip.visibility = View.VISIBLE
                    btn_getStarted.visibility = View.GONE

                } else {
                    indicatorLayout.visibility = View.GONE
                    btn_skip.visibility = View.GONE
                    btn_getStarted.visibility = View.VISIBLE

                }
            }
        })
        btn_skip.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btn_getStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    fun registerListenerSetting() {
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                //    indicatorLayout.visibility=View.VISIBLE
                  //  btn_skip.visibility=View.VISIBLE
                  //  btn_getStarted.visibility=View.GONE

                } else {
                    //indicatorLayout.visibility=View.GONE
                   // btn_skip.visibility=View.GONE
                   // btn_getStarted.visibility=View.VISIBLE

                }
            }
        })
       }

//        tvNext.setOnClickListener {
//            val position = vpIntroSlider.currentItem
//            if (position < fragmentList.lastIndex) {
//                vpIntroSlider.currentItem = position + 1
//            } else {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }
    }
