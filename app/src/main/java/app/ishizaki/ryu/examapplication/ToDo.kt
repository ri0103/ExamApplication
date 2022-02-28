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
    open var bgColor: Int = 0,
    open var dateTimeStart: Date = Date(System.currentTimeMillis()),
    open var dateTimeEnd: Date = Date(System.currentTimeMillis())
) : RealmObject()