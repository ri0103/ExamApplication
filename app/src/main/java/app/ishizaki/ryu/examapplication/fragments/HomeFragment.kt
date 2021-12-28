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
import app.ishizaki.ryu.examapplication.R
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.*
import java.util.*

class HomeFragment : Fragment(){


    var calendar1: Calendar = Calendar.getInstance();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        untilExamSelectButton.setOnClickListener{
            showDatePickerDialog()
        }

    }

    fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()


        DatePickerDialog(
            requireContext(),

            { _, year, monthOfYear, dayOfMonth ->
                Calendar.getInstance().apply { set(year, monthOfYear, dayOfMonth) }


                untilExamSelectButton.text="${year}年${monthOfYear+1}月${dayOfMonth}日"

                calendar1.set(year, monthOfYear, dayOfMonth)


                var timeMills1: Long = calendar1.timeInMillis
                var currentTimeMills: Long = System.currentTimeMillis()
                var timeDiff: Long = timeMills1 - currentTimeMills;

                timeDiff = timeDiff / 1000;
                timeDiff = timeDiff / 60;
                timeDiff = timeDiff / 60;
                timeDiff = timeDiff / 24;
                timeDiff = timeDiff + 1;


                var str: String=timeDiff.toString() + "日"
                untilExamNumber.setText( str )


            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
        }.show()
    }


}