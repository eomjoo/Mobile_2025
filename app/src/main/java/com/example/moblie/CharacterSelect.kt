package com.example.moblie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moblie.databinding.ActivityCharacterSelectBinding
import kotlin.jvm.java

class CharacterSelect : BaseActivity() {
    private lateinit var binding: ActivityCharacterSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        선택 가능 캐릭터
        binding.charInuyasha.setOnClickListener {
            selectCharacter("INUYASHA")
        }

        binding.charKagome.setOnClickListener {
            selectCharacter("KAGOME")
        }

        // 잠긴 캐릭터 (지금은 아무 동작 없음)
        binding.charKagura.setOnClickListener { /* 잠금 처리 */ }
    }

    private fun selectCharacter(name: String) {
         val intent = Intent(this, EnemySelectActivity::class.java)
         intent.putExtra("selectedCharacter", name)
         startActivity(intent)
    }
}
