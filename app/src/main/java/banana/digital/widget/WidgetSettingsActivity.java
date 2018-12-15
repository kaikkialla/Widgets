package banana.digital.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class WidgetSettingsActivity extends PreferenceActivity {

    TextView textView;
    private static final String REFRESH_ACTION = "REFRESH_ACTION";
    SharedPreferences.OnSharedPreferenceChangeListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.widget_preference);
        setContentView(R.layout.widget_preference_activity);
        textView = findViewById(R.id.textView);



        if (widgetId() != AppWidgetManager.INVALID_APPWIDGET_ID) { // если действительно получили
            final AppWidgetManager manager = AppWidgetManager.getInstance(this);
            final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout_hor);
            // TODO обновляем виджет
            // TODO меняем цвет на тот, что в настройках

            findPreference("widget_color").setKey("widget_color_" + widgetId());
            findPreference("widget_date_pattern").setKey("widget_date_pattern_" + widgetId());

            manager.updateAppWidget(widgetId(), remoteViews); // вызываем апдейт созданного виджета сразу

            // записываем в результат активности RESULT_OK (мол, всё хорошо)
            final Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId());
            setResult(RESULT_OK, resultIntent);


            //Вызываем onUpdate в Widget
            Intent intent = new Intent(this, Widget.class);
            intent.setAction(REFRESH_ACTION);
            sendBroadcast(intent);
        }






        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference colorPreference = findPreference("widget_color_" + widgetId());
                SharedPreferences colorPref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
                int color = colorPref.getInt("widget_color_" + widgetId(), Color.BLACK);
                colorPreference.setSummary(String.valueOf(color));

                Preference patternPreference = findPreference("widget_date_pattern");
                SharedPreferences patternPref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
                String pattern = patternPref.getString("widget_date_pattern", "Выберите паттерн");
                colorPreference.setSummary(String.valueOf(pattern));


                createWidget();
                finish();
            }
        });

    }



    private void createWidget() {
        // записываем в результат активности RESULT_OK (мол, всё хорошо)
        final Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId());
        setResult(RESULT_OK, resultIntent);

        // просим DigitalClockWidgetProvider обновить все виджеты
        // (и только что созданный в том числе)
        final Intent intent = new Intent(this, Widget.class);
        intent.setAction(REFRESH_ACTION);
        sendBroadcast(intent);
    }



    private int widgetId() {
        final Bundle extras = getIntent().getExtras();
        int id = Integer.parseInt(String.valueOf(extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)).replace(" ", ""));
        return id;//Первое значение - id виджета, второе - нет id
    }



    @Override
    protected void onResume() {
        super.onResume();


        updateDateSummary();
        updateColorSummary();





        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("widget_color")) {
                    updateColorSummary();
                } else if(key.equals("widget_date_pattern")) {
                    updateDateSummary();
                }
            }
        };

        updateColorSummary();
        updateDateSummary();
        pref.registerOnSharedPreferenceChangeListener(listener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }




    private void updateDateSummary(){
        Preference patternPreference = findPreference("widget_date_pattern_" + widgetId());
        SharedPreferences patternPref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
        String pattern = patternPref.getString("widget_date_pattern_" + widgetId(), "HH:mm");
        patternPreference.setSummary(pattern);
    }

    private void updateColorSummary() {
        Preference colorPreference = findPreference("widget_color_" + widgetId());
        SharedPreferences colorPref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
        int color = colorPref.getInt("widget_color_" + widgetId(), Color.BLACK);
        colorPreference.setSummary(String.valueOf(color));
    }
}


//widget_color
