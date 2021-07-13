package com.example.pingpong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import com.example.pingpong.databinding.ActivityAgainstAiBinding

class againstAI : AppCompatActivity() {

    override fun onBackPressed() {
        binding.againstAi.letsStop()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    lateinit var binding: ActivityAgainstAiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar2.isVisible = false

       binding.btnStart.setOnClickListener(){
           binding.textView2.text = ""
           binding.againstAi.letsGo()
           scoreRefresh()
           binding.againstAi.BarT().start()
       }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }

    private fun scoreRefresh() {
        val countDown: CountDownTimer
        countDown = object : CountDownTimer(10000000, 1000) {
            override fun onTick(millisecsToFinish: Long) {
                if (playerPoint == 5)
                {
                    binding.textView2.text = "You win"
                    playerPoint = 0
                    binding.progressBar2.isVisible = true
                    pageRefresh()
                }
                if (aiPoint == 5)
                {
                    binding.textView2.text = "You lose"
                    aiPoint = 0
                    binding.progressBar2.isVisible = true
                    pageRefresh()
                }
            }
            override fun onFinish() {
            }
        }
        countDown.start()
    }

    private fun pageRefresh() {
        val countDown: CountDownTimer
        countDown = object : CountDownTimer(1500, 1000) {
        override fun onTick(millisecsToFinish: Long) {
        }
            override fun onFinish() {
            startActivity(getIntent())
        }
    }
    countDown.start()
}
}
