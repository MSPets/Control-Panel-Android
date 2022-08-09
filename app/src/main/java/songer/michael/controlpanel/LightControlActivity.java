package songer.michael.controlpanel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LightControlActivity extends AppCompatActivity {

    private long lastClicked = 0;
    private long longCoolDown = 8000;
    private long shortCoolDown = 2000;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);

        sharedPreferences = this.getSharedPreferences("pref_default", MODE_PRIVATE);

        final TextView textView = findViewById(R.id.lightResult);
        textView.setText("Checking lights status");

        run(sharedPreferences.getString(getString(R.string.key_lightsstatus_command),getString(R.string.default_value_lightsstatus)));
}

    public void run (String command)
    {
        DoTask task = new DoTask();

        task.execute(command);
    }

    //ImageButton simpleImageButton = findViewById(R.id.lightPowerButton);
    //simpleImageButton.setBackground(getResources().getDrawable(R.drawable.powerBetween));

    public void lightPowerButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run("power");
    }

    public void brightnessUpButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_brightnessup_command),getString(R.string.default_value_brightnessup)));
    }
    public void brightnessDownButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_brightnessdown_command),getString(R.string.default_value_brightnessdown)));
    }
    public void redButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_red_command),getString(R.string.default_value_red)));
    }
    public void greenButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_green_command),getString(R.string.default_value_green)));
    }
    public void blueButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_blue_command),getString(R.string.default_value_blue)));
    }
    public void whiteButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_white_command),getString(R.string.default_value_white)));
    }
    public void redUpButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_redup_command),getString(R.string.default_value_redup)));
    }
    public void greenUpButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_greenup_command),getString(R.string.default_value_greenup)));
    }
    public void blueUpButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_blueup_command),getString(R.string.default_value_blueup)));
    }
    public void redDownButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_reddown_command),getString(R.string.default_value_reddown)));
    }
    public void greenDownButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_greendown_command),getString(R.string.default_value_greendown)));
    }
    public void blueDownButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.shortCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_bluedown_command),getString(R.string.default_value_bluedown)));
    }
    public void slowButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_slow_command),getString(R.string.default_value_slow)));
    }
    public void diy1ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy1_command),getString(R.string.default_value_diy1)));
    }
    public void diy2ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy2_command),getString(R.string.default_value_diy2)));
    }
    public void diy3ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy3_command),getString(R.string.default_value_diy3)));
    }
    public void autoButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_auto_command),getString(R.string.default_value_auto)));
    }
    public void diy4ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy4_command),getString(R.string.default_value_diy4)));
    }
    public void diy5ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy5_command),getString(R.string.default_value_diy5)));
    }
    public void diy6ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_diy6_command),getString(R.string.default_value_diy6)));
    }
    public void flashButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_flash_command),getString(R.string.default_value_flash)));
    }
    public void jump3ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_jump3_command),getString(R.string.default_value_jump3)));
    }
    public void jump7ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_jump7_command),getString(R.string.default_value_jump7)));
    }
    public void fade3ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_fade3_command),getString(R.string.default_value_fade3)));
    }
    public void fade7ButtonClicked(View view)
    {
        if (SystemClock.elapsedRealtime() - this.lastClicked < this.longCoolDown){
            return;
        }
        this.lastClicked = SystemClock.elapsedRealtime();

        run(sharedPreferences.getString(getString(R.string.key_fade7_command),getString(R.string.default_value_fade7)));
    }



    private class DoTask extends AsyncTask<String, Void, Void> {
        String result;

        private int getLightsStatus()
        {
            String status = sharedPreferences.getString(getString(R.string.key_lightsstatus_command),getString(R.string.default_value_lightsstatus));
            ImageButton imageButton = findViewById(R.id.lightPowerButton);

            status = HTTP(status);
            int result;

            if (status.contains("on"))
            {
                imageButton.setImageResource(R.drawable.power_on);
                result = 0;
            }
            else if (status.contains("off"))
            {
                imageButton.setImageResource(R.drawable.power_off);
                result = 1;
            }
            else {
                imageButton.setImageResource(R.drawable.power_between);
                result = 1;
            }
            imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

            return result;
        }

        private String HTTP(String com)
        {
            final String REQUEST_METHOD = "GET";
            final int READ_TIMEOUT = 15000;
            final int CONNECTION_TIMEOUT = 15000;


            String stringUrl = sharedPreferences.getString(getString(R.string.key_lightControlUrl_command),getString(R.string.default_value_lightControlUrl)) +
                    sharedPreferences.getString(getString(R.string.key_firstVariableUrl_command),getString(R.string.default_value_firstVariableUrl)) + com;

            String result;
            String inputLine;

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
            String result = "";
            int numresult;

            if (command.equals(sharedPreferences.getString(getString(R.string.key_lightsstatus_command),getString(R.string.default_value_lightsstatus))))
            {
                numresult = getLightsStatus();
                if (numresult ==0)
                {
                    result = "on";
                }
                else
                {
                    result = "off";
                }
            }
            else if (command.equals("power"))
            {
                ImageButton imageButton = findViewById(R.id.lightPowerButton);

                numresult = getLightsStatus();

                if (numresult == 0)
                {
                    result = HTTP(sharedPreferences.getString(getString(R.string.key_lightsoff_command),getString(R.string.default_value_lightsoff)));
                }
                else if (numresult == 1)
                {
                    result = HTTP(sharedPreferences.getString(getString(R.string.key_lightson_command),getString(R.string.default_value_lightson)));
                }

                if (result.contains("on"))
                {
                    imageButton.setImageResource(R.drawable.power_on);
                }
                else if (result.contains("off"))
                {
                    imageButton.setImageResource(R.drawable.power_off);
                }
                else
                {
                    imageButton.setImageResource(R.drawable.power_between);
                }

                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (getLightsStatus() == 0)
            {
                result = HTTP(command);
            }

            else
            {
                result = "Cannot control lights while they are off";
            }


            // work out result
            if (result.toLowerCase().contains("dataplicity") || result.toLowerCase().contains("error"))
            {
                result = "Can not connect to PI";
            }
            else if (result.equals("on"))
            {
                result = "Lights are on";
            }
            else if (result.equals("off"))
            {
                result = "Lights are off";
            }


            this.result = "Result: " + result;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView newText = findViewById(R.id.lightResult);
            newText.setText(this.result);
        }
    }
}
