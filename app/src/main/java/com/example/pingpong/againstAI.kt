package com.example.pingpong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.pingpong.databinding.ActivityAgainstAiBinding
import com.example.pingpong.databinding.ActivityGameBinding

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

       binding.btnStart.setOnClickListener(){
           binding.textView2.text = ""
           binding.againstAi.letsGo()
       }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
    }
}