package com.example.moblie

import android.R.attr.name
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moblie.databinding.ActivityEnemySelectBinding
import kotlin.jvm.java

class EnemySelectActivity : BaseActivity() {

    private lateinit var binding: ActivityEnemySelectBinding

    data class Enemy(val name: String, val imageRes: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnemySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 내 캐릭터 이름 받기
        val selectedCharacter = intent.getStringExtra("selectedCharacter") ?: "INUYASHA"

        // 임시 이미지 설정 (모든 캐릭터 동일 이미지)
        val playerImageRes = R.drawable.swordmaster

        // 적 캐릭터 2명 랜덤 섞기
        val enemyList = listOf(
            Enemy("INUYASHA", R.drawable.swordmaster),
            Enemy("KAGOME", R.drawable.swordmaster)
        ).shuffled()
        //플레이어 이름
        binding.playerName.text = selectedCharacter
        //적 이름
        binding.enemyName.text = "${enemyList[0].name}"
        binding.middleEnemy1.text = "${enemyList[0].name}"
        binding.middleEnemy2.text = "${enemyList[1].name}"
        // 대면 캐릭터 이미지 설정
        binding.playerFaceImage.setImageResource(playerImageRes)
        binding.enemyFaceImage.setImageResource(enemyList[0].imageRes)

        //적 캐릭터 순서 1,2
        binding.thumbEnemy1.contentDescription = "${enemyList[0].name}"
        binding.thumbEnemy2.contentDescription = "${enemyList[1].name}"

        // 텍스트뷰가 하나뿐이므로 이름 텍스트는 토스트로 대체하거나 UI에 추가해야 함

        // 버튼 클릭 처리
        binding.fightButton.setOnClickListener {
            Toast.makeText(this, "${enemyList[0].name} 와 전투 시작!", Toast.LENGTH_SHORT).show()
        }

        binding.changeFighterButton.setOnClickListener {
            finish() // 이전 캐릭터 선택 화면으로 돌아감
        }
    }
}
