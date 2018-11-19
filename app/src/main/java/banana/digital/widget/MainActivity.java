package banana.digital.widget;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//AppWidgetProvider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);
        

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    public void run(){
                        Log.e("a", "Second Thread" + SoundMananger.getInstance());
                    }
                };

                thread.start();
                Log.e("a", "Main Thread" + SoundMananger.getInstance());

            }

        });


    }
}
