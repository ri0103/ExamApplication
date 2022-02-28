package app.ishizaki.ryu.examapplication


import android.annotation.SuppressLint
import android.graphics.Color
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
import java.time.LocalDate
import java.util.*

class ToDoAdapter(
    private val context: RecyclerView,
    private var taskList: OrderedRealmCollection<ToDo>?,
    private var listener: OnButtonClickListener,
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
               val v = LayoutInflater.from(parent.context).inflate(
                   R.layout.item_schedule_data_cell, parent, false
               )
               return ViewHolder(v)
    }


    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }




    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo: ToDo = taskList?.get(position) ?:return
        holder.subjectText.text = toDo.subject
        holder.contentText.text = toDo.content

        if (
            LocalDate.now().year == toDo.dateTimeStart.year+1900 &&
            LocalDate.now().monthValue == toDo.dateTimeStart.month+1 &&
            LocalDate.now().dayOfMonth == toDo.dateTimeStart.date
             ){
            holder.dateText.text = SimpleDateFormat("今日  H:mm", Locale.JAPANESE).format(toDo.dateTimeStart)
        }else{
            holder.dateText.text = SimpleDateFormat("M月dd日(E)  H:mm", Locale.JAPANESE).format(toDo.dateTimeStart)
        }

        holder.timeLengthText.text = SimpleDateFormat("～H:mm", Locale.JAPANESE).format(toDo.dateTimeEnd)

        if(
            toDo.dateTimeEnd.time < System.currentTimeMillis()
        ){
            holder.scheduleCell.setBackgroundResource(R.drawable.bg_resource_timeover)
            holder.subjectText.setTextColor(Color.GRAY)
            holder.contentText.setTextColor(Color.GRAY)
            holder.dateText.setTextColor(Color.RED)
            holder.timeLengthText.setTextColor(Color.RED)
        }else{
            holder.scheduleCell.setBackgroundResource(toDo.bgColor)
        }

        holder.scheduleCell.setOnClickListener {
            listener.onButtonClick(toDo)
        }
    }

    interface OnButtonClickListener{
        fun onButtonClick(item: ToDo)
    }



}