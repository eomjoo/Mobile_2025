package com.example.moblie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moblie.databinding.ActivityDifficultyBinding
import kr.ac.jbnu.kangdongki.inuyasha2.Play


class DifficultyActivity : BaseActivity() {
    private lateinit var binding: ActivityDifficultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //난이도 버튼 추가
        binding.normalButton.setOnClickListener {
            val intent = Intent(this, Play::class.java)
            intent.putExtra("difficulty", "normal")
            startActivity(intent)
            finish()
        }
        binding.hardButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.update_message), Toast.LENGTH_SHORT).show()
        }
    }
}