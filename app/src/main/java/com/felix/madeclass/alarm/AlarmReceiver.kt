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
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.felix.madeclass.BuildConfig
import com.felix.madeclass.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AlarmReceiver: BroadcastReceiver() {

    private lateinit var pendingIntent: PendingIntent
    private lateinit var pendingIntentMovie: PendingIntent

    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra(extra_title)
        val message = intent?.getStringExtra(extra_message)
        val code = intent?.getStringExtra(extra_code)
        showAlarmNotification(context, title?:"", message?:"", code?:"")

    }


    private fun showAlarmNotification(context: Context, title: String, message:String, code: String){

        var newTitle: String?
        var newMessage: String?

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder : NotificationCompat.Builder
        Log.d("ALARM", code)
        if(code == "movie"){
            val date: Date = Calendar.getInstance().time
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            val stringDate:String= df.format(date)

            AndroidNetworking.get(context.resources.getString(R.string.url_today_movie, BuildConfig.API_KEY, stringDate, stringDate))
                .setPriority(Priority.IMMEDIATE)
                .build()
                    .getAsJSONObject(object: JSONObjectRequestListener{
                        override fun onResponse(response: JSONObject?) {
                            if(response!= null){
                                val resultArr = response.getJSONArray("results")
                                val movieObj = resultArr.getJSONObject(0)
                                newTitle = movieObj.get("title").toString()
                                newMessage = movieObj.get("overview").toString()

                                builder = NotificationCompat.Builder(context, "1")
                                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                                        .setContentTitle("New Movie : $newTitle")
                                        .setContentText(newMessage)
                                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                        .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
                                        .setSound(alarmSound)
                                        .setStyle(NotificationCompat.BigTextStyle().bigText(newMessage))

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
                        }

                        override fun onError(anError: ANError?) {

                        }
                    })



        }else{
            builder = NotificationCompat.Builder(context, "1")
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
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


    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(extra_title, "")
        intent.putExtra(extra_message, "")
        intent.putExtra(extra_code, "repeating")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    fun setRepeatingAlarm(context: Context, title: String, message: String){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(extra_title, title)
        intent.putExtra(extra_message, message)
        intent.putExtra(extra_code, "repeating")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Alarm Set Up", Toast.LENGTH_SHORT).show()

    }

    fun cancelMovieAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(extra_title, "")
        intent.putExtra(extra_message, "")
        intent.putExtra(extra_code, "movie")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntentMovie = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntentMovie)

        alarmManager.cancel(pendingIntentMovie)
        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    fun setMovieAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(extra_title, "")
        intent.putExtra(extra_message, "")
        intent.putExtra(extra_code, "movie")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntentMovie = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntentMovie)

        Toast.makeText(context, "Movie Alarm Set Up", Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val extra_title = "EXTRA_TITLE"
        const val extra_message= "EXTRA_MESSAGE"
        const val extra_code = "EXTRA_CODE"
    }

}