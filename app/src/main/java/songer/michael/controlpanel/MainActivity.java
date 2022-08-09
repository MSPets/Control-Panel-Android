package songer.michael.controlpanel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            //return true;
        }
        if (id == R.id.action_resetdata)
        {
            SharedPreferences preferences =getSharedPreferences("pref_default",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }

        return super.onOptionsItemSelected(item);
    }

    public void lightButtonClicked(View view)
    {
        Intent intent = new Intent(this, LightControlActivity.class);
        startActivity(intent);
    }

    public void musicButtonClicked(View view)
    {
        Intent intent = new Intent(this, MusicControlActivity.class);
        startActivity(intent);
    }

    public void alarmButtonClicked(View view)
    {
        Intent intent = new Intent(this, AlarmsActivity.class);
        startActivity(intent);
    }

    public void pcControlButtonClicked(View view)
    {
        Intent intent = new Intent(this, PcControlActivity.class);
        startActivity(intent);
    }

    public void backupButtonClicked(View view)
    {
        Intent intent = new Intent(this, BackupActivity.class);
        startActivity(intent);
    }
}
