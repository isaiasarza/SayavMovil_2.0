package com.sayav.desarrollo.sayav20;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Naza on 31/10/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "5";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("", "Remote Message " + remoteMessage);

        showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        // Gets the data repository in write mode
        guardarNotificacion(remoteMessage);
    }

    private void guardarNotificacion(RemoteMessage remoteMessage){
        NotificacionDbHelper nDbHelper = new NotificacionDbHelper(this);
        SQLiteDatabase db = nDbHelper.getWritableDatabase();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(NotificacionContract.NotificacionEntry.COLUMN_NAME_DATE, remoteMessage.getSentTime());

        values.put(NotificacionContract.NotificacionEntry.COLUMN_NAME_DESCRIPTION, remoteMessage.getData().get("message"));

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(NotificacionContract.NotificacionEntry.TABLE_NAME, null, values);

        db.close();
    }

   private void showNotification(String message,String title) {
        Intent i = new Intent(this,BandejaEntrada.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Log.i("Mensaje", message);
       NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
               .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
               .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                       R.drawable.common_google_signin_btn_icon_dark))
               .setContentTitle(title)
               .setContentInfo(message)
               .setContentText(message)
               .setSubText("Fecha: " + new SimpleDateFormat("dd/mm/yyyy hh:mm").format(new Date()))
               .setStyle(new NotificationCompat.BigTextStyle()
                       .bigText(message))
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setSound(defaultSoundUri)
               .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }

    private void sendNotification(String message,String title) {
        Intent intent = new Intent(this, BandejaEntrada.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
