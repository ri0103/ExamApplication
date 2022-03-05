package app.ishizaki.ryu.examapplication.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.ishizaki.ryu.examapplication.*
import app.ishizaki.ryu.examapplication.toDoFunctions.AddToDoActivity
import app.ishizaki.ryu.examapplication.toDoFunctions.ToDo
import app.ishizaki.ryu.examapplication.toDoFunctions.ToDoAdapter
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.fragment_todo.*

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
            adapter = ToDoAdapter(toDoList, object : ToDoAdapter.OnButtonClickListener {
                override fun onButtonClick(item: ToDo) {

//                    Toast.makeText(getActivity(),"リスナーの設置テスト",Toast.LENGTH_SHORT).show()

                }
            }, true, requireContext())

        }

        emptyTextToDo.isVisible = toDoList.isEmpty()




        toDoTitleText.setOnLongClickListener {
            AlertDialog.Builder(this.requireContext())
                .setTitle("予定の一括削除")
                .setMessage("すべての予定を削除してもよろしいですか。")
                .setPositiveButton("はい"){ _,_ ->
                    deleteToDoFromRealm()
                }
                .setNegativeButton("キャンセル"){_,_ -> }
                .show()

            true
        }



        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter = ToDoAdapter(
            toDoList,
            object : ToDoAdapter.OnButtonClickListener {
                override fun onButtonClick(item: ToDo) {} },
            true, requireContext()))

        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView1)


        fab.setOnClickListener {
            val intent = Intent(activity, AddToDoActivity::class.java)
            activity?.startActivity(intent)
        }



    }

    override fun onResume() {
        super.onResume()

        realm.addChangeListener{

            emptyTextToDo.isVisible = toDoList.isEmpty()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        realm.removeAllChangeListeners()

    }



    fun readAll(): RealmResults<ToDo> {
        return realm.where(ToDo::class.java).findAll().sort("dateTimeStart", Sort.ASCENDING)
    }

//    private fun refreshFragment(context: Context?){
//        context?.let {
//            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
//            fragmentManager?.let {
//                val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container)
//                currentFragment?.let{
//                    val fragmentTransaction = fragmentManager.beginTransaction()
//                    fragmentTransaction.detach(it)
//                    fragmentTransaction.attach(it)
//                    fragmentTransaction.commit()
//                }
//            }
//        }
//    }



    fun deleteToDoFromRealm() {
        val task = realm.where(ToDo::class.java).findAll()
        realm.executeTransaction {
                task.deleteAllFromRealm()
        }
    }

//    private fun cancelNotification()
//    {
//        val intent = Intent(requireContext(), Notification::class.java)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            requireContext(),
//            NotificationId.iD,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        pendingIntent.cancel()
//    }

//    private fun cancelNotification(){
////        val alarmManager = getSystemService(requireContext().ALARM_SERVICE) as AlarmManager
//        val intent = Intent(requireContext(), Notification::class.java)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            requireContext(),
//            NotificationId.iD,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
////        alarmManager.cancel(pendingIntent)
//        pendingIntent.cancel()
//        Toast.makeText(activity, "通知はキャンセルされました", Toast.LENGTH_SHORT).show()
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

                val intent = Intent(activity, Notification::class.java)
                val notificationID = toDoList[viewHolder.adapterPosition]?.notificationID
                val pendingIntent = notificationID?.let {
                    PendingIntent.getBroadcast(activity,
                        it, intent, PendingIntent.FLAG_NO_CREATE)
                }

                val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(pendingIntent)
                pendingIntent?.cancel()

                realm.executeTransaction{
                    val id = toDoList[viewHolder.adapterPosition]?.id
                    val task = realm.where(ToDo::class.java).equalTo("id", id).findFirst()
                    if (task != null) {
                        task.deleteFromRealm()
                    }
                }

                toDoList = realm.where(ToDo::class.java).findAll().sort("dateTimeStart", Sort.ASCENDING)
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
//                val background = ColorDrawable(getResources().getColor(R.color.delete_red))
                val background = getResources().getDrawable(R.drawable.swipe_to_delete_background2)


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


