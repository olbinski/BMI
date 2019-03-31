package olb.dawid.bmi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.main_layout.*
import olb.dawid.bmi.logic.BmiForKgCm
import olb.dawid.bmi.logic.BmiForLbIn
import java.lang.Double.MAX_VALUE
import java.lang.Double.MIN_VALUE


class MainActivity : AppCompatActivity() {
    private var isMetric = true

    private var historyList: MutableList<HistoryElement> = mutableListOf()

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
                    val bmiValue: Double

                    bmiValue = if (isMetric) {
                        val bmi = BmiForKgCm(mass, height)
                        bmi.countBmi()
                    } else {
                        val bmi = BmiForLbIn(mass, height)
                        bmi.countBmi()
                    }

                    updateTextsFields(bmiValue)

                    saveToHistory(bmiValue, mass, height)

                    infoButt.visibility = View.VISIBLE


                } catch (e: Exception) {
                    clearTextsFields(this)
                    Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                clearTextsFields(this)
                Toast.makeText(this@MainActivity, "puste editable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putCharSequence(getString(R.string.saved_result_key), bmi_result.text)
        outState?.putBoolean(getString(R.string.is_info_button_key), infoButt.visibility == View.VISIBLE)
        outState?.putBoolean(getString(R.string.isMetric_key), isMetric)
        outState?.putString("history", Gson().toJson(historyList))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.getCharSequence(getString(R.string.saved_result_key)).toString().toDoubleOrNull()
            ?.let { updateTextsFields(it) }
        val bool = savedInstanceState!!.getBoolean(getString(R.string.is_info_button_key))
        if (bool)
            infoButt.visibility = View.VISIBLE
        else
            infoButt.visibility = View.INVISIBLE

        isMetric = savedInstanceState.getBoolean(getString(R.string.isMetric_key))
        setUnitsTexts()

        val historyListType = object : TypeToken<MutableList<HistoryElement>>() {}.type
        this.historyList = Gson().fromJson(savedInstanceState.getString("history"), historyListType)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.units_settings -> {
                clearTextsFields(this)
                isMetric = !isMetric
                setUnitsTexts()
            }
            R.id.about_menu -> {
                val intent = Intent(this, AboutMe::class.java)
                startActivity(intent)
            }
            R.id.history_menu ->{
                val intent = Intent(this, HistoryActivity::class.java)
                val historyString = Gson().toJson(historyList)
                intent.putExtra("history_set", historyString)
                startActivity(intent)
            }
            R.id.history_clear -> {
                historyList.clear()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("history", Gson().toJson(historyList))
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        val historyListType = object : TypeToken<MutableList<HistoryElement>>() {}.type
        val stringList = sharedPref.getString("history","")
        if(stringList != "")
        this.historyList = Gson().fromJson(stringList, historyListType)


    }

    private fun updateTextsFields(bmi: Double) {

        val color = getTextColor(bmi)
        val text = getTextString(bmi)


        bmi_text_result.setTextColor(color)
        bmi_result.setTextColor(color)


        bmi_text_result.text = text
        val bmiText = "%.2f".format(bmi)
        bmi_result.text = bmiText.replace(",", ".")
    }

    private fun getTextString(bmi: Double) =
        when (bmi) {
            in MIN_VALUE..18.5 -> {
                getString(R.string.underweight_text)
            }
            in 18.5..25.0 -> {
                getString(R.string.normal_weight_text)
            }
            in 25.0..30.0 -> {
                getString(R.string.overweight_text)
            }
            in 30.0..40.0 -> {
                getString(R.string.obesity_text)
            }
            in 40.0..MAX_VALUE -> {
                getString(R.string.morbid_obesity_text)
            }
            else -> {
                ""
            }
        }


    private fun getTextColor(bmi: Double) =
        when (bmi) {
            in MIN_VALUE..18.5 -> {
                ContextCompat.getColor(this, R.color.rozPompejski)
            }
            in 18.5..25.0 -> {
                ContextCompat.getColor(this, R.color.pomaranczowy)
            }
            in 25.0..30.0 -> {
                ContextCompat.getColor(this, R.color.zolty)
            }
            in 30.0..40.0 -> {
                ContextCompat.getColor(this, R.color.grynszpan)
            }
            in 40.0..MAX_VALUE -> {
                ContextCompat.getColor(this, R.color.lapisLazuli)
            }
            else -> {
                ContextCompat.getColor(this, R.color.black)
            }
        }


    private fun setUnitsTexts() = if (!isMetric) {
            massText.text = getString(R.string.mass_imperial_text)
            heightText.text = getString(R.string.height_imperial_text)
        } else {
            massText.text = getString(R.string.mass_metric_text)
            heightText.text = getString(R.string.height_metric_text)
        }

    private fun saveToHistory(bmi: Double, mass: Int, height: Int) {

            val element = HistoryElement(bmi, mass, height,getTextString(bmi),getTextColor(bmi),massText.text.toString(), heightText.text.toString())

            while(historyList.size > 9)
                historyList.removeAt(0)
            historyList.add(element)


            Toast.makeText(this@MainActivity, historyList.size.toString(), Toast.LENGTH_SHORT).show()
        }

    companion object {
        private fun clearTextsFields(mainActivity: MainActivity) {
            mainActivity.massET.text.clear()
            mainActivity.heightET.text.clear()
            mainActivity.bmi_result.text = ""
            mainActivity.bmi_text_result.text = ""
            mainActivity.infoButt.visibility = View.INVISIBLE
        }
    }
}


