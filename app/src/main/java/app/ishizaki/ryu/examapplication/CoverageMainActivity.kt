package app.ishizaki.ryu.examapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_coverage_main.*
import kotlinx.android.synthetic.main.activity_main.*

class CoverageMainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    val coverageList = readAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coverage_main)

        val adapter = CoverageAdapter(this, coverageList, true)
        recyclerView2.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView2.adapter = adapter

        fabCoverage.setOnClickListener {
            val intent = Intent(this, AddCoverageActivity::class.java)
            startActivity(intent)
        }
    }

    fun readAll(): RealmResults<Coverage> {
        return realm.where(Coverage::class.java).findAll()
    }

}