package songer.michael.controlpanel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PcControlActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private long lastClicked = 0;
    private long longCoolDown = 8000;
    private long shortCoolDown = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_control);

        sharedPreferences = this.getSharedPreferences("pref_default", MODE_PRIVATE);

        run(sharedPreferences.getString(getString(R.string.key_pcStatus_command),getString(R.string.default_value_pcStatus)));
    }

    public void pcOnButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_pcOn_command),getString(R.string.default_value_pcOn)));
    }

    public void pcOffButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_pcOff_command),getString(R.string.default_value_pcOff)));
    }

    public void pcStatusButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_pcStatus_command),getString(R.string.default_value_pcStatus)));
    }

    public void run(String com)
    {
        DoTask task = new DoTask();
        task.execute(com);
    }

    private class DoTask extends AsyncTask<String, Void, Void> {
        String result;

        private String HTTP(String command)
        {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;

            String stringUrl;
            String result;
            String inputLine;

            stringUrl = sharedPreferences.getString(getString(R.string.key_pcControlUrl_command),getString(R.string.default_value_pcControlUrl)) +
                    sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) +
                    command;

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

            String result = "";
            result = HTTP(command);

            this.result = "Result: " + result;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            TextView textView = findViewById(R.id.pcResult);
            textView.setText(this.result);
        }
    }
}
