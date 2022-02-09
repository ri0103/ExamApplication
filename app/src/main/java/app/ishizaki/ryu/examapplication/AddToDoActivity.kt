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
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class AddToDoActivity : AppCompatActivity(), DatePickerDialogClass.OnSelectedDateListner, TimePickerDialogClass.OnSelectedTimeListner{

    var yearSaved: Int? = null
    var monthSaved :Int? = null
    var dateSaved: Int? = null
    var hourSaved: Int? = null
    var minuteSaved: Int? = null
    var colorSaved: Int = R.color.bg_grey
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



        scheduleSaveButton.setOnClickListener {


            if (yearSaved != null && monthSaved != null && dateSaved != null && hourSaved != null && minuteSaved != null) {
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

                    val intToDateTime: LocalDateTime = LocalDateTime.of(
                        yearSaved!!,
                        monthSaved!! + 1,
                        dateSaved!!,
                        hourSaved!!,
                        minuteSaved!!
                    )
                    val zdt = intToDateTime.atZone((ZoneId.systemDefault()))
                    val date = Date.from(zdt.toInstant())
                    newToDo.dateTime = date

                }
                finish()
            }else{
                val context: Context = applicationContext
                Toast.makeText(context, "日付、時刻を選択してください", Toast.LENGTH_SHORT).show()
            }





        }


        cancelAddingToDo.setOnClickListener{
            finish()
        }


        timeNumberPicker.minValue = 0
        timeNumberPicker.maxValue = 100
        timeNumberPicker.value = 50


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






}