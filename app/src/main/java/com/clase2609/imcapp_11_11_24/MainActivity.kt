package com.clase2609.imcapp_11_11_24

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    //Crear variables privadas para recoger los elementos visuales y lo hacemos con
    // inicializacion tardia ( Lazy )


    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubWeight: FloatingActionButton
    private lateinit var btnPlusbWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubAge: FloatingActionButton
    private lateinit var btnPlusbAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnImcCalculator: Button

    // Creamos los atributos necesarios para la logica de nuestros componentes

    private var IsMaleSelected: Boolean = true
    private var IsFemaleSelected: Boolean = false
    private var currentWeight: Int = 70
    private var currentAge: Int = 40
    private var currentHeight: Double = 1.5 //esta en metros aunque despues lo tratamos como cm

    //Creacion de un companion objetc que es accesible desde toas las activities

    companion object {
        const val IMC_Key = "IMC_RESULT"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // funciones, estructura de elementos para el proyecto

        // iniciar los componentes visuales
        initComponents()

        // Iniciar o crear los eventos
        initListeners()

        // Iniciar o configuraciones visuales de los componentes, como queremos que esten al principio
        initUI()



    }

    private fun initUI() {
        this.setWeight()
        this.setAge()
        this.setGenderColor()
        this.currentWeight


    }

    private fun initListeners() {
        this.viewMale.setOnClickListener{
            if(!this.IsMaleSelected){
                this.changeGender()
                this.setGenderColor()
            }
        }

        this.viewFemale.setOnClickListener{
            if(!this.IsFemaleSelected){
                this.changeGender()
                this.setGenderColor()
            }
        }

        this.rsHeight.addOnChangeListener{_, value,_ ->
            this.currentHeight= (value /100.0).toDouble()// metros


            // Definimos un formato para la altura
            val df = DecimalFormat("#.##")
            val result = df.format(value)
            this.tvHeight.text = result+ "cm"

        }

        this.btnSubWeight.setOnClickListener{
            this.currentWeight -= 1
            setWeight()

        }

        this.btnPlusbWeight.setOnClickListener{
            this.currentWeight += 1
            setWeight()

        }

        this.btnSubAge.setOnClickListener{
            this.currentAge -= 1
            setAge()
        }

        this.btnPlusbAge.setOnClickListener{
            this.currentAge += 1
            setAge()
        }

        this.btnImcCalculator.setOnClickListener{
            val resultIMC = calcularteIMC()

            navigateToResult(resultIMC)

            //Navegacion a la otra ventana


        }

    }

    private fun navigateToResult(resultIMC: Double) {
        // Creamos el objeto intent

        val intent= Intent(this, ResultIMCActivity::class.java)


        //Añadimos el extra para pasar el resultIMC
        intent.putExtra(IMC_Key,resultIMC)

        this.startActivity(intent)

    }

    private fun calcularteIMC(): Double {
        val imc  = this.currentWeight / (this.currentHeight * this.currentHeight)

        val df = DecimalFormat("#.##")
        val result = df.format(imc).toDouble()
        Log.i ( "IMC", "EL IMC es: $result ")
        return result

    }

    private fun setAge() {
        this.tvAge.text = this.currentAge.toString()

    }

    // Funcion para ver en el texto el resultado del peso y edad
    private fun setWeight() {
        this.tvWeight.text = this.currentWeight.toString()
    }

    // iniciar los componentes visuales

    private fun initComponents() {
        this.viewMale = findViewById((R.id.viewMale))
        this.viewFemale = findViewById((R.id.viewFemale))
        this.tvHeight = findViewById(R.id.tvHeight)
        this.rsHeight = findViewById(R.id.rsHeight)
        this.btnSubWeight = findViewById(R.id.btnSubWeight)
        this.btnPlusbWeight = findViewById(R.id.btnPlusbWeight)
        this.tvWeight = findViewById(R.id.tvWeight)
        this.btnSubAge = findViewById(R.id.btnSubAge)
        this.btnPlusbAge = findViewById(R.id.btnPlusbAge)
        this.tvAge = findViewById(R.id.tvAge)
        this.btnImcCalculator = findViewById(R.id.btnImcCalculator)

    }

    // Cambiamos el valor del boolean de trua a false o al reves
    private fun changeGender(){
        this.IsMaleSelected = !this.IsMaleSelected
        this.IsFemaleSelected = !this.IsFemaleSelected

    }


    private fun setGenderColor(){
        this.viewMale.setCardBackgroundColor(this.getBackGroundColor(this.IsMaleSelected))
        this.viewFemale.setCardBackgroundColor(this.getBackGroundColor(this.IsFemaleSelected))

    }

    private fun getBackGroundColor(isSelectedComponent : Boolean): Int{
        val colorReference = if(isSelectedComponent){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }
        return ContextCompat.getColor(this, colorReference)
    }
}