package app.ishizaki.ryu.examapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class UntilExamDate (
    @PrimaryKey
    open var id: String = UUID.randomUUID().toString(),
    open var yearE: Int = 0,
    open var monthE: Int = 0,
    open var dateE: Int = 0,
) : RealmObject()