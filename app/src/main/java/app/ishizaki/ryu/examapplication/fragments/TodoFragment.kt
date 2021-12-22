package app.ishizaki.ryu.examapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
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
    }

    fun readAll(): RealmResults<ToDo> {
        return realm.where(ToDo::class.java).findAll()
    }

}

