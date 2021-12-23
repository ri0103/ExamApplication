package app.ishizaki.ryu.examapplication.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_todo.*

class TodoFragment : Fragment() {



    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }





    val toDoList = readAll()


    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<ToDoAdapter.ViewHolder>?=null

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

        fab.setOnClickListener {
            val intent = Intent(activity, AddToDoActivity::class.java)
            activity?.startActivity(intent)
        }


        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)
            }

        }

val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView1)


    }


    


    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("計画を削除")
        builder.setMessage("計画を削除してもよろしいですか。二度と戻すことはできません。")
        builder.setPositiveButton("はい"){dialog, which ->
          val position = viewHolder.adapterPosition
            toDoList.removeAt(position)
            adapter?.notifyItemRemoved(position)
        }
        builder.setNegativeButton("中止"){dialog, which ->
            val position = viewHolder.adapterPosition
            adapter?.notifyItemChanged(position)
        }
        builder.show()
    }

    fun readAll(): RealmResults<ToDo> {
        return realm.where(ToDo::class.java).findAll()
    }



}

