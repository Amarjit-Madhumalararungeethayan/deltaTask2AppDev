package com.example.pingpong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.pingpong.databinding.ActivityHmodeBinding

var gLoBal = 0
var temp = 0
var checker = 0

class hMode : AppCompatActivity() {
    lateinit var binding: ActivityHmodeBinding
    private lateinit var viewModel: viewHard

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

        viewModel = ViewModelProvider(this).get(viewHard::class.java)
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



