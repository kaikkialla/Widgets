package banana.digital.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//AppWidgetProvider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView textView = findViewById(R.id.text);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.icon2);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), largeIcon);
        dr.setCornerRadius(64);
        textView.setImageDrawable(dr);


        /*
        //Получаем id виджета
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        //Получаем
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            appWidgetManager.updateAppWidget(id, new RemoteViews(this.getPackageName(), R.layout.widget_layout));


        }
        */
    }
}


