package com.example.week4_weekend_broadfragments.view

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.week4_weekend_broadfragments.R
import com.example.week4_weekend_broadfragments.service.PlaySheriff
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var topFragment: Top_Fragment
    private lateinit var middleFragment: Middle_Fragment
    private lateinit var bottomFragment: Bottom_Fragment
    lateinit var serviceIntent: Intent
    var playSheriff : PlaySheriff? = null

    private lateinit var system_receiver: BroadcastReceiver
    private lateinit var phrase_receiver: BroadcastReceiver



    private var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {


        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            playSheriff = (service as PlaySheriff.PlaySheriffBinder).getMusicService()
            Log.d("TAG_X", "onServiceConnected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_textview.setText("DON'T DO THE CRIME IF YOU CAN'T...")

        val first_filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        }
        system_receiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, intent?.action, Toast.LENGTH_SHORT).show()
            }

        }

        registerReceiver(system_receiver, first_filter)



        phrase_receiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var intent = Intent("South Park")
                intent.putExtra("Police", "RESPECT")
                    .putExtra("Cops", "MY")
                    .putExtra("Popo", "AUTHORITAY")
                sendBroadcast(intent)
            }

        }

        val second_filter = IntentFilter("Force")
        registerReceiver(phrase_receiver, second_filter)


        topFragment = Top_Fragment()
        middleFragment = Middle_Fragment()
        bottomFragment = Bottom_Fragment()


        serviceIntent = Intent(this, PlaySheriff::class.java)



        send_button.setOnClickListener {

            showFragments()
            if(playSheriff == null) {
                startService(serviceIntent)
                bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
            playSheriff?.playPressed()


            var next_intent = Intent("Force")
            next_intent.putExtra("Police", "RESPECT")
                .putExtra("Cops", "MY")
                .putExtra("Popo", "AUTHORITAY")
            sendBroadcast(next_intent)
//                startService(serviceIntent)

            send_button.visibility = View.INVISIBLE

        }
    }

    private fun showFragments(){
        supportFragmentManager.beginTransaction()
            .add(R.id.top_frame_layout, topFragment)
            .addToBackStack(topFragment.tag)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.middle_frame_layout, middleFragment)
            .addToBackStack(middleFragment.tag)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.bottom_frame_layout, bottomFragment)
            .addToBackStack(bottomFragment.tag)
            .commit()
    }


    override fun onStop() {
        super.onStop()
        stopService(serviceIntent)
        unbindService(serviceConnection)
    }
}
