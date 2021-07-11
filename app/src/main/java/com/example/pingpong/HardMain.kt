package com.example.pingpong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pingpong.databinding.ActivityHmodeBinding

var gLoBal = 0
var checker = 0
var end = false
class hMode : AppCompatActivity() {
    lateinit var binding: ActivityHmodeBinding
    private lateinit var viewModel: viewHard

    override fun onBackPressed() {
        binding.gameH.letsStop()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.isVisible = false

        binding.btnStart.setOnClickListener {
            binding.textView2.text = ""
            highScoreRefresh()

            binding.gameH.letsGo()
            binding.imageView4.setOnClickListener(){
                binding.gameH.powerUp()
                powerUpCountDown()
                binding.imageView4.isClickable = false
            }
        }

        viewModel = ViewModelProvider(this).get(viewHard::class.java)
        viewModel.readFrom.observe(this, { miNum ->

            binding.textView.text = miNum.toString()

        })

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }

    private fun highScoreRefresh() {
        val countDown: CountDownTimer
        countDown = object : CountDownTimer(10000000, 1000) {
            override fun onTick(millisecsToFinish: Long) {
                if (gLoBal > checker) {
                    val miNum = gLoBal
                    viewModel.saveTo(miNum)
                    checker = gLoBal
                }
                if(runG == false){
                    binding.imageView4.isClickable = true
                    binding.imageView4.isVisible = true
                    binding.progressBar.isVisible = false
                    binding.textView8.text = "Paddle x1.5"
                }
            }

            override fun onFinish() {
            }
        }
        countDown.start()
    }

    private fun powerUpCountDown() {
            val countDown: CountDownTimer
            countDown = object : CountDownTimer(11000, 1000) {
                override fun onTick(millisecsToFinish: Long) {
                    if(runG == true){
                        binding.textView8.text = (millisecsToFinish / 1000).toString()
                    }

                }

                override fun onFinish() {
                    binding.gameH.powerReset()
                    powerRefresh()
                    binding.imageView4.isVisible = false
                }
            }
            countDown.start()
        }

    private fun powerRefresh() {
        val countDown: CountDownTimer
        countDown = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisecsToFinish: Long) {
                if(runG == true){
                    binding.textView8.text = "Live in ${(millisecsToFinish / 1000).toString()}"
                    binding.progressBar.isVisible = true
                }
            }

            override fun onFinish() {
                binding.imageView4.isClickable = true
                binding.imageView4.isVisible = true
                binding.progressBar.isVisible = false
                binding.textView8.text = "Paddle x1.5"
            }
        }
        countDown.start()
    }
}





