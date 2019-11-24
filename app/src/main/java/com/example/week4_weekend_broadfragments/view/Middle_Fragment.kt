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
import kotlinx.android.synthetic.main.middle_frame_layout.*

class Middle_Fragment: Fragment() {

    lateinit var middle_reciever: BroadcastReceiver
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.middle_frame_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(
                Glide.with(this).asGif()
                    .load("https://media3.giphy.com/media/l0HlxBaX9oJ24YT7i/200.gif?cid=790b7611d34319cdf588a157864aacac9ff0db6a2c69c456&rid=200.gif")
                    .into(middle_imageView))
    }

    override fun onStart() {
        super.onStart()
        middle_reciever = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var my = intent?.getStringExtra("Cops")
                middle_textview.text = my


            }

        }


        val middle_filter = IntentFilter("South Park")



        val main = activity as MainActivity?

        main?.registerReceiver(middle_reciever, middle_filter)

    }


    override fun onDestroy() {

        val main = activity as MainActivity?

        main?.unregisterReceiver(middle_reciever)
        super.onDestroy()
    }
}