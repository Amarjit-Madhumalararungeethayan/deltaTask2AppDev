package com.example.pingpong

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.pingpong.databinding.ActivityGameBinding
import com.example.pingpong.databinding.ActivityHmodeBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.concurrent.thread

var gLoBal = 0
var temp = 0
var checker = 0

class hMode : AppCompatActivity() {
    lateinit var binding: ActivityHmodeBinding
    private lateinit var viewModel: ViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            binding.textView2.text = ""
            if (gLoBal > checker) {
                val miNum = gLoBal
                viewModel.saveTo(miNum)
                checker = gLoBal
            }
            binding.gameH.letsGo()
        }

        viewModel = ViewModelProvider(this).get(ViewModel2::class.java)
        viewModel.readFrom.observe(this, { miNum ->

            binding.textView.text = miNum.toString()

        })

        binding.btnStop.setOnClickListener {
            binding.gameH.letsStop()
            binding.textView2.text = "Click Play to Start ..."
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }
}



