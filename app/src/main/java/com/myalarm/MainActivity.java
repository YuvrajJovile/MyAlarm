package com.myalarm;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private TextView mtvDate, mTvTime;
    private ToggleButton mtbAlarmToggle;
    private Toast mToast;

    private String mDate, mTime;
    private Calendar mCalandarAlarm;

    private AlarmManager mAlarmManager;

    private PendingIntent mPendingIntent;

    private static MainActivity mMainActivityInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mtvDate = findViewById(R.id.tv_date);
        mTvTime = findViewById(R.id.tv_time);

        mtbAlarmToggle = findViewById(R.id.tb_alarm);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        mCalandarAlarm = Calendar.getInstance();

        mtbAlarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onToggleClicked();
            }
        });


        mtvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateValue();
            }
        });


        mTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimeValue();
            }
        });
    }

    @Override
    protected void onStart() {
        mMainActivityInstance = this;
        super.onStart();
    }

    private void setTimeValue() {


        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(this + "", "Hour==" + hourOfDay + " min==" + minute);
                mTime = hourOfDay + ":" + minute;
                mCalandarAlarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalandarAlarm.set(Calendar.MINUTE, minute);
                mTvTime.setText("Alarm on Time : " + mTime);
            }
        };

        TimePickerDialog timePicker;
        Calendar calendar = Calendar.getInstance();
        timePicker = new TimePickerDialog(this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePicker.show();


    }

    private void setDateValue() {

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                mCalandarAlarm.set(Calendar.YEAR, year);
                mCalandarAlarm.set(Calendar.MONTH, month);
                mCalandarAlarm.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mtvDate.setText("Alarm on Date : " + mDate);
            }
        };

        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onToggleClicked() {
        if (mtbAlarmToggle.isChecked()) {
            showMessage("Alarm set on " + mDate + " by " + mTime);
            Log.d("MainActivity", "TIme in millisecond==" + mCalandarAlarm.getTimeInMillis());

            Intent intent = new Intent(MainActivity.this, AlarmBroadcast.class);

            mPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            mAlarmManager.set(AlarmManager.RTC, mCalandarAlarm.getTimeInMillis(), mPendingIntent);

        } else {
            mAlarmManager.cancel(mPendingIntent);
            showMessage("Alarm is off");
        }
    }

    private void showMessage(String data) {

        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, data, Toast.LENGTH_SHORT);
        mToast.show();
    }


}
