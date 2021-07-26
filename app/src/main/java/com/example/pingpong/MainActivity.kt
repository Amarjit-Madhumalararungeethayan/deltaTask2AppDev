package com.example.pingpong

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.pingpong.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> this.finish() })
            .setNegativeButton("No", null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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