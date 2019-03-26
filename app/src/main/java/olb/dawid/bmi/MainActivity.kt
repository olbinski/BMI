package olb.dawid.bmi

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.main_layout.*
import olb.dawid.bmi.logic.BmiForKgCm
import olb.dawid.bmi.logic.BmiForLbIn
import java.lang.Double.MAX_VALUE
import java.lang.Double.MIN_VALUE


class MainActivity : AppCompatActivity() {
    private var isMetric = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        setSupportActionBar(my_toolbar as android.support.v7.widget.Toolbar?)

        infoButt.setOnClickListener {

            val intent = Intent(this, Info::class.java)
            intent.putExtra("bmiNumber", bmi_result.text)
            intent.putExtra("bmiText", bmi_text_result.text)
            startActivity(intent)
        }

        countBT.setOnClickListener {
            val mass: Int
            val height: Int

            try {
                mass = Integer.parseInt(massET.text.toString())
                height = Integer.parseInt(heightET.text.toString())


                try {
                    if (isMetric) {
                        val bmi = BmiForKgCm(mass, height)
                        setTexts(bmi.countBmi())
                    } else {
                        val bmi = BmiForLbIn(mass, height)
                        setTexts(bmi.countBmi())
                    }
                    infoButt.visibility = View.VISIBLE
                } catch (e: Exception) {
                    clearFields()

                    Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                clearFields()
                Toast.makeText(this@MainActivity, "puste editable", Toast.LENGTH_SHORT).show()

            }


        }


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putCharSequence(getString(R.string.saved_result_key), bmi_result.text)
        outState?.putBoolean(getString(R.string.is_info_button_key), infoButt.visibility == View.VISIBLE)
        outState?.putBoolean(getString(R.string.isMetric_key), isMetric)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.getCharSequence(getString(R.string.saved_result_key)).toString().toDoubleOrNull()?.let { setTexts(it) }
        val bool = savedInstanceState!!.getBoolean(getString(R.string.is_info_button_key))
        if (bool)
            infoButt.visibility = View.VISIBLE
        else
            infoButt.visibility = View.INVISIBLE

        isMetric = savedInstanceState.getBoolean(getString(R.string.isMetric_key))
        setUnitsTexts()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.units_settings -> {
                clearFields()
                isMetric = !isMetric
                setUnitsTexts()
            }
            R.id.about -> {
                val intent = Intent(this, AboutMe::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun clearFields() {
        massET.text.clear()
        heightET.text.clear()
        bmi_result.text = ""
        bmi_text_result.text = ""
        infoButt.visibility = View.INVISIBLE
    }

    fun setTexts(bmi: Double) {
        when (bmi) {
            in MIN_VALUE..18.5 -> {
                bmi_text_result.setTextColor(ContextCompat.getColor(this, R.color.rozPompejski))
                bmi_result.setTextColor(ContextCompat.getColor(this, R.color.rozPompejski))
                bmi_text_result.text = getString(R.string.underweight_text)
            }
            in 18.5..25.0 -> {
                bmi_text_result.setTextColor(ContextCompat.getColor(this, R.color.pomaranczowy))
                bmi_result.setTextColor(ContextCompat.getColor(this, R.color.pomaranczowy))
                bmi_text_result.text = getString(R.string.normal_weight_text)

            }
            in 25.0..30.0 -> {
                bmi_text_result.setTextColor(ContextCompat.getColor(this, R.color.zolty))
                bmi_result.setTextColor(ContextCompat.getColor(this, R.color.zolty))
                bmi_text_result.text = getString(R.string.overweight_text)

            }
            in 30.0..40.0 -> {
                bmi_text_result.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                bmi_result.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                bmi_text_result.text = getString(R.string.obesity_text)

            }
            in 40.0..MAX_VALUE -> {
                bmi_text_result.setTextColor(ContextCompat.getColor(this, R.color.lapisLazuli))
                bmi_result.setTextColor(ContextCompat.getColor(this, R.color.lapisLazuli))
                bmi_text_result.text = getString(R.string.morbid_obesity_text)
            }
        }
        bmi_result.text = bmi.toString()
    }

    fun setUnitsTexts() = if (!isMetric) {
        massText.text = getString(R.string.mass_imperial_text)
        heightText.text = getString(R.string.height_imperial_text)
    } else {
        massText.text = getString(R.string.mass_metric_text)
        heightText.text = getString(R.string.height_metric_text)
    }
}
