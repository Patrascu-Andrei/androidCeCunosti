package com.example.android.cecunosti;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cecunosti.data.ConfigData;
import com.example.android.cecunosti.data.JsonProcessor;
import com.example.android.cecunosti.data.LevelData;

import static com.example.android.cecunosti.R.layout.dialog_names;

public class MainActivity extends AppCompatActivity implements AppConstants
{


    private CountDown mCount;
    private Thread mThreadCount;
    private Context mContext;
    private TextView timeInput;
    private TextView cPlayer;
    private Button timerStop;
    private Button timerStop2;

    // pentru intrebare
    private TextView title;
    private TextView question;
    int crtLevel;

    SharedPreferences settings;
    LevelData ld;
    ConfigData config;
    //--------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        final View mView = getLayoutInflater().inflate(dialog_names, null);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        final TextView jucator1 = (TextView) findViewById(R.id.jucator1);
        final TextView jucator2 = (TextView) findViewById(R.id.jucator2);
        //---- Pentru cronometru

        timeInput = (TextView) findViewById(R.id.timer);
        cPlayer = (TextView) findViewById(R.id.crt_player);
        //----- pentru oprirea cronometrului

        timerStop = (Button) findViewById(R.id.letter_conf);
        timerStop2 = (Button) findViewById(R.id.letter_conf2);

        //---------------------
        //---- pentru a lua intrebarea
        config = JsonProcessor.readConfig(mContext, "levels");
        settings = getSharedPreferences(PREFS_NAME, 0);
        crtLevel = getLevel();
        // de cautat unde pui nivelul la care jcuatorii se afla

        ld = config.getLevels().get(crtLevel - 1);
// setam intrebarea
        question = (TextView) findViewById(R.id.level_question);
        question.setText(ld.getQuestion());
        //-----


        Button mButton = (Button) mView.findViewById(R.id.mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nume_jucator = (EditText) mView.findViewById(R.id.nume_jucator1);
                String name = nume_jucator.getText().toString();

                // Aici setam jucatorul curent caruia ii adresam intrebarea
                cPlayer.setText(name);
//----------------------------------

                EditText nume_jucator2 = (EditText) mView.findViewById(R.id.nume_jucator2);
                String name2 = nume_jucator2.getText().toString();

                if (!nume_jucator.getText().toString().isEmpty() && !nume_jucator2.getText().toString().isEmpty()) {
                    jucator1.setText(name);
                    jucator2.setText(name2);

                    CharSequence greeting = "Bine a-ti venit " + name + " " + name2;

                    Toast.makeText(MainActivity.this, greeting, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Introduce-ti numele jucatorilor", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

                /* aici declar pentru countDown
                trebuie sa gasesc alt event
*/

                if (mCount == null) {
                    mCount = new CountDown(mContext);
                    mThreadCount = new Thread(mCount);
                    mThreadCount.start();
                    mCount.start();
                }
            }
        });

        timerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount != null) {
                    mCount.stop();
                    mThreadCount.interrupt();
                    mThreadCount = null;
                    mCount = null;
                }
            }
        });

        timerStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount != null) {
                    mCount.stop();
                    mThreadCount.interrupt();
                    mThreadCount = null;
                    mCount = null;
                }
            }
        });
    }


    public void updateTimerText(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeInput.setText(time);
            }
        });
    }
    public int getLevel(){
        int level = settings.getInt(LEVEL, 1);
        int maxLevel = settings.getInt(NO_LEVELS, -1);
        if(maxLevel < 0) {
            return level;
        }else{
            if(level > maxLevel){
                level = 1;
            }
        }
        return level;
    }
}
