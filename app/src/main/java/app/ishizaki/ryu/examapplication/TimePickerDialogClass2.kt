package app.ishizaki.ryu.examapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerDialogClass2: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    interface OnSelectedTime2Listner{
        fun selectedTime2(hourOfDay2: Int, minute2: Int)
    }

    private lateinit var listner: OnSelectedTime2Listner

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSelectedTime2Listner) {
            listner = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = Date().time
        val context = context
        return when{
            context !=null -> {
                TimePickerDialog(
                    context,
                    this,
                    calendar2.get(Calendar.HOUR_OF_DAY),
                    calendar2.get(Calendar.MINUTE),
                    true
                )
            }
            else -> super.onCreateDialog(savedInstanceState)

        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay2: Int, minute2: Int) {
        listner.selectedTime2(hourOfDay2, minute2)
    }

    companion object {
        private val TAG = TimePickerDialogClass2::class.java.simpleName
    }


}