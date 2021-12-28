package app.ishizaki.ryu.examapplication.fragments

import android.app.AlertDialog
import android.app.LauncherActivity
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
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.item_schedule_data_cell.*
import java.util.*

class TodoFragment : Fragment() {



    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    val toDoList = readAll()



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





    }









        fun readAll(): RealmResults<ToDo> {
            return realm.where(ToDo::class.java).findAll()
        }



}


