package songer.michael.controlpanel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class AlarmsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private long lastClicked = 0;
    private long longCoolDown = 8000;
    private long shortCoolDown = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        sharedPreferences = this.getSharedPreferences("pref_default", MODE_PRIVATE);

        run(sharedPreferences.getString(getString(R.string.key_currentAlarms_command),getString(R.string.default_value_currentAlarms)), "");
    }

    public void updateButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        String newAlarms = "";
        EditText editText;
        Spinner spinner;

        editText = findViewById(R.id.mondayAlarm);
        spinner = findViewById(R.id.mondayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Monday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.tuesdayAlarm);
        spinner = findViewById(R.id.tuesdayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Tuesday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.wednesdayAlarm);
        spinner = findViewById(R.id.wednesdayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Wednesday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.thursdayAlarm);
        spinner = findViewById(R.id.thursdayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Thursday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.fridayAlarm);
        spinner = findViewById(R.id.fridayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Friday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.saturdayAlarm);
        spinner = findViewById(R.id.saturdayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Saturday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        editText = findViewById(R.id.sundayAlarm);
        spinner = findViewById(R.id.sundayEarlyLate);
        if (editText.getText().toString().contains(":"))
        {
            newAlarms += "_Sunday-" + editText.getText().toString() + "-" + spinner.getSelectedItem().toString();
            editText.setText("");
        }

        Log.d("newAlarms",newAlarms);
        run(sharedPreferences.getString(getString(R.string.key_updateAlarms_command),getString(R.string.default_value_updateAlarms)), newAlarms);
    }

    public void mondayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "monday");
    }

    public void tuesdayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "tuesday");
    }

    public void wednesdayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "wednesday");
    }

    public void thursdayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "thursday");
    }

    public void fridayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "friday");
    }

    public void saturdayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "saturday");
    }

    public void sundayRemoveButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown)
        {
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_removeAlarm_command),getString(R.string.default_value_removeAlarm)), "sunday");
    }

    public void run(String command, String newAlarms)
    {
        DoTask task = new DoTask();

        task.execute(command, newAlarms);
    }


    private class DoTask extends AsyncTask<String, Void, Void> {
        String result = "";

        private void UpdateAlarms(String day, String time, String earlyLate)
        {
            EditText editText;
            Spinner spinner;

            if (day.equals("monday"))
            {
                editText = findViewById(R.id.mondayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.mondayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("tuesday"))
            {
                editText = findViewById(R.id.tuesdayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.tuesdayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("wednesday"))
            {
                editText = findViewById(R.id.wednesdayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.wednesdayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("thursday"))
            {
                editText = findViewById(R.id.thursdayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.thursdayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("friday"))
            {
                editText = findViewById(R.id.fridayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.fridayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("saturday"))
            {
                editText = findViewById(R.id.saturdayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.saturdayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
            else if (day.equals("sunday"))
            {
                editText = findViewById(R.id.sundayAlarm);
                editText.setHint(time);

                spinner = findViewById(R.id.sundayEarlyLate);
                if (earlyLate.toLowerCase().contains("late"))
                {
                    spinner.setSelection(1);
                }
                else
                {
                    spinner.setSelection(0);
                }
            }
        }

        private String HTTP(String com, String com2)
        {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;

            String stringUrl = sharedPreferences.getString(getString(R.string.key_alarmControlUrl_command),getString(R.string.default_value_alarmControlUrl)) +
                    sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) + com +
                    "&" +
                    sharedPreferences.getString(getString(R.string.key_secondVariableUrl_command),getString(R.string.default_value_secondValueUrl)) + com2;

            String result;
            String inputLine;

            Log.d("command", stringUrl);
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
                result = "Error " + e;
            }

            return result;
        }

        @Override
        protected Void doInBackground(String... params){
            String command = params[0];
            String newAlarms = params[1];
            String result = "";

            if (command.equals(sharedPreferences.getString(getString(R.string.key_updateAlarms_command), getString(R.string.default_value_updateAlarms))))
            {
                HTTP(sharedPreferences.getString(getString(R.string.key_updateAlarms_command), getString(R.string.default_value_updateAlarms)), newAlarms);
                try
                {
                    Thread.sleep(2000);
                }
                catch (Exception e){ }
            }

            else if(command.equals(sharedPreferences.getString(getString(R.string.key_removeAlarm_command), getString(R.string.default_value_removeAlarm))))
            {
                HTTP(sharedPreferences.getString(getString(R.string.key_removeAlarm_command), getString(R.string.default_value_removeAlarm)), newAlarms);
                try
                {
                    Thread.sleep(2000);
                }
                catch (Exception e){ }
                this.result = "removed " + newAlarms;
                return null;
            }

            this.result = HTTP(sharedPreferences.getString(getString(R.string.key_currentAlarms_command), getString(R.string.default_value_currentAlarms)), "");
            if (this.result.toLowerCase().contains("dataplicity") || result.toLowerCase().contains("error"))
            {
                this.result = "Can not connect to PI";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (this.result.toLowerCase().contains("can not"))
            {
                UpdateAlarms("monday", this.result, "");
                UpdateAlarms("tuesday", this.result, "");
                UpdateAlarms("wednesday", this.result, "");
                UpdateAlarms("thursday", this.result, "");
                UpdateAlarms("friday", this.result, "");
                UpdateAlarms("saturday", this. result, "");
                UpdateAlarms("sunday", this.result, "");
            }
            else if (this.result.contains("removed"))
            {
                EditText editText;
                if (this.result.toLowerCase().contains("monday"))
                {
                    editText = findViewById(R.id.mondayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("tuesday"))
                {
                    editText = findViewById(R.id.tuesdayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("wednesday"))
                {
                    editText = findViewById(R.id.wednesdayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("thursday"))
                {
                    editText = findViewById(R.id.thursdayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("friday"))
                {
                    editText = findViewById(R.id.fridayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("saturday"))
                {
                    editText = findViewById(R.id.saturdayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
                else if (this.result.toLowerCase().contains("sunday"))
                {
                    editText = findViewById(R.id.sundayAlarm);
                    editText.setHint(R.string.noAlarmSet);
                }
            }
            else
            {
                String[] resultArray = this.result.split("_");
                String[] tempArray;

                for (String i : resultArray)
                {
                    if (i.contains("monday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("monday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("tuesday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("tuesday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("wednesday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("wednesday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("thursday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("thursday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("friday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("friday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("saturday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("saturday", tempArray[1], tempArray[2]);
                    }
                    else if (i.contains("sunday"))
                    {
                        tempArray = i.split("-");
                        UpdateAlarms("sunday", tempArray[1], tempArray[2]);
                    }
                }
            }
        }
    }
}
