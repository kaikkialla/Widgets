package banana.digital.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//AppWidgetProvider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);
        
        //Получаем id виджета
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        //Получаем
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            appWidgetManager.updateAppWidget(id, new RemoteViews(this.getPackageName(), R.layout.widget_layout));

        }
    }
}
