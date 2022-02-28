package app.ishizaki.ryu.examapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_add_to_do.*
import kotlinx.android.synthetic.main.item_schedule_data_cell.*
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

class AddToDoActivity : AppCompatActivity(){

    var colorSaved: Int = R.color.bg_grey
    var realm: Realm = Realm.getDefaultInstance()
    var dateStartSet: Date = Date(System.currentTimeMillis())
    var dateEndSet: Date = Date(System.currentTimeMillis())
    open val calendarDefault: Calendar = Calendar.getInstance()
    val dateFormatShow = SimpleDateFormat("M/dd (E)", Locale.JAPANESE)
    val timeFormatShow = SimpleDateFormat("H:mm~", Locale.JAPANESE)
    val calendarTestIfFuture: Calendar = Calendar.getInstance()
    var dateTimeStartHistory: Date = Date(System.currentTimeMillis())
    var dateTimeEndHistory: Date = Date(System.currentTimeMillis())
    var ToDoSaved = readFirst()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        if (ToDoSaved !=null){
            if ( Date(System.currentTimeMillis()) > ToDoSaved!!.dateTimeEnd){
                calendarDefault.set(
                    LocalDate.now().year,
                    LocalDate.now().monthValue-1,
                    if (LocalTime.now().hour == 23){
                        LocalDate.now().dayOfMonth+1
                    }else {
                        LocalDate.now().dayOfMonth
                    },
                    if (LocalTime.now().hour == 23){
                        0
                    }else {
                        LocalTime.now().hour + 1
                    },
                    0
                )
            }else {
                calendarDefault.setTime(ToDoSaved!!.dateTimeEnd)
            }

        }else{
            calendarDefault.set(
                LocalDate.now().year,
                LocalDate.now().monthValue-1,
                if (LocalTime.now().hour == 23){
                    LocalDate.now().dayOfMonth+1
                }else {
                    LocalDate.now().dayOfMonth
                },
                if (LocalTime.now().hour == 23){
                    0
                }else {
                    LocalTime.now().hour + 1
                },
                0
            )
        }


        showDateText()
        timeScheduleButton.text= timeFormatShow.format(calendarDefault.time)


        timeNumberPicker.minValue = 0
        timeNumberPicker.maxValue = 100
        timeNumberPicker.value = 50


        if (ToDoSaved !=null){
            dateTimeStartHistory = ToDoSaved!!.dateTimeStart
            dateTimeEndHistory = ToDoSaved!!.dateTimeEnd
        }


        dateScheduleButton.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    Calendar.getInstance().apply { set(year, month, dayOfMonth) }

                    calendarDefault.set(year, month, dayOfMonth)

                    showDateText()

                },
                calendarDefault.get(Calendar.YEAR),
                calendarDefault.get(Calendar.MONTH),
                calendarDefault.get(Calendar.DAY_OF_MONTH)

            ).apply {
            }.show()

        }

        timeScheduleButton.setOnClickListener{
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hour);set(Calendar.MINUTE, minute)
                    }

                    calendarDefault.set(Calendar.HOUR_OF_DAY, hour)
                    calendarDefault.set(Calendar.MINUTE, minute)

                    timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)

                },
                calendarDefault.get(Calendar.HOUR_OF_DAY),
                calendarDefault.get(Calendar.MINUTE),
                true
            ).apply {
            }.show()

        }

        dateSelectChoices()

        setBackgroundColors()

        val subjectsAuto = resources.getStringArray(R.array.subjects)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, subjectsAuto)
        subjectSchedule.setAdapter(adapter)
        subjectSchedule.setCompletionHint("科目を選択してください")
        subjectSchedule.setOnFocusChangeListener { view, b ->
            subjectSchedule.showDropDown()
        }
        subjectSchedule.dropDownHeight = 600


        scheduleSaveButton.setOnClickListener {

            dateStartSet = calendarDefault.time
            dateEndSet = calendarDefault.time
            dateEndSet.minutes = calendarDefault.get(Calendar.MINUTE)+timeNumberPicker.value

            if (ToDoSaved !=null){

                if (dateTimeEndHistory > dateStartSet && dateTimeStartHistory< dateEndSet){
                    Toast.makeText(applicationContext, "他の予定と重なっています！！", Toast.LENGTH_SHORT).show()
                }else{
                    realm.executeTransaction {
                        val newToDo: ToDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
                        newToDo.subject = subjectSchedule.text.toString()
                        newToDo.content = contentSchedule.text.toString()
                        newToDo.bgColor = colorSaved
                        newToDo.dateTimeStart = dateStartSet
                        newToDo.dateTimeEnd = dateEndSet
                    }
                    finish()
                }

            }else{
                realm.executeTransaction {
                    val newToDo: ToDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
                    newToDo.subject = subjectSchedule.text.toString()
                    newToDo.content = contentSchedule.text.toString()
                    newToDo.bgColor = colorSaved
                    newToDo.dateTimeStart = dateStartSet
                    newToDo.dateTimeEnd = dateEndSet
                }
                finish()
            }

        }




        cancelAddingToDo.setOnClickListener{
            finish()
        }

    }


    fun readFirst(): ToDo? {
        return realm.where(ToDo::class.java).sort("dateTimeEnd", Sort.DESCENDING).findFirst()
    }

//    private fun showDatePickerDialog(){
//
//        val datePickerDialogClass = DatePickerDialogClass()
//        datePickerDialogClass.show(supportFragmentManager, null)
//
//    }
//
//    private fun showTimePickerDialog(){
//
//        val timePickerDialogClass = TimePickerDialogClass()
//        timePickerDialogClass.show(supportFragmentManager, null)
//
//    }



//    override fun selectedDate(year: Int, month: Int, date: Int) {
//
////        calendarTestIfFuture.set(year, month, date)
//
////        if((calendarTestIfFuture.timeInMillis - System.currentTimeMillis() ) / 86400000 < 0){
////            Toast.makeText(applicationContext, "過去の日付は選択できません(m_m)", Toast.LENGTH_SHORT).show()
////        }else {
//        calendarDefault.set(year, month, date)
//        showDateText()
////        }
//    }
//
//
//    override fun selectedTime(hourOfDay: Int, minute: Int) {
//
//        calendarDefault.set(Calendar.HOUR_OF_DAY, hourOfDay)
//        calendarDefault.set(Calendar.MINUTE, minute)
//        timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)
//
//    }

    private fun dateSelectChoices(){

        selectTodayButton.setOnClickListener {
            calendarDefault.set(
                LocalDate.now().year,
                LocalDate.now().monthValue-1,
                LocalDate.now().dayOfMonth
            )
            showDateText()
        }
        selectTomorrowButton.setOnClickListener {
            calendarDefault.set(
                LocalDate.now().year,
                LocalDate.now().monthValue-1,
                LocalDate.now().dayOfMonth+1
            )
            showDateText()
        }
        selectNextTomorrowButton.setOnClickListener {
            calendarDefault.set(
                LocalDate.now().year,
                LocalDate.now().monthValue-1,
                LocalDate.now().dayOfMonth+2
            )
            showDateText()
        }
        selectNextWeekButton.setOnClickListener {
            calendarDefault.set(
                LocalDate.now().year,
                LocalDate.now().monthValue-1,
                LocalDate.now().dayOfMonth+7
            )
            showDateText()
        }
    }

    private fun showDateText(){
        if (LocalDate.now().year == calendarDefault.get(Calendar.YEAR) &&
            LocalDate.now().monthValue == calendarDefault.get(Calendar.MONTH)+1 &&
            LocalDate.now().dayOfMonth == calendarDefault.get(Calendar.DAY_OF_MONTH)){
            dateScheduleButton.text = "今日"
        }else{
            dateScheduleButton.text = dateFormatShow.format(calendarDefault.time)
        }
    }

    private fun setBackgroundColors(){

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



}