package app.ishizaki.ryu.examapplication

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


class CoverageAdapter(
    private val context: RecyclerView,
    private var taskList: OrderedRealmCollection<Coverage>?,
    private val autoUpdate: Boolean
): RealmRecyclerViewAdapter<Coverage, CoverageAdapter.ViewHolder>(taskList, autoUpdate){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val coverageSubjectText: TextView = view.findViewById(R.id.coverageSubjectText)
        val coverageContentText: TextView = view.findViewById(R.id.coverageContentText)
        val coverageScrollView: ScrollView = view.findViewById(R.id.coverageScrollView)
    }


    override fun getItemCount(): Int {
        return taskList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_exam_coverage_data_cell, parent, false
        )
        return  ViewHolder(v)
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coverage: Coverage = taskList?.get(position) ?:return
        holder.coverageSubjectText.text = coverage.subject
        holder.coverageContentText.text = coverage.content

        holder.coverageScrollView.setOnTouchListener { view, motionEvent ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            view.onTouchEvent(motionEvent)
            true
        }


//        holder.coverageContentText.movementMethod = ScrollingMovementMethod()
    }
}