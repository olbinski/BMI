import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import olb.dawid.bmi.HistoryElement
import olb.dawid.bmi.R
import java.text.SimpleDateFormat
import java.util.*


class MyAdapter(private val myDataset: MutableList<HistoryElement>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(View: View) : RecyclerView.ViewHolder(View) {
        val mass: TextView = itemView.findViewById(R.id.h_element_mass)
        val height: TextView = itemView.findViewById(R.id.h_element_height)
        val data: TextView = itemView.findViewById(R.id.h_element_date)

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val View = LayoutInflater.from(parent.context).inflate(R.layout.history_element, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(View)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val element: HistoryElement = myDataset[position]

        holder.mass.text = element.mass.toString()
        holder.height.text = element.height.toString()
        holder.data.text = SimpleDateFormat("dd.MM.yy  HH:mm", Locale.getDefault()).format(element.date)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size


}