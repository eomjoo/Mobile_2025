package kr.ac.jbnu.kangdongki.inuyasha2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moblie.R

open class skills : AppCompatActivity() {
    private lateinit var btnContinue: Button
    private lateinit var btnChangeHero: Button
    private lateinit var resultButtonsLayout: LinearLayout
    private lateinit var charactercontrol: LinearLayout

    private lateinit var heroImage: ImageView
    private lateinit var enemyImage: ImageView

    private lateinit var actionOrderText: TextView

    private lateinit var btnAttack: Button
    private lateinit var btnDefend: Button
    private lateinit var btnJump: Button
    private lateinit var btnStart: Button

    lateinit var yourHealthBar: ProgressBar
    lateinit var enemyHealthBar: ProgressBar
    var yourHealth = 100
    var enemyHealth = 100// Máu tối đa 100
    private val damage = 30
    private val defendDamage = 15

    private var usedAttack = false
    private var usedDefend = false
    private var usedJump = false

    private val heroActions = mutableListOf<String>()

    private fun checkActionsReady() {
        if (heroActions.size == 3) {
            actionOrderText.text ="$heroActions"
            btnStart.visibility = View.VISIBLE
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skills)

        btnContinue = findViewById(R.id.btnContinue)
        btnChangeHero = findViewById(R.id.btnChangeHero)
        resultButtonsLayout = findViewById(R.id.resultButtonsLayout)
        charactercontrol = findViewById(R.id.charactercontrol)
        btnStart = findViewById(R.id.btnStart)
        actionOrderText = findViewById(R.id.actionOrderText)

        btnContinue.setOnClickListener {
            yourHealth = 100
            enemyHealth = 100

            yourHealthBar.progress = yourHealth
            enemyHealthBar.progress = enemyHealth

            charactercontrol.visibility = View.VISIBLE
            resultButtonsLayout.visibility = View.INVISIBLE


        }

        btnChangeHero.setOnClickListener {
            val intent = Intent(this, Play::class.java)
            startActivity(intent)
            finish()
        }

        heroImage = findViewById(R.id.heroImage)
        enemyImage = findViewById(R.id.enemyImage)

        val playerChampion = intent.getIntExtra("playerChampion", -1)
        val opponentChampion = intent.getIntExtra("opponentChampion", -1)

        if (playerChampion != -1 && opponentChampion != -1) {
            heroImage.setImageResource(playerChampion)
            enemyImage.setImageResource(opponentChampion)
        }

        yourHealthBar = findViewById(R.id.yourHealthBar)
        enemyHealthBar = findViewById(R.id.enemyHealthBar)
        yourHealthBar.progress = yourHealth
        enemyHealthBar.progress = enemyHealth

        btnAttack = findViewById(R.id.btnAttack)
        btnDefend = findViewById(R.id.btnDefend)
        btnJump = findViewById(R.id.btnJump)

        btnStart.visibility = View.GONE

        btnAttack.setOnClickListener {
            if (!usedAttack) {
                heroActions.add("attack")
                usedAttack = true
                checkActionsReady()
            } else {
                Toast.makeText(this, "Is used", Toast.LENGTH_SHORT).show()
            }
        }

        btnDefend.setOnClickListener {
            if (!usedDefend) {
                heroActions.add("defend")
                usedDefend = true
                checkActionsReady()
            } else {
                Toast.makeText(this, "Is used", Toast.LENGTH_SHORT).show()
            }
        }

        btnJump.setOnClickListener {
            if (!usedJump) {
                heroActions.add("jump")
                usedJump = true
                checkActionsReady()
            } else {
                Toast.makeText(this, "Is used", Toast.LENGTH_SHORT).show()
            }
        }
        btnStart.setOnClickListener {
            val intent = Intent(this, Fight::class.java)
            intent.putExtra("playerChampion", playerChampion)
            intent.putExtra("opponentChampion", opponentChampion)
            intent.putStringArrayListExtra("heroActions", ArrayList(heroActions))
            intent.putStringArrayListExtra("enemyActions", ArrayList(heroActions))
            startActivity(intent)
            finish()
        }
    }

    // Hiệu ứng tấn công: dịch sang phải rồi quay lại
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
                resultButtonsLayout.visibility = View.VISIBLE
                charactercontrol.visibility = View.INVISIBLE
            }
        } else {
            yourHealth -= damage
            if (yourHealth < 0) yourHealth = 0
            yourHealthBar.progress = yourHealth

            if (yourHealth == 0) {
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show()
                resultButtonsLayout.visibility = View.VISIBLE
                charactercontrol.visibility = View.INVISIBLE
            }
        }

    }

    // Hiệu ứng phòng thủ: rung nhẹ sang ngang
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
                resultButtonsLayout.visibility = View.VISIBLE
                charactercontrol.visibility = View.INVISIBLE
            }
        } else {
            yourHealth -= defendDamage
            if (yourHealth < 0) yourHealth = 0
            yourHealthBar.progress = yourHealth

            if (yourHealth == 0) {
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show()
                resultButtonsLayout.visibility = View.VISIBLE
                charactercontrol.visibility = View.INVISIBLE
            }
        }
    }

    // Hiệu ứng nhảy lên rồi hạ xuống
    private fun jump(view: ImageView) {
        val jump = TranslateAnimation(0f, 0f, 0f, -150f).apply {
            duration = 300
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        view.startAnimation(jump)

        yourHealth -= 0
        enemyHealth -=0

    }

    fun enemyTurn() {
        val enemyAction = listOf("Attack", "Defend", "Jump").random()

        when (enemyAction) {
            "Attack" -> {
                attack(enemyImage, false)
                yourHealth -= 30
                if (yourHealth < 0) yourHealth = 0
            }

            "Defend" -> {
                defend(enemyImage, false)
                enemyHealth += 15
                if (enemyHealth > 100) enemyHealth = 100
            }

            "Jump" -> {
                jump(enemyImage)
            }
        }
    }
    var index = 0
    fun heroTurn(){
        val heroaction=heroActions[index]
        when(heroaction){
            "attack"-> attack(heroImage, true)
            "defend" -> defend(enemyImage, false)
            "jump" -> jump(heroImage)
            }
        index++
        }
    }
