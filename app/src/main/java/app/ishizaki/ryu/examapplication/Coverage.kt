package app.ishizaki.ryu.examapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Coverage (
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var subject: String = "",
    open var content: String = ""
) : RealmObject()