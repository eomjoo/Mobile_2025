package kr.ac.jbnu.kangdongki.inuyasha2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.content.Intent
import com.example.moblie.R

class Play : AppCompatActivity() {

    private val champions = listOf(
        R.drawable.kagome,
        R.drawable.sesshomaru,
        R.drawable.naraku_png
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val img1 = findViewById<ImageView>(R.id.imageView)
        val img2 = findViewById<ImageView>(R.id.imageView3)
        val img3 = findViewById<ImageView>(R.id.imageView2)

        img1.setOnClickListener { selectChampion(0) }
        img2.setOnClickListener { selectChampion(1) }
        img3.setOnClickListener { selectChampion(2) }
    }

    private fun selectChampion(index: Int) {
        val selectedChampion = champions[index]

        // Tạo danh sách các tướng còn lại
        val otherChampions = champions.toMutableList()
        otherChampions.removeAt(index)

        // Chọn ngẫu nhiên đối thủ
        val opponentChampion = otherChampions.random()

        // Gửi dữ liệu sang BattleActivity
        val intent = Intent(this, character::class.java)
        intent.putExtra("playerChampion", selectedChampion)
        intent.putExtra("opponentChampion", opponentChampion)
        startActivity(intent)
    }
}