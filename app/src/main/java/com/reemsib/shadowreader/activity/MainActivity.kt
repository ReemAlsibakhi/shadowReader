package com.reemsib.shadowreader.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.fragment.NotificationsFragment
import com.reemsib.shadowreader.setting.PreferencesManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var manager: PreferencesManager
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager=PreferencesManager(applicationContext)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        if (manager.isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (checkGooglePlayServices()) {

        } else {
            //You won't be able to send notifications to this device
            Log.w(TAG, "Device doesn't have google play services")
        }

        onNewIntent(getIntent())


    }

//    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent) {
//        //    text_view_notification.text = intent.extras?.getString("message")
//          //  Toast.makeText(applicationContext, intent.extras?.getString("Positives"),Toast.LENGTH_LONG).show()
//            val bundle = intent.extras
//            if (bundle != null) {
//                var a=bundle.getString("flag")
//                 if (bundle.getString("flag")=="1"){
//                        Log.e("flag",a!!)
//                     //   val notificationsFragment: NotificationsFragment? = null
//                        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, NotificationsFragment()).commit()
//                    }
//                }
//                //Toast.makeText(applicationContext, intent.extras?.getString(bundle.getString("text")),Toast.LENGTH_LONG).show()
//
//
//             //   Log.e("text_message","${intent.extras?.getString(bundle.getString("text"))}")
//            }
//        }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i(TAG, "Google play services updated")
            true
        }
    }

//    override fun onStart() {
//        super.onStart()
//  //    LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter("notify"))
//
//    }

//    override fun onStop() {
//        super.onStop()
//   //     LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
//
//    }
    public override fun onNewIntent(intent: Intent){
        super.onNewIntent(intent)
        var extras = intent.getExtras()
        if(extras != null){
            if(extras.containsKey("flag")) {
                if (extras.getString("flag")=="1"){
                  var a=extras.getString("flag")
                    Log.e("flag", a!!)
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, NotificationsFragment()).commit();
                    nav_view.menu.findItem(R.id.navigation_notifications).isChecked = true;

                }
            }
        }
    }
}