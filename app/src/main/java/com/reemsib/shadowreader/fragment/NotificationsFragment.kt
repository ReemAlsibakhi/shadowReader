package com.reemsib.shadowreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.LessonAdapter
import com.reemsib.shadowreader.adapter.NotificationAdapter
import com.reemsib.shadowreader.model.Lesson
import com.reemsib.shadowreader.model.Notification
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    var notificatonsList=ArrayList<Notification>()
    var mNotifiAdapter: NotificationAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
        notificatonsList.add(Notification(0,"Unit One - New friends","It Has Roots In A Piece Of Classiyical CA Literature From 45 BC.","7/8/2020"))
       mNotifiAdapter=NotificationAdapter(requireActivity(),notificatonsList)
        rv_notify.adapter=mNotifiAdapter
        rv_notify.layoutManager=LinearLayoutManager(requireContext())

    }
}