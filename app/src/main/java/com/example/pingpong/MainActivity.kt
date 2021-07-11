package com.example.pingpong

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.pingpong.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** val dj = MediaPlayer.create(this, R.raw.beat)
        dj.isLooping = true
        dj.start() **/

        /**binding.textView3.setOnClickListener(){
            binding.imageView.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.button.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.textView4.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.textView5.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.textView6.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.button2.animate().apply {
                duration = 500
                rotationYBy(360f)
            }.start()
            binding.imageView2.animate().apply {
                duration = 1000
                rotationXBy(720f)
            }.start()
            binding.imageView.setImageResource(R.drawable.screenshot_2021_06_14_at_3_51_47_pm)
        }
**/
        binding.button.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, hMode::class.java)
            startActivity(intent)
        }
        binding.ai.setOnClickListener(){
            val intent = Intent(this, againstAI::class.java)
            startActivity(intent)
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }
}