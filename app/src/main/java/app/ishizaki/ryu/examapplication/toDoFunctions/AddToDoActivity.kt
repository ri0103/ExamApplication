package app.ishizaki.ryu.examapplication.toDoFunctions

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import app.ishizaki.ryu.examapplication.*
import app.ishizaki.ryu.examapplication.Notification
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.text.SimpleDateFormat
import java.time.*
import java.util.*


class AddToDoActivity : AppCompatActivity(){

    var colorSaved: Int = R.drawable.bg_resource1
    var realm: Realm = Realm.getDefaultInstance()
    var dateStartSet: Date = Date(System.currentTimeMillis())
    var dateEndSet: Date = Date(System.currentTimeMillis())
    val calendarDefault: Calendar = Calendar.getInstance()
    val dateFormatShow = SimpleDateFormat("M/dd(E)", Locale.JAPANESE)
    val timeFormatShow = SimpleDateFormat("H:mm~", Locale.JAPANESE)
    val timeFormatError = SimpleDateFormat("H:mm", Locale.JAPANESE)
    var numberPickerHistory: Int = 50
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
            }else{
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


        if (ToDoSaved !=null){
            numberPickerHistory = ToDoSaved!!.timeLenght
        }


        timeNumberPicker.minValue = 0
        timeNumberPicker.maxValue = 999
        timeNumberPicker.value = numberPickerHistory

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
        timeAddChoices()
        timeLenghtSelection()
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


        createNotificationChannel()

        scheduleSaveButton.setOnClickListener {

            dateStartSet = calendarDefault.time
            dateEndSet = calendarDefault.time
            dateEndSet.minutes = calendarDefault.get(Calendar.MINUTE)+timeNumberPicker.value

            if (ToDoSaved !=null && ToDoSaved!!.dateTimeEnd > dateStartSet && ToDoSaved!!.dateTimeStart < dateEndSet){
                Toast.makeText(applicationContext, "${timeFormatError.format(ToDoSaved!!.dateTimeStart)}~${timeFormatError.format(ToDoSaved!!.dateTimeEnd)}の「${ToDoSaved!!.subject}」と時間が重なっています", Toast.LENGTH_SHORT).show()
            }else{
                realm.executeTransaction {
                    val newToDo: ToDo = it.createObject(ToDo::class.java, UUID.randomUUID().toString())
                    newToDo.subject = subjectSchedule.text.toString()
                    newToDo.content = contentSchedule.text.toString()
                    newToDo.bgColor = colorSaved
                    newToDo.dateTimeStart = dateStartSet
                    newToDo.dateTimeEnd = dateEndSet
                    newToDo.timeLenght = timeNumberPicker.value
                    newToDo.notificationID = NotificationId.iD+1
                }
                scheduleNotification()
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

    private fun timeAddChoices(){

        addfiveminsButton.setOnClickListener {
            calendarDefault.set(Calendar.MINUTE, calendarDefault.get(Calendar.MINUTE)+5)
            timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)
            showDateText()
        }
        addtenminsButton.setOnClickListener {
            calendarDefault.set(Calendar.MINUTE, calendarDefault.get(Calendar.MINUTE)+10)
            timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)
            showDateText()
        }
        addthirtyminsButton.setOnClickListener {
            calendarDefault.set(Calendar.MINUTE, calendarDefault.get(Calendar.MINUTE)+30)
            timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)
            showDateText()
        }
        addonehourButton.setOnClickListener {
            calendarDefault.set(Calendar.HOUR_OF_DAY, calendarDefault.get(Calendar.HOUR_OF_DAY)+1)
            timeScheduleButton.text = timeFormatShow.format(calendarDefault.time)
            showDateText()
        }
    }

    private fun timeLenghtSelection(){

        selectfifteenminsButton.setOnClickListener {
            timeNumberPicker.value =15
        }
        selectthirtyminsButton.setOnClickListener {
            timeNumberPicker.value =30
        }
        selectfourtyfiveminsButton.setOnClickListener {
            timeNumberPicker.value =45
        }
        selectfiftyminsButton.setOnClickListener {
            timeNumberPicker.value =50
        }
        selectsixtyminsButton.setOnClickListener {
            timeNumberPicker.value =60
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
            colorSaved = R.drawable.bg_resource1
        }
        circleButton2.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_red)
            colorSaved = R.drawable.bg_resource2
        }
        circleButton3.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_orange)
            colorSaved = R.drawable.bg_resource3
        }
        circleButton4.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_yellow)
            colorSaved = R.drawable.bg_resource4
        }
        circleButton5.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_green)
            colorSaved = R.drawable.bg_resource5
        }
        circleButton6.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_darkgreen)
            colorSaved = R.drawable.bg_resource6
        }
        circleButton7.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_bluegreen)
            colorSaved = R.drawable.bg_resource7
        }
        circleButton8.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_skyblue)
            colorSaved = R.drawable.bg_resource8
        }
        circleButton9.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_purple)
            colorSaved = R.drawable.bg_resource9
        }
        circleButton10.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_redpurple)
            colorSaved = R.drawable.bg_resource10
        }
        circleButton11.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_lightpink)
            colorSaved = R.drawable.bg_resource11
        }
        circleButton12.setOnClickListener {
            addToDoActivityBG.setBackgroundResource(R.color.bg_purewhite)
            colorSaved = R.drawable.bg_resource12
        }


    }



    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = subjectSchedule.text.toString()
        val message = contentSchedule.text.toString()
        intent.putExtra(titleExtra, "「${title}」の勉強を10分後に開始しましょう！")
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NotificationId.iD,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }


    private fun getTime(): Long
    {
        val calendarNotification = Calendar.getInstance()
        calendarNotification.time = calendarDefault.time
        calendarNotification.add(Calendar.MINUTE, -10)

        return calendarNotification.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "予定通知"
        val desc = "やることリストに登録されている予定の開始10分前に通知を送信します"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }



}