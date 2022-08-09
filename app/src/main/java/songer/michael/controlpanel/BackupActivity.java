package songer.michael.controlpanel;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackupActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private long lastClicked = 0;
    private long longCoolDown = 8000;
    //private long shortCoolDown = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        sharedPreferences = this.getSharedPreferences("pref_default", MODE_PRIVATE);

        run(sharedPreferences.getString(getString(R.string.key_currenttasks_command),getString(R.string.default_value_currenttasks)), null);

        run(sharedPreferences.getString(getString(R.string.key_bootPiDetails_command),getString(R.string.default_value_bootPiDetails)), null);
        run(sharedPreferences.getString(getString(R.string.key_lightPiDetails_command),getString(R.string.default_value_lightPiDetails)), null);
        run(sharedPreferences.getString(getString(R.string.key_memoryStickDetails_command),getString(R.string.default_value_memoryStickDetails)), null);
    }

    public void alert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Password needed");
        builder.setMessage("Unable to backup device without password");
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void backupBootPiButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        EditText editText = findViewById(R.id.backupBootPiPassword);

        if (editText.getText().toString().equals(""))
        {
            alert();
        }
        else
        {
            String Text = editText.getText().toString();
            editText.setText("");
            run(sharedPreferences.getString(getString(R.string.key_backupBootPi_command),getString(R.string.default_value_backupBootPi)), Text);
        }
    }

    public void backupLightPiButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        EditText editText = findViewById(R.id.backupLightPiPassword);
        if (editText.getText().toString().equals(""))
        {
            alert();
        }
        else
        {
            String Text = editText.getText().toString();
            editText.setText("");
            run(sharedPreferences.getString(getString(R.string.key_backupLightPi_command), getString(R.string.default_value_backupLightPi)), Text);
        }
    }

    public void backupMemoryStickButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        EditText editText = findViewById(R.id.backupMemoryStickPassword);
        if (editText.getText().toString().equals(""))
        {
            alert();
        }
        else
        {
            String Text = editText.getText().toString();
            editText.setText("");
            run(sharedPreferences.getString(getString(R.string.key_backupMemoryStick_command), getString(R.string.default_value_backupMemoryStick)), Text);
        }
    }

    public void run(String com, String pass)
    {
        DoTask task = new DoTask();
        task.execute(com, pass);
    }
    
    private class DoTask extends AsyncTask<String, Void, Void> {
        String result;
        String command;

        private String HTTP(String command, String pass)
        {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;

            String stringUrl;
            String result;
            String inputLine;

            stringUrl = sharedPreferences.getString(getString(R.string.key_backupUrl_command),getString(R.string.default_value_backupUrl)) +
                    sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) +
                    command + "&" +
                    sharedPreferences.getString(getString(R.string.key_secondVariableUrl_command),getString(R.string.default_value_secondValueUrl)) + pass;

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
                result = "HTML Error";
                Log.e("HTML Error", String.valueOf(e));
            }

            return result;
        }

        @Override
        protected Void doInBackground(String... params){
            this.command = params[0];
            String pass = params[1];

            this.result = HTTP(this.command, pass);

            if (this.result.toLowerCase().contains("dataplicity") || result.toLowerCase().contains("html"))
            {
                this.result = "Can not connect to PI";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (this.command.equals(sharedPreferences.getString(getString(R.string.key_currenttasks_command), getString(R.string.default_value_currenttasks))))
            {
                Button button;
                if (this.result.toLowerCase().contains("boot"))
                {
                    button = findViewById(R.id.backupBootPiButton);
                    button.setEnabled(false);
                }
                else
                {
                    button = findViewById(R.id.backupBootPiButton);
                    button.setEnabled(true);
                }

                if (this.result.toLowerCase().contains("light"))
                {
                    button = findViewById(R.id.backupLightPiButton);
                    button.setEnabled(false);
                }
                else
                {
                    button = findViewById(R.id.backupLightPiButton);
                    button.setEnabled(true);
                }

                if (this.result.toLowerCase().contains("memory"))
                {
                    button = findViewById(R.id.backupMemoryStickButton);
                    button.setEnabled(false);
                }
                else
                {
                    button = findViewById(R.id.backupMemoryStickButton);
                    button.setEnabled(true);
                }
            }
            else
            {
                this.result = this.result.replace("_", "\n");
                if (this.command.equals(sharedPreferences.getString(getString(R.string.key_bootPiDetails_command), getString(R.string.default_value_bootPiDetails))))
                {
                    TextView textView = findViewById(R.id.backupBootPiText);
                    textView.setText(this.result);
                }
                else if (this.command.equals(sharedPreferences.getString(getString(R.string.key_lightPiDetails_command), getString(R.string.default_value_lightPiDetails))))
                {
                    TextView textView = findViewById(R.id.backupLightPiText);
                    textView.setText(this.result);
                }
                else if (this.command.equals(sharedPreferences.getString(getString(R.string.key_memoryStickDetails_command), getString(R.string.default_value_memoryStickDetails))))
                {
                    TextView textView = findViewById(R.id.backupMemoryStickText);
                    textView.setText(this.result);
                }
            }
        }
    }
}
