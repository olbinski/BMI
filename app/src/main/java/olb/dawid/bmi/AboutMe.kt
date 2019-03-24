package olb.dawid.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.about_me_layout.*

class AboutMe : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_me_layout)
        setSupportActionBar(my_toolbar_about as android.support.v7.widget.Toolbar?)
    }

}






