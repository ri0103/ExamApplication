package app.ishizaki.ryu.examapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class AddToDoActivity : AppCompatActivity(), DatePickerDialogClass.OnSelectedDateListner, TimePickerDialogClass.OnSelectedTimeListner, TimePickerDialogClass2.OnSelectedTime2Listner{

    var yearSaved: Int? = null
    var monthSaved :Int? = null
    var dateSaved: Int? = null
    var hourSaved: Int? = null
    var minuteSaved: Int? = null
    var year2Saved: Int? = null
    var month2Saved :Int? = null
    var date2Saved: Int? = null
    var hour2Saved: Int? = null
    var minute2Saved: Int? = null
    var realm: Realm = Realm.getDefaultInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)




        dateScheduleButton.setOnClickListener {
            showDatePickerDialog()
        }

        timeScheduleButton.setOnClickListener{
            showTimePickerDialog()
        }
        endTimeScheduleButton.setOnClickListener {
            showTimePickerDialog2()
        }



        scheduleSaveButton.setOnClickListener {
            if (yearSaved != null && monthSaved != null && dateSaved != null && hourSaved != null && minuteSaved != null && year2Saved != null && month2Saved != null && date2Saved != null && hour2Saved != null && minute2Saved != null){
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
                    val zdt = intToDateTime.atZone((ZoneId.systemDefault()))
                    val date = Date.from(zdt.toInstant())
                    newToDo.dateTime = date

                    newToDo.year2 = year2Saved as Int
                    newToDo.month2 = month2Saved as Int
                    newToDo.day2 = date2Saved as Int
                    newToDo.hour2 = hour2Saved as Int
                    newToDo.minute2 = minute2Saved as Int

                    val intToDateTime2: LocalDateTime = LocalDateTime.of(year2Saved!!,month2Saved!!+1,date2Saved!!,hour2Saved!!,minute2Saved!!)
                    val zdt2 = intToDateTime2.atZone((ZoneId.systemDefault()))
                    val date2 = Date.from(zdt2.toInstant())
                    newToDo.dateTime2 = date2
                }
            }



finish()


        }


        cancelAddingToDo.setOnClickListener{
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

    private fun showTimePickerDialog2(){

        val timePickerDialogClass2 = TimePickerDialogClass2()
        timePickerDialogClass2.show(supportFragmentManager, null)

    }


    override fun selectedDate(year: Int, month: Int, date: Int) {

        dateScheduleButton.text="${month+1}月${date}日"

        yearSaved = year
        monthSaved = month
        dateSaved = date
    }


    override fun selectedTime(hourOfDay: Int, minute: Int) {

        timeScheduleButton.text="${hourOfDay}時${minute}分"


        hourSaved = hourOfDay
        minuteSaved = minute

    }

    override fun selectedTime2(hourOfDay2: Int, minute2: Int) {

        endTimeScheduleButton.text="${hourOfDay2}時${minute2}分"


        hour2Saved = hourOfDay2
        minute2Saved = minute2

    }







}