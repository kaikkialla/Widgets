package banana.digital.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class WidgetSettingsActivity extends PreferenceActivity {

    TextView textView;
    private static final String REFRESH_ACTION = "REFRESH_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.widget_settings_preference_activity);
        setContentView(R.layout.activity_widget_settings);
        textView = findViewById(R.id.textView);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference preference = findPreference("widget_color");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
                int theme = pref.getInt("widget_color", Color.BLACK);
                preference.setSummary(String.valueOf(theme));

                createWidget();
                finish();
            }
        });

    }

    private int getWidgetId() {
        final Bundle extras = getIntent().getExtras();
        return extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);//Первое значение - id виджета, второе - нет id
    }


    private void createWidget() {
        // получаем идентификатор виджета, который сейчас добавляем
        if (getWidgetId() != AppWidgetManager.INVALID_APPWIDGET_ID) { // если действительно получили
            final AppWidgetManager manager = AppWidgetManager.getInstance(this);
            final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout_hor);
            // TODO обновляем виджет
            // TODO меняем цвет на тот, что в настройках

            findPreference("widget_color").setKey("widget_color_" + getWidgetId());

            manager.updateAppWidget(getWidgetId(), remoteViews); // вызываем апдейт созданного виджета сразу

            // записываем в результат активности RESULT_OK (мол, всё хорошо)
            final Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, getWidgetId());
            setResult(RESULT_OK, resultIntent);


            //Вызываем onUpdate в Widget
            Intent intent = new Intent(this, Widget.class);
            intent.setAction(REFRESH_ACTION);
            sendBroadcast(intent);
        }
    }
}


//widget_color
