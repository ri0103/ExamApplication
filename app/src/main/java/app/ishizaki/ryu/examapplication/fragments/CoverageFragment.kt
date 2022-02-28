package app.ishizaki.ryu.examapplication.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_coverage.*

class CoverageFragment : Fragment() {


    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    var coverageList = readAll()


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

        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter = CoverageAdapter(recyclerView2, coverageList, true))
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView2)



        fabCoverage.setOnClickListener {
            val intent = Intent(activity, AddCoverageActivity::class.java)
            activity?.startActivity(intent)
        }


    }

    fun readAll(): RealmResults<Coverage> {
        return realm.where(Coverage::class.java).findAll().sort("createdTime", Sort.ASCENDING)
    }



    private fun getSwipeToDismissTouchHelper(adapter: CoverageAdapter)=
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
                    val id = coverageList[viewHolder.adapterPosition]?.id
                    val task = realm.where(Coverage::class.java).equalTo("id", id).findFirst()
                    if (task != null) {
                        task.deleteFromRealm()
                    }
                }
                coverageList = realm.where(Coverage::class.java).findAll().sort("createdTime", Sort.ASCENDING)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            @SuppressLint("ResourceType")
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
//                val background = ColorDrawable(getResources().getColor(R.color.delete_red))
                val background = getResources().getDrawable(R.drawable.swipe_to_delete_background)


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