package kr.ac.jbnu.kangdongki.inuyasha2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Fight : AppCompatActivity() {

    private lateinit var heroImage: ImageView
    private lateinit var enemyImage: ImageView
    private lateinit var btnNextRound: Button
    private var yourHealth = 100
    private var enemyHealth = 100
    private val damage = 30
    lateinit var yourHealthBar: ProgressBar
    lateinit var enemyHealthBar: ProgressBar
    private val defendDamage = 15

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)

        heroImage = findViewById(R.id.heroImage)
        enemyImage = findViewById(R.id.enemyImage)
        btnNextRound = findViewById<Button>(R.id.btnNextRound)

        val playerChampion = intent.getIntExtra("playerChampion", -1)
        val opponentChampion = intent.getIntExtra("opponentChampion", -1)
        val heroActions = intent.getStringArrayListExtra("heroActions") ?: arrayListOf()
        val enemyActions = intent.getStringArrayListExtra("enemyActions") ?: arrayListOf()

        if (playerChampion != -1 && opponentChampion != -1) {
            heroImage.setImageResource(playerChampion)
            enemyImage.setImageResource(opponentChampion)
        }

        yourHealthBar = findViewById(kr.ac.jbnu.kangdongki.inuyasha2.R.id.yourHealthBar)
        enemyHealthBar = findViewById(kr.ac.jbnu.kangdongki.inuyasha2.R.id.enemyHealthBar)
        yourHealthBar.progress = yourHealth
        enemyHealthBar.progress = enemyHealth

        // Gọi lượt đánh
        startBattle(heroActions)
        btnNextRound.setOnClickListener {
            val intent = Intent(this, NEXT_ROUND::class.java)
            intent.putExtra("yourHealth", yourHealth)
            intent.putExtra("enemyHealth", enemyHealth)
            intent.putExtra("playerChampion", playerChampion)
            intent.putExtra("opponentChampion", opponentChampion)
            startActivity(intent)
            finish()
        }
    }

    private fun enemyTurn2() {
        val action = listOf("attack", "defend", "jump").random()
        when (action) {
            "attack" -> attack(enemyImage, false)
            "defend" -> defend(enemyImage, false)
            "jump" -> jump(enemyImage)
        }
    }

    private fun attack(attacker: ImageView, isHeroAttacking: Boolean) {
        val attackAnim = TranslateAnimation(0f, 100f, 0f, 0f).apply {
            duration = 150
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        attacker.startAnimation(attackAnim)

        if (isHeroAttacking) {
            enemyHealth -= damage
            if (enemyHealth < 0) enemyHealth = 0
            enemyHealthBar.progress = enemyHealth

            if (enemyHealth == 0) {
                Toast.makeText(this, "Win!", Toast.LENGTH_SHORT).show()
            }
        } else {
            yourHealth -= damage
            if (yourHealth < 0) yourHealth = 0
            yourHealthBar.progress = yourHealth

            if (yourHealth == 0) {
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun defend(defender: ImageView, isHeroAttacking: Boolean) {
        val shake = TranslateAnimation(0f, 15f, 0f, 0f).apply {
            duration = 50
            repeatCount = 5
            repeatMode = Animation.REVERSE
        }
        defender.startAnimation(shake)

        if (isHeroAttacking) {
            enemyHealth -= defendDamage
            if (enemyHealth < 0) enemyHealth = 0
            enemyHealthBar.progress = enemyHealth

            if (enemyHealth == 0) {
                Toast.makeText(this, "Win!", Toast.LENGTH_SHORT).show()
            }
        } else {
            yourHealth -= defendDamage
            if (yourHealth < 0) yourHealth = 0
            yourHealthBar.progress = yourHealth

            if (yourHealth == 0) {
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun jump(view: ImageView) {
        val jump = TranslateAnimation(0f, 0f, 0f, -150f).apply {
            duration = 300
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        view.startAnimation(jump)

    }

    private fun checkBattleResult() {
        when {
            yourHealth <= 0 -> {
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show()
            }
            enemyHealth <= 0 -> {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            }
            else -> btnNextRound.visibility = View.VISIBLE
        }
    }

    private fun startBattle(actions: List<String>) {
        val handler = Handler()
        var index = 0
        val heroActions = intent.getStringArrayListExtra("heroActions") ?: arrayListOf()

        fun nextTurn() {
            if (yourHealth <= 0 || enemyHealth <= 0 || index >= actions.size) {
                checkBattleResult()
                return
            }

            // Enemy turn
            handler.postDelayed({
                enemyTurn2()
                yourHealthBar.progress = yourHealth
            }, 1000)

            // Hero turn
            handler.postDelayed({
                when (actions[index]) {
                    "attack" -> attack(heroImage, true)
                    "defend" -> defend(heroImage,  true)
                    "jump" -> jump(heroImage)
                }
                yourHealthBar.progress = yourHealth
                enemyHealthBar.progress = enemyHealth
                index++
                nextTurn()
            }, 2000)
        }
        nextTurn()
    }
}