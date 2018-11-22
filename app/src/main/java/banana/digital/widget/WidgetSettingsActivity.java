package banana.digital.widget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class WidgetSettingsActivity extends PreferenceActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.widget_settings_preference_activity);
        setContentView(R.layout.activity_widget_settings);
        textView = findViewById(R.id.textView);


     /*Не работает, крашится
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference preference = findPreference("widget_color");
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(WidgetSettingsActivity.this);
                String theme = pref.getString("widget_color", "#aaa");
                preference.setSummary(theme);
            }
        });
        */
    }
}
