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
import android.preference.Preference;
import android.support.annotation.RequiresApi;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.System.currentTimeMillis;

public class Widget extends AppWidgetProvider {


    RemoteViews remoteViews;
    private static final String REFRESH_ACTION = "REFRESH_ACTION";





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0; i< appWidgetIds.length; i++) {
            final int widgetId = appWidgetIds[i]; // получаем id очередного виджета
            final Bundle widgetOptions = appWidgetManager.getAppWidgetOptions(widgetId);
            final int minHeight = widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
            final RemoteViews views;
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_hor);



            final SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
            final String pattern = sharedPreferences.getString("widget_date_pattern_" + widgetId, "HH:mm");
            final int color = sharedPreferences.getInt("widget_color_" + widgetId, Color.BLACK);


            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
            Date date = new Date();


            //final String displayedTime = pattern.format(String.valueOf(new Date(currentTimeMillis()))); // форматируем текущее время
            views.setTextViewText(R.id.textView,formatter.format(date)); // меняем в виджете
            views.setInt(R.id.root_container, "setBackgroundColor", color);

            appWidgetManager.updateAppWidget(widgetId, views); // обновляем виджет
        }
        ScheduleUpdates(context); // подписываем виджеты на обновление через 1 минуту
    }






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(REFRESH_ACTION)) { // если это наш интент
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context); // получаем инстанс AppWidgetManager
            final ComponentName name = new ComponentName(context,  Widget.class);
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




    public void ScheduleUpdates(Context context) {
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