package com.example.notificationtracker;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class CustomNotificationListener extends NotificationListenerService {
    private Ringtone ringtone;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
            if(checkAppPackageName(sbn.getPackageName()) && ringtone == null){
                playRingtone();
            }
    }

//    @Override
//    public void onInterruptionFilterChanged(int interruptionFilter) {
//        super.onInterruptionFilterChanged(interruptionFilter);
//    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if(checkAppPackageName(sbn.getPackageName())) {
            stopRingtone();
        }
    }

    private Boolean checkAppPackageName(String packageName){
        return packageName.equals("com.Slack");
    }

    private void playRingtone(){
        try{

            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), notificationUri);
            ringtone.play();

            android.os.Handler handler = new Handler();


            handler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            stopRingtone();
                        }
                    },
                    10000
            );


        }catch (Exception e){

            Log.e("SlackNotification", "Failed to play ringtone", e);

        }
    }

    private void stopRingtone(){
        if(ringtone == null) return;
        ringtone.stop();
        ringtone = null;
    }
}
