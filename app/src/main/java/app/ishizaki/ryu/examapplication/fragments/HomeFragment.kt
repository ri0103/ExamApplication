package app.ishizaki.ryu.examapplication.fragments

import android.annotation.SuppressLint
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
import androidx.core.view.isVisible
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


        untilExamSaveButton.isVisible = false


        if (UntilExamDateSaved !=null) {
            yearSavedE = UntilExamDateSaved!!.yearE
            monthSavedE = UntilExamDateSaved!!.monthE
            dateSavedE = UntilExamDateSaved!!.dateE
            countDownShow()
        }


        untilExamSelectButton.setOnClickListener{

            DatePickerDialog(
                requireContext(),

                { _, year, monthOfYear, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }

                    yearSavedE = year
                    monthSavedE = monthOfYear
                    dateSavedE = dayOfMonth

                    countDownShow()

                },
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
            ).apply {
            }.show()

            untilExamSaveButton.isVisible = true

        }

        untilExamSaveButton.setOnClickListener{

            realm.executeTransaction{
                if (UntilExamDateSaved !=null){
                    UntilExamDateSaved!!.yearE = yearSavedE as Int
                    UntilExamDateSaved!!.monthE = monthSavedE as Int
                    UntilExamDateSaved!!.dateE = dateSavedE as Int
                }else {
                    val newExamDate: UntilExamDate = it.createObject(UntilExamDate::class.java, UUID.randomUUID().toString())
                    newExamDate.yearE = yearSavedE as Int
                    newExamDate.monthE = monthSavedE as Int
                    newExamDate.dateE = dateSavedE as Int
                }
            }

            untilExamSaveButton.isVisible = false


        }


    }


    fun readFirst(): UntilExamDate? {
        return realm.where(UntilExamDate::class.java).findFirst()
    }


    fun countDownShow(){

        calendar1.set(yearSavedE as Int, monthSavedE as Int, dateSavedE as Int)
        var timeMills1: Long = calendar1.timeInMillis
        var currentTimeMills: Long = System.currentTimeMillis()
        var timeDiff: Long = timeMills1 - currentTimeMills;
        timeDiff = timeDiff / 1000;
        timeDiff = timeDiff / 60;
        timeDiff = timeDiff / 60;


        if (timeDiff<1){
            untilExamNumber.setText("!試験期間中!")
            untilExamNumber.textSize = 60F
            untilExamTitle.isVisible = false
            untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
        }
        else{
            timeDiff = timeDiff / 24;
            timeDiff = timeDiff + 1

            if(timeDiff<4){
                var str: String = timeDiff.toString() + "日"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 130F
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.delete_red))
            }else{
                var str: String = timeDiff.toString() + "日"
                untilExamNumber.setText(str)
                untilExamNumber.textSize = 100F
                untilExamTitle.isVisible = true
                untilExamNumber.setTextColor(getResources().getColor(R.color.blackorwhite))
            }
        }

        untilExamSelectButton.text="${yearSavedE as Int}年${monthSavedE as Int+1}月${dateSavedE as Int}日~"

    }

}