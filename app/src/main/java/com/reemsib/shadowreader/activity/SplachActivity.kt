package com.reemsib.shadowreader.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.setting.PreferencesManager

class SplachActivity : AppCompatActivity() {

    private lateinit var manager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manager = PreferencesManager(this)

        setContentView(R.layout.activity_splach)
        Handler().postDelayed({
           // if (manager.isFirstRun()) {
                val i = Intent(this, IntroSliderActivity::class.java)
                startActivity(i)
                finish()
           // } else {
//                if(manager.isLoggedIn){
//                    val i = Intent(this, MainActivity::class.java)
//                    startActivity(i)
//                    finish()
//                }else{
//                    val i = Intent(this, LoginActivity::class.java)
//                    startActivity(i)
//                    finish()
//                }

      //      }

           // }

        }, 4000)
    }

    }
