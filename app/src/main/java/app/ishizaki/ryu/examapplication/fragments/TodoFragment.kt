package app.ishizaki.ryu.examapplication.fragments

import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_todo.*
import java.util.*
import javax.security.auth.Subject

class TodoFragment : Fragment() {



    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    var toDoList = readAll()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView1.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ToDoAdapter(this, toDoList, object : ToDoAdapter.OnButtonClickListener {
                override fun onButtonClick(item: ToDo) {
                    Toast.makeText(getActivity(),"リスナーの設置テスト",Toast.LENGTH_SHORT).show()
                }
            }, true)

        }

        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter = ToDoAdapter(recyclerView1, toDoList,object : ToDoAdapter.OnButtonClickListener {
            override fun onButtonClick(item: ToDo) {} }, true))
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView1)

        fab.setOnClickListener {
            val intent = Intent(activity, AddToDoActivity::class.java)
            activity?.startActivity(intent)
        }



    }

    fun readAll(): RealmResults<ToDo> {
        return realm.where(ToDo::class.java).findAll().sort("dateTime", Sort.ASCENDING)
    }

//    fun createFooter() {
//        val calendarForFooter: Calendar = Calendar.getInstance()
//        calendarForFooter.set(10000, 1, 1, 0, 0)
//        val dateForFooter: Date = calendarForFooter.time
//            createCell("", "", dateForFooter)
//    }
//
//    fun createCell(subject: String, content: String, datetime: Date) {
//        realm.executeTransaction {
//            val toDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
//            toDo.subject = subject
//            toDo.content = content
//            toDo.dateTime = datetime
//        }
//    }


    private fun getSwipeToDismissTouchHelper(adapter: ToDoAdapter)=
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.RIGHT
        ){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                realm.executeTransaction{
                    val id = toDoList[viewHolder.adapterPosition]?.id
                    val task = realm.where(ToDo::class.java).equalTo("id", id).findFirst()
                    if (task != null) {
                        task.deleteFromRealm()
                    }
                }
                toDoList = realm.where(ToDo::class.java).findAll().sort("dateTime", Sort.ASCENDING)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                val itemView = viewHolder.itemView
                val background = ColorDrawable(getResources().getColor(R.color.delete_red))

//                val deleteIcon = AppCompatResources.getDrawable(
//                    activity,
//                    R.drawable.ic_baseline_delete_24
//                )

                val deleteIcon = activity?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.ic_outline_delete_24
                    )
                }




                val iconMarginVertical = (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) /2
                deleteIcon.setBounds(
                    itemView.left + iconMarginVertical,
                    itemView.top + iconMarginVertical,
                    itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                    itemView.bottom - iconMarginVertical
                )

                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.right + dX.toInt(),
                    itemView.bottom
                )
                background.draw(c)
                deleteIcon.draw(c)
            }

        })


}


