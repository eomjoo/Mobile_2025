package kr.ac.jbnu.kangdongki.inuyasha2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.content.Intent
import android.widget.Button
import com.example.moblie.R

class character: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        val playerImage = findViewById<ImageView>(R.id.playerImage)
        val opponentImage = findViewById<ImageView>(R.id.opponentImage)

        val playerChampion = intent.getIntExtra("playerChampion", -1)
        val opponentChampion = intent.getIntExtra("opponentChampion", -1)

        if (playerChampion != -1 && opponentChampion != -1) {
            playerImage.setImageResource(playerChampion)
            opponentImage.setImageResource(opponentChampion)
        }
        val btnChangeHero = findViewById<Button>(R.id.btnChangeHero)
        btnChangeHero.setOnClickListener {
            val intent1 = Intent(this, Play::class.java)
            startActivity(intent1)
            finish()
        }
        val btnFight = findViewById<Button>(R.id.btnFight)
        btnFight.setOnClickListener {
            val intent = Intent(this, skills::class.java)
            intent.putExtra("playerChampion", playerChampion)
            intent.putExtra("opponentChampion", opponentChampion)
            startActivity(intent)
        }


    }
}