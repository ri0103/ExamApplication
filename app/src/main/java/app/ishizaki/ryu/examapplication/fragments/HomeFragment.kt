package app.ishizaki.ryu.examapplication.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import app.ishizaki.ryu.examapplication.DatePickerDialogClass
import app.ishizaki.ryu.examapplication.R
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.*
import java.util.*

class HomeFragment : Fragment(),DatePickerDialogClass.OnSelectedDateListner{

    var calendar1: Calendar = Calendar.getInstance();
    var yearSavedC: Int? = null
    var monthSavedC: Int? = null
    var dateSavedC: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar1.set(yearSavedC as Int, monthSavedC as Int, dateSavedC as Int)

        untilExamSelectButton.setOnClickListener{
            showDatePickerDialog()
        }


        var timeMills1: Long = calendar1.timeInMillis
        var currentTimeMills: Long = System.currentTimeMillis()
        var timeDiff: Long = timeMills1 - currentTimeMills;

        timeDiff = timeDiff / 1000;
        timeDiff = timeDiff / 60;
        timeDiff = timeDiff / 60;
        timeDiff = timeDiff / 24;

        val untilExamNumberText: TextView = view.findViewById(R.id.untilExamNumber)
        var str: String=timeDiff.toString() + "日"
        untilExamNumberText.setText( str )

    }

    override fun selectedDate(year: Int, month: Int, date: Int) {

        untilExamSelectButton.text="${year}年${month+1}月${date}日"

        yearSavedC = year
        monthSavedC = month
        dateSavedC = date
    }

    private fun showDatePickerDialog(){
        val datePickerDialogClass = DatePickerDialogClass()
        datePickerDialogClass.show(childFragmentManager, null)
    }

}