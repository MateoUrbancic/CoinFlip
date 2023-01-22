package com.example.mmos_projekt

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.util.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val SHAKE_THRESHOLD = 2.0f
    private lateinit var sensorManager: SensorManager
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textViewCoinFlip)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt((x * x + y * y + z * z).toDouble()) / SensorManager.GRAVITY_EARTH
        if (acceleration > SHAKE_THRESHOLD) {
            // Phone is being shaken
            // Do something here
            flipCoin()
        }


    }
    var i = 0
    @SuppressLint("SetTextI18n")
    fun flipCoin() {

        constraintLayout = findViewById(R.id.main_textview1)

        if (i == 0){
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.white))
            i = 1
        }
        else{
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.black))
            i = 0
        }


        textView.isVisible = false;
        val random = Random()
        val coinSide = random.nextInt(2)
        if (coinSide == 0) {
            // Show heads
            //textView.text = "Heads"
            imageView = findViewById(R.id.imageView);
            imageView.getLayoutParams().height = imageView.getLayoutParams().height - 100;
            imageView.getLayoutParams().width = imageView.getLayoutParams().width - 100;
            imageView.setImageResource(R.drawable.euro_glava);
            imageView.getLayoutParams().height = imageView.getLayoutParams().height + 100;
            imageView.getLayoutParams().width = imageView.getLayoutParams().width + 100;
        } else {
            // Show tails
            //textView.text = "Tails"
            imageView = findViewById(R.id.imageView);
            imageView.getLayoutParams().height = imageView.getLayoutParams().height + 100;
            imageView.getLayoutParams().width = imageView.getLayoutParams().width + 100;
            imageView.setImageResource(R.drawable.euro_pismo);
            imageView.getLayoutParams().height = imageView.getLayoutParams().height - 100;
            imageView.getLayoutParams().width = imageView.getLayoutParams().width - 100;
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used in this example
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}

