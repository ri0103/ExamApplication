package app.ishizaki.ryu.examapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.sql.Time
import java.time.chrono.ChronoLocalDateTime
import java.util.*

open class ToDo (
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var subject: String = "",
    open var content: String = "",
    open var year: Int = 0,
    open var month: Int = 0,
    open var day: Int = 0,
    open var hour: Int = 0,
    open var minute: Int = 0,
    open var year2: Int = 0,
    open var month2: Int = 0,
    open var day2: Int = 0,
    open var hour2: Int = 0,
    open var minute2: Int = 0,
    open var dateTime: Date = Date(System.currentTimeMillis()),
    open var dateTime2: Date = Date(System.currentTimeMillis())
) : RealmObject()