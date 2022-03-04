package app.ishizaki.ryu.examapplication

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.util.concurrent.atomic.AtomicInteger

object NotificationId {
    private val atmi: AtomicInteger = AtomicInteger(0)
    val iD: Int
        get() = atmi.incrementAndGet()
}

const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setSmallIcon(R.drawable.todo_icon_fill)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(NotificationId.iD, notification)
    }

}

