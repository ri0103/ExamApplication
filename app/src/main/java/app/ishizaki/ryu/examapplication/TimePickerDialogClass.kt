package app.ishizaki.ryu.examapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerDialogClass: DialogFragment(), TimePickerDialog.OnTimeSetListener {
    interface OnSelectedTimeListner{
        fun selectedTime(hourOfDay: Int, minute: Int)
    }

    private lateinit var listner: OnSelectedTimeListner

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSelectedTimeListner) {
            listner = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        val context = context
        return when{
            context !=null -> {
                TimePickerDialog(
                    context,
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
            }
            else -> super.onCreateDialog(savedInstanceState)

        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listner.selectedTime(hourOfDay, minute)
    }

    companion object {
        private val TAG = TimePickerDialogClass::class.java.simpleName
    }


}