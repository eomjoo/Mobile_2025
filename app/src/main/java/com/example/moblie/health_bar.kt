package kr.ac.jbnu.kangdongki.inuyasha2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class health_bar : AppCompatActivity() {

    lateinit var yourHealthBar: ProgressBar
    lateinit var enemyHealthBar: ProgressBar
    var yourHealth = 100
    var enemyHealth = 100// Máu tối đa 100

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(kr.ac.jbnu.kangdongki.inuyasha2.R.layout.activity_skills_choose)

        yourHealthBar = findViewById(kr.ac.jbnu.kangdongki.inuyasha2.R.id.yourHealthBar)
        enemyHealthBar = findViewById(kr.ac.jbnu.kangdongki.inuyasha2.R.id.enemyHealthBar)
        yourHealthBar.progress = yourHealth
        enemyHealthBar.progress = enemyHealth


        }
}