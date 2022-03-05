package app.ishizaki.ryu.examapplication.toDoFunctions

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class ToDo(
    @PrimaryKey
    open var id: String = UUID.randomUUID().toString(),
    open var notificationID: Int = 0,
    open var subject: String = "",
    open var content: String = "",
    open var bgColor: Int = 0,
    open var timeLenght: Int = 0,
    open var dateTimeStart: Date = Date(System.currentTimeMillis()),
    open var dateTimeEnd: Date = Date(System.currentTimeMillis())
) : RealmObject()