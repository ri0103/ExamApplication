package app.ishizaki.ryu.examapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_coverage.*

class CoverageFragment : Fragment() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    val coverageList = readAll()


    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<CoverageAdapter.ViewHolder>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coverage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView2.apply {
            layoutManager = GridLayoutManager (activity, 2, GridLayoutManager.VERTICAL, false)
            adapter = CoverageAdapter(this, coverageList, true)
        }

        fabCoverage.setOnClickListener {
            val intent = Intent(activity, AddCoverageActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    fun readAll(): RealmResults<Coverage> {
        return realm.where(Coverage::class.java).findAll()
    }

}