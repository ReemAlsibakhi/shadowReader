package com.reemsib.shadowreader.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.fragment.LoginFragment
import com.reemsib.shadowreader.fragment.RegisterFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_login.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        loadFragment(LoginFragment())

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_login -> {
                tv_login.setBackgroundResource(R.drawable.ic_bg_login)
                tv_register.setBackgroundResource(0)
                loadFragment(LoginFragment())
            }
            R.id.tv_register -> {
              tv_register.setBackgroundResource(R.drawable.ic_bg_login)
              tv_login.setBackgroundResource(0)
              loadFragment(RegisterFragment())
            }
        }
    }
    private fun loadFragment(fragment: Fragment): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
            return true
        }
        return false;
    }
}