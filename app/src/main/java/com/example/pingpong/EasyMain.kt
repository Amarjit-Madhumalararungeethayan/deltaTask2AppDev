package com.example.pingpong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.pingpong.databinding.ActivityGameBinding

var checkerQ = 0

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    private lateinit var viewModel : ViewEasy

    override fun onBackPressed() {
        binding.PingPongView.letsStop()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            binding.textView2.text = ""
            highScoreRefresh()
            binding.PingPongView.letsGo()
        }

        viewModel = ViewModelProvider(this).get(ViewEasy::class.java)
        viewModel.readFrom.observe(this, { myNum ->

            binding.textView.text = myNum.toString()

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
                    if (gLoBalQ > checkerQ) {                 //speed increases in easy mode if user clicks play again while the game is on
                        val myNum = gLoBalQ                     // in case user wants a faster speed
                        viewModel.saveTo1(myNum)
                        checkerQ = gLoBalQ
                    }
                }

                override fun onFinish() {
                }
            }
            countDown.start()
    }
}




