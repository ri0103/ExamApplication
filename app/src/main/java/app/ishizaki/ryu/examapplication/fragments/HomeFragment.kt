package app.ishizaki.ryu.examapplication.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import app.ishizaki.ryu.examapplication.*
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_coverage.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.*
import java.util.*

class HomeFragment : Fragment(){

    var calendar1: Calendar = Calendar.getInstance();

    var yearSavedE: Int? = null
    var monthSavedE: Int? = null
    var dateSavedE: Int? = null
    val realm: Realm = Realm.getDefaultInstance()

    var UntilExamDateSaved = readFirst()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (UntilExamDateSaved !=null) {
            calendar1.set(
                UntilExamDateSaved?.yearE as Int,
                UntilExamDateSaved?.monthE as Int,
                UntilExamDateSaved?.dateE as Int
            )
            var timeMills1: Long = calendar1.timeInMillis
            var currentTimeMills: Long = System.currentTimeMillis()
            var timeDiff: Long = timeMills1 - currentTimeMills;
            timeDiff = timeDiff / 1000;
            timeDiff = timeDiff / 60;
            timeDiff = timeDiff / 60;
            timeDiff = timeDiff / 24;
            timeDiff = timeDiff + 1;
            var str: String = timeDiff.toString() + "日"
            untilExamNumber.setText(str)

            untilExamSelectButton.text="${UntilExamDateSaved?.yearE as Int}年${UntilExamDateSaved?.monthE as Int+1}月${UntilExamDateSaved?.dateE as Int}日~"

        }else{
            untilExamSaveButton.text="保存"
        }


        untilExamSelectButton.setOnClickListener{



            DatePickerDialog(
                requireContext(),

                { _, year, monthOfYear, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }


                    untilExamSelectButton.text="${year}年${monthOfYear+1}月${dayOfMonth}日~"



                    yearSavedE = year
                    monthSavedE = monthOfYear
                    dateSavedE = dayOfMonth

                    calendar1.set(year, monthOfYear, dayOfMonth)
                    var timeMills1: Long = calendar1.timeInMillis
                    var currentTimeMills: Long = System.currentTimeMillis()
                    var timeDiff: Long = timeMills1 - currentTimeMills;
                    timeDiff = timeDiff / 1000;
                    timeDiff = timeDiff / 60;
                    timeDiff = timeDiff / 60;
                    timeDiff = timeDiff / 24;
                    timeDiff = timeDiff + 1;
                    var str: String = timeDiff.toString() + "日"
                    untilExamNumber.setText(str)


                },
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
            ).apply {
            }.show()


        }

        untilExamSaveButton.setOnClickListener{
            realm.executeTransaction{
                if (UntilExamDateSaved !=null){
                    UntilExamDateSaved!!.yearE = yearSavedE as Int
                    UntilExamDateSaved!!.monthE = monthSavedE as Int
                    UntilExamDateSaved!!.dateE = dateSavedE as Int
                }else {
                    val newExamDate: UntilExamDate =
                        it.createObject(UntilExamDate::class.java, UUID.randomUUID().toString())
                    newExamDate.yearE = yearSavedE as Int
                    newExamDate.monthE = monthSavedE as Int
                    newExamDate.dateE = dateSavedE as Int
                }
            }

            calendar1.set(yearSavedE as Int, monthSavedE as Int, dateSavedE as Int)
            var timeMills1: Long = calendar1.timeInMillis
            var currentTimeMills: Long = System.currentTimeMillis()
            var timeDiff: Long = timeMills1 - currentTimeMills;
            timeDiff = timeDiff / 1000;
            timeDiff = timeDiff / 60;
            timeDiff = timeDiff / 60;
            timeDiff = timeDiff / 24;
            timeDiff = timeDiff + 1;
            var str: String = timeDiff.toString() + "日"
            untilExamNumber.setText(str)


        }






    }


    fun readFirst(): UntilExamDate? {
        return realm.where(UntilExamDate::class.java).findFirst()
    }





//    fun showDatePickerDialog() {
//        val calendar: Calendar = Calendar.getInstance()
//
//
//        DatePickerDialog(
//            requireContext(),
//
//            { _, year, monthOfYear, dayOfMonth ->
//                Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }
//
//
//                untilExamSelectButton.text="${year}年${monthOfYear+1}月${dayOfMonth}日"
//
//
//
//                yearSavedE = year
//                monthSavedE = monthOfYear
//                dateSavedE = dayOfMonth
//
//                calendar1.set(year, monthOfYear, dayOfMonth)
//
//
//                var timeMills1: Long = calendar1.timeInMillis
//                var currentTimeMills: Long = System.currentTimeMillis()
//                var timeDiff: Long = timeMills1 - currentTimeMills;
//                timeDiff = timeDiff / 1000;
//                timeDiff = timeDiff / 60;
//                timeDiff = timeDiff / 60;
//                timeDiff = timeDiff / 24;
//                timeDiff = timeDiff + 1;
//                var str: String=timeDiff.toString() + "日"
//                untilExamNumber.setText( str )
//
//
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).apply {
//        }.show()
//    }




}