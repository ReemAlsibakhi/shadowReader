package com.reemsib.shadowreader.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.activity.LessonsActivity
import com.reemsib.shadowreader.setting.PreferencesManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private lateinit var manager: PreferencesManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        root.first_semester.setOnClickListener {
            val i= Intent(requireContext(),LessonsActivity::class.java)
            i.putExtra("id",1)
            startActivity(i)
        }
        root.second_semester.setOnClickListener {
           val i= Intent(requireContext(),LessonsActivity::class.java)
            i.putExtra("id",2)
            startActivity(i)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     manager=PreferencesManager(requireContext())

      if (manager.isLoggedIn){
          tv_name.text=manager.getUser().name
          tv_mobile.text=manager.getUser().mobile
          tv_school.text=manager.getUser().school
      }

    }
}