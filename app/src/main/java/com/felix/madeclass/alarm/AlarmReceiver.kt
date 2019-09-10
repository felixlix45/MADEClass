package com.felix.madeclass.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import android.app.*
import android.os.Build
import android.widget.Toast
import com.felix.madeclass.MainActivity
import com.felix.madeclass.R
import java.util.*


class AlarmReceiver: BroadcastReceiver() {

    private lateinit var pendingIntent: PendingIntent

    override fun onReceive(context: Context, intent: Intent?) {
        showAlarmNotification(context)
    }

    fun AlarmReceiver(){

    }


    private fun showAlarmNotification(context: Context){
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle("Notification")
                .setContentText("Get Back")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
                .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("1", "Notification Day", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
            builder.setChannelId("1")

            notificationManagerCompat.createNotificationChannel(channel)

        }

        val notification: Notification = builder.build()

        notificationManagerCompat.notify(1, notification)
    }

    fun cancelAlarm(context: Context){


        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    fun setRepeatingAlarm(context: Context){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 46)
        calendar.set(Calendar.SECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

//
//        val calendar2 = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, 21)
//        calendar.set(Calendar.MINUTE, 45)
//        calendar.set(Calendar.SECOND, 0)
//
//        val pendingIntent2 = PendingIntent.getBroadcast(context, 1, intent, 0)
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar2.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent2)

        Toast.makeText(context, "Alarm Set Up", Toast.LENGTH_SHORT).show()

    }

}