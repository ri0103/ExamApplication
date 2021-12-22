package app.ishizaki.ryu.examapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

class ToDoAdapter (
    private val context: Context,
    private var taskList: OrderedRealmCollection<ToDo>?,
    private val autoUpdate: Boolean
): RealmRecyclerViewAdapter<ToDo, ToDoAdapter.ViewHolder>(taskList, autoUpdate){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val subjectText: TextView = view.findViewById(R.id.subjectText)
        val contentText: TextView = view.findViewById(R.id.contentText)
        val dateText: TextView = view.findViewById(R.id.dateText)
    }

    override fun getItemCount(): Int {
        return taskList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_schedule_data_cell, parent, false
        )
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo: ToDo = taskList?.get(position) ?:return
        holder.subjectText.text = toDo.subject
        holder.contentText.text = toDo.content
        holder.dateText.text = SimpleDateFormat("MM月dd日　HH:mm", Locale.JAPANESE).format(toDo.dateTime)

    }
}