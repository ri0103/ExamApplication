package app.ishizaki.ryu.examapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    val toDoList = readAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ToDoAdapter(this, toDoList, true)
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.adapter = adapter

        fab.setOnClickListener {
            val intent = Intent(this, AddToDoActivity::class.java)
            startActivity(intent)
        }
    }

    fun readAll(): RealmResults<ToDo> {
        return realm.where(ToDo::class.java).findAll()
    }

}