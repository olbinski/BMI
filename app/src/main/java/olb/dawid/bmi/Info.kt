package olb.dawid.bmi

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.bmi_layout.*
import kotlinx.android.synthetic.main.main_layout.*

class Info : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_layout)
        setSupportActionBar(my_toolbar_bmi as android.support.v7.widget.Toolbar?)

        val bmiTextFromIntent = intent.getStringExtra("bmiText")
        bmiNumber.text = intent.getStringExtra("bmiNumber")
        bmiText.text = bmiTextFromIntent

        var description = "defualt"
        when(bmiTextFromIntent){

            getString(R.string.underweight_text) -> {
                text_about_bmi.setTextColor(ContextCompat.getColor(this, R.color.rozPompejski))
                bmiText.setTextColor(ContextCompat.getColor(this, R.color.rozPompejski))
                bmiNumber.setTextColor(ContextCompat.getColor(this, R.color.rozPompejski))
                description = getString(R.string.underweight_description_text)
            }
            getString(R.string.normal_weight_text) -> {
                text_about_bmi.setTextColor(ContextCompat.getColor(this, R.color.pomaranczowy))
                bmiText.setTextColor(ContextCompat.getColor(this, R.color.pomaranczowy))
                bmiNumber.setTextColor(ContextCompat.getColor(this, R.color.pomaranczowy))
                description = getString(R.string.normal_weight_description_text)
            }
            getString(R.string.overweight_text) -> {
                text_about_bmi.setTextColor(ContextCompat.getColor(this, R.color.zolty))
                bmiNumber.setTextColor(ContextCompat.getColor(this, R.color.zolty))
                bmiText.setTextColor(ContextCompat.getColor(this, R.color.zolty))
                description = getString(R.string.overweight_description_text)
            }
            getString(R.string.obesity_text) -> {
                text_about_bmi.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                bmiNumber.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                bmiText.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                description = getString(R.string.obesity_description_text)
            }
            getString(R.string.morbid_obesity_text) -> {
                text_about_bmi.setTextColor(ContextCompat.getColor(this, R.color.lapisLazuli))
                bmiNumber.setTextColor(ContextCompat.getColor(this, R.color.lapisLazuli))
                bmiText.setTextColor(ContextCompat.getColor(this, R.color.lapisLazuli))
                description = getString(R.string.morbid_obesity_description_text)
            }
        }
        text_about_bmi.text = description
    }


}