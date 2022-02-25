package app.ishizaki.ryu.examapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.time.*
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
    val calendarDefault: Calendar = Calendar.getInstance()
    val calendarToday: Calendar = Calendar.getInstance()
    val calendarTomorrow: Calendar = Calendar.getInstance()
    val calendarNextTomorrow: Calendar = Calendar.getInstance()
    val calendarNextWeek: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        calendarDefault.set(
            LocalDate.now().year,
            LocalDate.now().monthValue-1,
            if (LocalTime.now().hour !=23){
                LocalDate.now().dayOfMonth
            }else {
                LocalDate.now().dayOfMonth+1
            },
            if (LocalTime.now().hour !=23){
                LocalTime.now().hour + 1
            }else {
                0
            },
            0
        )

        yearSaved = calendarDefault.get(Calendar.YEAR)
        monthSaved = calendarDefault.get(Calendar.MONTH)+1
        dateSaved = calendarDefault.get(Calendar.DAY_OF_MONTH)
        hourSaved = calendarDefault.get(Calendar.HOUR_OF_DAY)
        minuteSaved = calendarDefault.get(Calendar.MINUTE)

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

    private fun dateSelectChoices(){
        calendarToday.set(
            LocalDate.now().year,
            LocalDate.now().monthValue-1,
            LocalDate.now().dayOfMonth
        )
        calendarTomorrow.set(
            LocalDate.now().year,
            LocalDate.now().monthValue-1,
            LocalDate.now().dayOfMonth+1
        )
        calendarNextTomorrow.set(
            LocalDate.now().year,
            LocalDate.now().monthValue-1,
            LocalDate.now().dayOfMonth+2
        )
        calendarNextWeek.set(
            LocalDate.now().year,
            LocalDate.now().monthValue-1,
            LocalDate.now().dayOfMonth+7
        )

        selectTodayButton.setOnClickListener {
            yearSaved = calendarToday.get(Calendar.YEAR)
            monthSaved = calendarToday.get(Calendar.MONTH)+1
            dateSaved = calendarToday.get(Calendar.DAY_OF_MONTH)
            dateScheduleButton.text="${monthSaved}月${dateSaved}日"
        }
        selectTomorrowButton.setOnClickListener {
            yearSaved = calendarTomorrow.get(Calendar.YEAR)
            monthSaved = calendarTomorrow.get(Calendar.MONTH)+1
            dateSaved = calendarTomorrow.get(Calendar.DAY_OF_MONTH)
            dateScheduleButton.text="${monthSaved}月${dateSaved}日"
        }
        selectNextTomorrowButton.setOnClickListener {
            yearSaved = calendarNextTomorrow.get(Calendar.YEAR)
            monthSaved = calendarNextTomorrow.get(Calendar.MONTH)+1
            dateSaved = calendarNextTomorrow.get(Calendar.DAY_OF_MONTH)
            dateScheduleButton.text="${monthSaved}月${dateSaved}日"
        }
        selectNextWeekButton.setOnClickListener {
            yearSaved = calendarNextWeek.get(Calendar.YEAR)
            monthSaved = calendarNextWeek.get(Calendar.MONTH)+1
            dateSaved = calendarNextWeek.get(Calendar.DAY_OF_MONTH)
            dateScheduleButton.text="${monthSaved}月${dateSaved}日"
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