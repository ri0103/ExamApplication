package app.ishizaki.ryu.examapplication.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.LauncherActivity
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.item_exam_coverage_data_cell.*
import kotlinx.android.synthetic.main.item_schedule_data_cell.*
import java.util.*

class TodoFragment : Fragment() {



    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

//    private var mContext: Context? = null


    var toDoList = readAll()




//    companion object{
//        fun createInstance(context: Context): Fragment{
//            val fragment = TodoFragment()
//            fragment.mContext = context
//            return fragment
//        }
//    }

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
            adapter = ToDoAdapter(this, toDoList, true)
        }

            val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter = ToDoAdapter(recyclerView1, toDoList, true))
            swipeToDismissTouchHelper.attachToRecyclerView(recyclerView1)

        fab.setOnClickListener {
            val intent = Intent(activity, AddToDoActivity::class.java)
            activity?.startActivity(intent)
        }
        }

        fun readAll(): RealmResults<ToDo> {
            return realm.where(ToDo::class.java).findAll().sort("dateTime", Sort.ASCENDING)
        }


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
                        R.drawable.ic_baseline_delete_24
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


