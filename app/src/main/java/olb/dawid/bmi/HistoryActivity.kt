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


        val stringHistory = intent.getStringExtra("history_set")

        val myDataset:MutableList<HistoryElement>
        val historyListType = object : TypeToken<MutableList<HistoryElement>>() {}.type
        if (stringHistory != null)
            myDataset = Gson().fromJson<MutableList<HistoryElement>>(stringHistory, historyListType)
        else
            myDataset = mutableListOf()

        viewAdapter = MyAdapter(myDataset)


        recyclerView = findViewById<RecyclerView>(R.id.history_layout).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }

    }


}



