package com.example.pingpong

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.pingpong.databinding.ActivityGameBinding

var checkerQ = 0

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    private lateinit var viewModel : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            binding.textView2.text = ""
            if(gLoBalQ > checkerQ){
                val myNum = gLoBalQ
                viewModel.saveTo1(myNum)
                checkerQ = gLoBalQ
            }
            binding.PingPongView.letsGo()
        }

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.readFrom.observe(this, {myNum ->

            binding.textView.text = myNum.toString()

        })

        binding.btnStop.setOnClickListener {
            binding.PingPongView.letsStop()
            binding.textView2.text = "Click Play to Start ..."
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }
}

