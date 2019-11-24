package com.example.week4_weekend_broadfragments.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.week4_weekend_broadfragments.R
import kotlinx.android.synthetic.main.bottom_frame_layout.*

class Bottom_Fragment: Fragment() {

    lateinit var bottom_receiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_frame_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(
                Glide.with(this).asGif()
                    .load("https://i.makeagif.com/media/8-30-2018/eQ9vkP.gif")
                    .into(bottom_imageView))
    }


    override fun onStart() {
        super.onStart()
        bottom_receiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var authoritay = intent?.getStringExtra("Popo")
                bottom_textview.text = authoritay


            }

        }

        val bottom_filter = IntentFilter("South Park")
        val main = activity as MainActivity?

        main?.registerReceiver(bottom_receiver, bottom_filter)


    }

    override fun onDestroy() {

        val main = activity as MainActivity?

        main?.unregisterReceiver(bottom_receiver)
        super.onDestroy()

    }
}