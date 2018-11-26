package banana.digital.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.System.currentTimeMillis;

public class Widget extends AppWidgetProvider {


    RemoteViews remoteViews;
    private static final String REFRESH_ACTION = "REFRESH_ACTION";



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i = 0; i < appWidgetIds.length; i++) {
            int currentId = appWidgetIds[i];
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_hor);


            SharedPreferences PatternSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String pattern = PatternSharedPreferences.getString("widget_date_pattern", "HH:mm:ss");
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
            Date date = new Date();


            SharedPreferences ColorSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int color = ColorSharedPreferences.getInt("widget_color", Color.BLACK);
            remoteViews.setInt(R.id.root_container,"setBackgroundColor", color);


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
        if (intent.getAction() != null && intent.getAction().equals(REFRESH_ACTION)) { // если это наш интент
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context); // получаем инстанс AppWidgetManager
            //final int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS); // получаем widget ids
            final ComponentName name = new ComponentName(context, Widget.class); // получаем widget ids
            final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(name);
            onUpdate(context, appWidgetManager, appWidgetIds); // обновляем виджеты
        } else {
            super.onReceive(context, intent);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        //Обновляем виджеты при ресайзе
        final Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        //если высота < 100 - горизонтальный, если высота > 100 - вертикальный
    }




    public void SchenduleUpdates(Context context, int[] appWidgetsIds) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        final Intent intent = new Intent(context, Widget.class);
        intent.setAction(REFRESH_ACTION);
        //intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID,  appWidgetsIds);
        final PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis() + 5 * 1000);
        calendar.set(Calendar.MILLISECOND, 0);
        //calendar.set(Calendar.SECOND, 0);
        final long time = calendar.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC
                , time
                , pendingIntent);
    }
}