package app.ishizaki.ryu.examapplication

import android.graphics.Color
import android.icu.util.LocaleData
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.sql.Time
import java.time.DayOfWeek
import java.time.chrono.ChronoLocalDateTime
import java.time.chrono.JapaneseDate
import java.util.*

open class ToDo(
    @PrimaryKey
    open var id: String = UUID.randomUUID().toString(),
    open var subject: String = "",
    open var content: String = "",
    open var year: Int = 0,
    open var month: Int = 0,
    open var day: Int = 0,
    open var hour: Int = 0,
    open var minute: Int = 0,
    open var bgColor: Int = 0,
    open var timeLenght: String = "",
    open var dateTime: Date = Date(System.currentTimeMillis()),
    open var dateTimeEnd: Date = Date(System.currentTimeMillis())
) : RealmObject()