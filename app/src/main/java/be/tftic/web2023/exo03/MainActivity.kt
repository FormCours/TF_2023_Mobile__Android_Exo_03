package be.tftic.web2023.exo03

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.tftic.web2023.exo03.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomTitleColor()



    }


























    private fun setCustomTitleColor() {
        // Mise en couleur du titre avec un dégradé
        val width = binding.titleApp.paint.measureText(binding.titleApp.text.toString())
        val height = binding.titleApp.textSize

        val textShader: Shader = LinearGradient(
            0f,  1f, width, height,
            intArrayOf(
                Color.parseColor("#FF0000"),
                Color.parseColor("#FF12FB"),
                Color.parseColor("#1DFFEC"),
                Color.parseColor("#00ff00"),
                Color.parseColor("#FFFFFB")
            ), null, Shader.TileMode.CLAMP);

        binding.titleApp.paint.shader = textShader
    }
}