package app.ishizaki.ryu.examapplication

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_coverage.*
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_schedule_data_cell.*
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class AddToDoActivity : AppCompatActivity(), DatePickerDialogClass.OnSelectedDateListner, TimePickerDialogClass.OnSelectedTimeListner{

    var yearSaved: Int? = null
    var monthSaved :Int? = null
    var dateSaved: Int? = null
    var hourSaved: Int? = null
    var minuteSaved: Int? = null
    var colorSaved: Int = R.color.bg_grey
    var realm: Realm = Realm.getDefaultInstance()
    var dateEnd: Date = Date(System.currentTimeMillis())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)



        yearSaved = LocalDate.now().year
        monthSaved = LocalDate.now().monthValue
        dateSaved = LocalDate.now().dayOfMonth
        if (LocalTime.now().hour !=23){
            hourSaved = LocalTime.now().hour + 1
        }else {
            hourSaved = 0
            dateSaved = LocalDate.now().dayOfMonth+1
        }
        minuteSaved = 0

        dateScheduleButton.text="${monthSaved}月${dateSaved}日"
        timeScheduleButton.text="${hourSaved}時${minuteSaved}分"

        timeNumberPicker.minValue = 0
        timeNumberPicker.maxValue = 100
        timeNumberPicker.value = 50




        dateScheduleButton.setOnClickListener {
            showDatePickerDialog()
        }

        timeScheduleButton.setOnClickListener{
            showTimePickerDialog()
        }





        scheduleSaveButton.setOnClickListener {

            dateEnd.year = yearSaved!!
            dateEnd.month = monthSaved!!
            dateEnd.date = dateSaved!!
            dateEnd.hours = hourSaved!!
            dateEnd.minutes = minuteSaved!!+timeNumberPicker.value


            realm.executeTransaction {
                val newToDo: ToDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
                newToDo.subject = subjectSchedule.text.toString()
                newToDo.content = contentSchedule.text.toString()
                newToDo.year = yearSaved as Int
                newToDo.month = monthSaved as Int
                newToDo.day = dateSaved as Int
                newToDo.hour = hourSaved as Int
                newToDo.minute = minuteSaved as Int
                newToDo.bgColor = colorSaved as Int
                newToDo.timeLenght = timeNumberPicker.value.toString()
                newToDo.dateTimeEnd = dateEnd


                val intToDateTime: LocalDateTime = LocalDateTime.of(
                    yearSaved!!,
                    monthSaved!!,
                    dateSaved!!,
                    hourSaved!!,
                    minuteSaved!!
                )
                val zdt = intToDateTime.atZone((ZoneId.systemDefault()))
                val date = Date.from(zdt.toInstant())
                newToDo.dateTime = date

//                    val intToDateTime2: LocalDateTime = LocalDateTime.of(
//                        yearSaved!!,
//                        monthSaved!!,
//                        dateSaved!!,
//                        hourSaved!!,
//                        minuteSaved!! + timeLenghtSaved!!
//                    )
//                    val zdt2 = intToDateTime2.atZone((ZoneId.systemDefault()))
//                    val date2 = Date.from(zdt2.toInstant())
//                    newToDo.endDateTime = date2

            }
            finish()






        }


        cancelAddingToDo.setOnClickListener{
            finish()
        }





        circleButton1.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_grey)
            colorSaved = R.color.bg_grey
        }
        circleButton2.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_red)
            colorSaved = R.color.bg_red
        }
        circleButton3.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_orange)
            colorSaved = R.color.bg_orange
        }
        circleButton4.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_yellow)
            colorSaved = R.color.bg_yellow
        }
        circleButton5.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_green)
            colorSaved = R.color.bg_green
        }
        circleButton6.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_darkgreen)
            colorSaved = R.color.bg_darkgreen
        }
        circleButton7.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_bluegreen)
            colorSaved = R.color.bg_bluegreen
        }
        circleButton8.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_skyblue)
            colorSaved = R.color.bg_skyblue
        }
        circleButton9.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_purple)
            colorSaved = R.color.bg_purple
        }
        circleButton10.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_redpurple)
            colorSaved = R.color.bg_redpurple
        }
        circleButton11.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_lightpink)
            colorSaved = R.color.bg_lightpink
        }
        circleButton12.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_purewhite)
            colorSaved = R.color.bg_purewhite
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

        yearSaved = year
        monthSaved = month +1
        dateSaved = date


        dateScheduleButton.text="${monthSaved}月${dateSaved}日"


    }


    override fun selectedTime(hourOfDay: Int, minute: Int) {


        hourSaved = hourOfDay
        minuteSaved = minute

        timeScheduleButton.text="${hourSaved}時${minuteSaved}分"

    }






}