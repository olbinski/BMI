package olb.dawid.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import MyAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_layout)

        viewManager = LinearLayoutManager(this)


        val string_history = intent.getStringExtra("history_set")

        val historyListType = object : TypeToken<MutableList<HistoryElement>>() {}.type
//        if (string_history != null)
            val myDataset = Gson().fromJson<MutableList<HistoryElement>>(string_history, historyListType)


        viewAdapter = MyAdapter(myDataset)


        recyclerView = findViewById<RecyclerView>(R.id.history_layout).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

    }


}



