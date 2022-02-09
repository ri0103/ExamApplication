package app.ishizaki.ryu.examapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

class ToDoAdapter(
    private val context: RecyclerView,
    private var taskList: OrderedRealmCollection<ToDo>?,
    private val autoUpdate: Boolean

): RealmRecyclerViewAdapter<ToDo, ToDoAdapter.ViewHolder>(taskList, autoUpdate){


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val subjectText: TextView = view.findViewById(R.id.subjectText)
        val contentText: TextView = view.findViewById(R.id.contentText)
        val dateText: TextView = view.findViewById(R.id.dateText)
        val scheduleCell: ConstraintLayout = view.findViewById(R.id.scheduleCell1)
        val timeLengthText: TextView = view.findViewById(R.id.timeLenghtText)
    }


    override fun getItemCount(): Int {
        return taskList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_data_cell, parent, false
        )
        return  ViewHolder(v)
    }

    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo: ToDo = taskList?.get(position) ?:return
        holder.subjectText.text = toDo.subject
        holder.contentText.text = toDo.content
        holder.dateText.text = SimpleDateFormat("MM月dd日(E)　HH:mm~ ", Locale.JAPANESE).format(toDo.dateTime)
        holder.scheduleCell.setBackgroundResource(toDo.bgColor)
        holder.timeLengthText.text = toDo.timeLenght + "分間"
    }



}