package app.ishizaki.ryu.examapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AddToDoActivity : AppCompatActivity(), DatePickerDialogClass.OnSelectedDateListner, TimePickerDialogClass.OnSelectedTimeListner {

    var yearSaved: Int? = null
    var monthSaved :Int? = null
    var dateSaved: Int? = null
    var hourSaved: Int? = null
    var minuteSaved: Int? = null
    var realm: Realm = Realm.getDefaultInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)




        dateScheduleButton.setOnClickListener {
            showDatePickerDialog()
        }

        timeScheduleButton.setOnClickListener {
            showTimePickerDialog()
        }

        scheduleSaveButton.setOnClickListener {
            if (yearSaved != null && monthSaved != null && dateSaved != null && hourSaved != null && minuteSaved != null){
                realm.executeTransaction{
                    val newToDo: ToDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
                    newToDo.subject = subjectSchedule.text.toString()
                    newToDo.content = contentSchedule.text.toString()
                    newToDo.year = yearSaved as Int
                    newToDo.month = monthSaved as Int
                    newToDo.day = dateSaved as Int
                    newToDo.hour = hourSaved as Int
                    newToDo.minute = minuteSaved as Int
                    val intToDateTime: LocalDateTime = LocalDateTime.of(yearSaved!!,monthSaved!!+1,dateSaved!!,hourSaved!!,minuteSaved!!)
                    val zdt = intToDateTime.atZone(ZoneId.systemDefault())
                    val date = Date.from(zdt.toInstant())
                    newToDo.dateTime = date
                }
            }

finish()


        }

    }


    private fun showDatePickerDialog(){

        val datePickerDialogClass = DatePickerDialogClass()
        datePickerDialogClass.show(supportFragmentManager, null)

    }

    private fun showTimePickerDialog(){

        val timePickerDialogClass = TimePickerDialogClass()
        timePickerDialogClass.show(supportFragmentManager, null)

    }

    override fun selectedDate(year: Int, month: Int, date: Int) {

        selectedDateToDo.text="${month+1}月${date}日"

        yearSaved = year
        monthSaved = month
        dateSaved = date
    }


    override fun selectedTime(hourOfDay: Int, minute: Int) {

        selectedTimeToDo.text="${hourOfDay}時${minute}分"

        hourSaved=hourOfDay
        minuteSaved=minute
    }



}