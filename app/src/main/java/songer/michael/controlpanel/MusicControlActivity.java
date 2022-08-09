package songer.michael.controlpanel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MusicControlActivity extends AppCompatActivity {

    private long lastClicked = 0;
//    private long longCoolDown = 8000;
    private long shortCoolDown = 2000;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_control);

        sharedPreferences = this.getSharedPreferences("pref_default", MODE_PRIVATE);

        NumberPicker np = findViewById(R.id.volume);
        np.setMinValue(0);
        np.setMaxValue(100);

        TextView textView = findViewById(R.id.musicResult);
        textView.setText("Loading");

        textView = findViewById(R.id.currentPlaylist);
        textView.setText("Checking if music is playing");

        run(sharedPreferences.getString(getString(R.string.key_currentvolume_command), getString(R.string.default_value_currentvolume)),"");
        run(sharedPreferences.getString(getString(R.string.key_currentsong_command), getString(R.string.default_value_currentsong)),"");
        run(sharedPreferences.getString(getString(R.string.key_getSleepTimer_command),getString(R.string.default_value_getSleepTimer)), "");
    }

    public void playButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_playmusic_command),getString(R.string.default_value_playmusic)), "");
    }

    public void stopButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_stopmusic_command),getString(R.string.default_value_stopmusic)),"");
    }

    public void setVolumeButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        NumberPicker np = findViewById(R.id.volume);

        run(sharedPreferences.getString(getString(R.string.key_setvolume_command),getString(R.string.default_value_setvolume)), String.valueOf(np.getValue()));
    }

    public void setSleepTimerButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        EditText editText = findViewById(R.id.SleepTimerText);

        String sleepTimerStr = editText.getText().toString();

        editText.setText("");

        run(sharedPreferences.getString(getString(R.string.key_setSleepTimer_command),getString(R.string.default_value_setSleepTimer)), sleepTimerStr);
    }

    public void removeSleepTimerButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeSleepTimer_command),getString(R.string.default_value_removeSleepTimer)), "");
    }



    public void run(String command, String command2)
    {
        DoTask task = new DoTask();
        task.execute(command, command2);
    }

    private class DoTask extends AsyncTask<String, Void, Void> {
        String result;

        String currentSong = "";
        String currentVolume = "";
        String currentSleepTimer = "";

        private String HTTP(String command, String command2)
        {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;

            String stringUrl;
            String result;
            String inputLine;

            if (!command2.equals(""))
            {
                stringUrl = sharedPreferences.getString(getString(R.string.key_musicControlUrl_command),getString(R.string.default_value_musicControlUrl)) +
                        sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) +
                        command +
                        "&" +
                        sharedPreferences.getString(getString(R.string.key_secondVariableUrl_command),getString(R.string.default_value_secondValueUrl))+
                        command2;
            }
            else
            {
                stringUrl = sharedPreferences.getString(getString(R.string.key_musicControlUrl_command),getString(R.string.default_value_musicControlUrl)) +
                        sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) +
                        command;
            }

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our this.result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = "Error";
                Log.e("HTML Error", String.valueOf(e));
            }

            return result;
        }

        @Override
        protected Void doInBackground(String... params){
            String command = params[0];
            String command2 = params[1];

            String result = "";

            if(!command.equals(sharedPreferences.getString(getString(R.string.key_currentsong_command),getString(R.string.default_value_currentsong)))
                    && !command.equals(sharedPreferences.getString(getString(R.string.key_currentvolume_command),getString(R.string.default_value_currentvolume)))
                    && !command.equals(sharedPreferences.getString(getString(R.string.key_getSleepTimer_command),getString(R.string.default_value_getSleepTimer))))
            {
                result = HTTP(command, command2);
            }

            // work out result
            if (result.toLowerCase().contains("dataplicity") || result.toLowerCase().contains("error")) {
                result = "Can not connect to PI";
            }

            this.currentVolume = HTTP(sharedPreferences.getString(getString(R.string.key_currentvolume_command),getString(R.string.default_value_currentvolume)),"");
            if (this.currentVolume.toLowerCase().contains("dataplicity") || this.currentVolume.toLowerCase().contains("error")) {
                this.currentVolume = "";
            }

            this.currentSong = HTTP(sharedPreferences.getString(getString(R.string.key_currentsong_command),getString(R.string.default_value_currentsong)),"");
            if (currentSong.toLowerCase().contains("dataplicity") || currentSong.toLowerCase().contains("error")) {
                this.currentSong = "Error getting current song";
            }

            this.currentSleepTimer = HTTP(sharedPreferences.getString(getString(R.string.key_getSleepTimer_command),getString(R.string.default_value_getSleepTimer)),"");
            if (this.currentSleepTimer.toLowerCase().contains("dataplicity") || this.currentSleepTimer.toLowerCase().contains("error")) {
                this.currentSleepTimer = "Error getting sleep timer";
            }

            this.result = "Result: " + result;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            TextView textView = findViewById(R.id.musicResult);
            textView.setText(this.result);

            textView = findViewById(R.id.currentPlaylist);
            if (!this.currentSong.contains("volume"))
            {
                textView.setText(this.currentSong);
            }
            else
            {
                textView.setText("No music is playing");
            }


            EditText editText = findViewById(R.id.SleepTimerText);
            if (this.currentSleepTimer.contains(":"))
            {
                editText.setHint("Sleep timer set for " + this.currentSleepTimer);
            }
            else if (this.currentSleepTimer.toLowerCase().contains("error"))
            {
                editText.setHint(this.currentSleepTimer);
            }
            else
            {
                editText.setHint("No current sleep Timer");
            }

            NumberPicker np = findViewById(R.id.volume);

            String[] volumeArray = this.currentVolume.split(" ");
            for (String i : volumeArray)
            {
                try { np.setValue(Integer.parseInt(i)); } catch (Exception e) { }
            }
        }
    }
}
