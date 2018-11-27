package com.example.orizilka.ex_03_notifications;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class QuoteService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_ONE = "com.example.orizilka.ex_03_notifications.action.ONE";
    public static final String[] Array = {"sentence one", "sentence two", "sentence three"};

    public static AlarmManager myAlarmer;
    public static NotificationManagerCompat notificationManager;
    public static int intentNumber = 1;
    public static final long SEC_BETWEEN_NOTIFICATIONS = 30;
    public static int placeInArray = 0;




    public QuoteService() {
        super("QuoteService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionOne(Context context) {
        Intent intent = new Intent(context, QuoteService.class);
        intent.setAction(ACTION_ONE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ONE.equals(action)) {
                handleActionOne();
            } else {
                throw new RuntimeException("Unknown action was given");
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionOne() {
        // TODO: Handle action one
        Intent myIntent = new Intent(this, NotificationReceiver.class);
        myAlarmer = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        myAlarmer.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), SEC_BETWEEN_NOTIFICATIONS,
                PendingIntent.getBroadcast(this, 0, myIntent, 0));

        String currentQuote = Array[placeInArray];
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(intentNumber + ": " + currentQuote)
                // .setContentText(id+ ": " + currentQuote)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                ;
        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(intentNumber ++, builder.build());

        placeInArray ++ ;
        placeInArray = (placeInArray % Array.length);
    }


}
