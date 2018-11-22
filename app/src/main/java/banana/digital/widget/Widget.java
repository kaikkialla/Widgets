package banana.digital.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

public class Widget extends AppWidgetProvider {


    RemoteViews remoteViews;
    String REQUEST_ACTION = "REFRESH_ACTION";



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i = 0; i < appWidgetIds.length; i++) {
            int currentId = appWidgetIds[i];
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
            Date date = new Date();









            remoteViews.setTextViewText(R.id.textView, formatter.format(date));

            appWidgetManager.updateAppWidget(currentId, remoteViews);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            remoteViews.setOnClickPendingIntent(R.id.root_container, pendingIntent);


        }
        SchenduleUpdates(context, appWidgetIds);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction() != null && intent.getAction().equals(REQUEST_ACTION)) {
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            int[] appWidgetIds = intent.getExtras().getIntArray(appWidgetManager.EXTRA_APPWIDGET_ID);//Получаем id всех виджетов
            onUpdate(context, appWidgetManager, appWidgetIds);

        }
    }

    public void SchenduleUpdates(Context context, int[] appWidgetsIds) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        final Intent intent = new Intent(context, Widget.class);
        intent.setAction(REQUEST_ACTION);
        intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID,  appWidgetsIds);
        final PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis() + 60 * 1000);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        final long time = calendar.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC
                , time
                , pendingIntent);
    }
}



//now - ms - s*1000