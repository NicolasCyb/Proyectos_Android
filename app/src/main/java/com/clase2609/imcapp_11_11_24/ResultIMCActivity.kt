package com.clase2609.imcapp_11_11_24

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.clase2609.imcapp_11_11_24.MainActivity.Companion.IMC_Key

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription:TextView
    private lateinit var btnReCalcularIMC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_imcactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        //Con esta val resultIMC podemos recoges el valor de imc comprobacion segura
        val resultIMC = intent.extras?.getDouble(IMC_Key)?: -1.0

        initComponent()
        initListeners()
        initUI(resultIMC)

    }

    private fun initUI(resultIMC:Double) {
        this.tvIMC.text = resultIMC.toString()
        when(resultIMC){

            in 0.00..18.50 -> {
                this.tvResult.setTextColor(ContextCompat.getColor(this,R.color.peso_bajo))
                this.tvResult.text= "PESO BAJO"
                this.tvDescription.text ="Tu peso esta por debajo de lo saludable"
            }
            in 18.51..24.99-> {
                this.tvResult.setTextColor(ContextCompat.getColor(this,R.color.peso_normal))
                this.tvResult.text= "PESO NORMAL"
                this.tvDescription.text ="Tu peso es saludable"
            }
            in 25.00..29.99 -> {
                this.tvResult.setTextColor(ContextCompat.getColor(this,R.color.sobre_peso))
                this.tvResult.text= "SOBREPESO"
                this.tvDescription.text ="Tu peso esta por encima de lo saludable"
            }
            in 30.00..99.00 -> {
                this.tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
                this.tvResult.text= "Obesidad"
                this.tvDescription.text ="Tu peso esta muy por encima de lo saludable"
            }
            else->{
                this.tvResult.setTextColor(ContextCompat.getColor(this,R.color.error))
                this.tvIMC.text = getString(R.string.error)
                this.tvResult.text = getString(R.string.error)
                this.tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initListeners() {
        this.btnReCalcularIMC.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    //Recoger los componentes que queremos usar
    private fun initComponent() {
        this.tvIMC = findViewById(R.id.tvIMC)
        this.tvResult = findViewById(R.id.tvResult)
        this.tvDescription = findViewById(R.id.tvDescription)
        this.btnReCalcularIMC = findViewById(R.id.btnReCalc)

    }
}