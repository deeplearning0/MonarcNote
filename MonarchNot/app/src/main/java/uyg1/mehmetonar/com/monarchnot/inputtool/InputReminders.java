package uyg1.mehmetonar.com.monarchnot.inputtool;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import uyg1.mehmetonar.com.monarchnot.signandlogin.LoginActivity;
import uyg1.mehmetonar.com.monarchnot.R;

public class InputReminders extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ImageButton mDatePickerButton;
    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;
    private EditText mTitle, mBody;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindrs);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.input_reminders);

        mBody = findViewById(R.id.reminder_body);
        mTitle = findViewById(R.id.reminder_title);
        spinner = findViewById(R.id.spinner);
        mDatePickerButton = findViewById(R.id.btn_datepicker);
        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
        Log.d("current user", "" + mUser);
        if (mUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("reminders").child(mUser.getUid());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.done1:
                saveReminders();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveReminders() {
        String priority = spinner.getSelectedItem().toString();
        String title = mTitle.getText().toString();
        String body = mBody.getText().toString();
        String date = this.date_time;
        Date currentTime = Calendar.getInstance().getTime();

        Log.w("kaydedilecek veri", "" + title + " " + body + " " + " " + priority + " " + date);


        if (!TextUtils.isEmpty(title)&& !TextUtils.isEmpty(body)&&!TextUtils.isEmpty(date)){
            DatabaseReference reminderRef = myRef.push();
            reminderRef.child("title").setValue(title);
            reminderRef.child("body").setValue(body);
            reminderRef.child("reminingDate").setValue(date);
            reminderRef.child("date").setValue(currentTime.toString());
            reminderRef.child("priority").setValue(priority);
            Log.e("saving note",""+reminderRef);

            new AwesomeSuccessDialog(this)
                    .setTitle(R.string.reminder)
                    .setMessage(R.string.reminder_message)
                    .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                    .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                    .setCancelable(true)
                    .setPositiveButtonText(getString(R.string.dialog_ok_button))
                    .setPositiveButtonbackgroundColor(R.color.colorPrimary)
                    .setPositiveButtonTextColor(R.color.white)
                    .setPositiveButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            finish();
                            overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
                        }
                    }).show();
        }else{
            new AwesomeErrorDialog(this)
                    .setTitle(R.string.reminder)
                    .setMessage(R.string.reminder_error_message)
                    .setColoredCircle(R.color.dialogErrorBackgroundColor)
                    .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                    .setCancelable(true).setButtonText(getString(R.string.dialog_ok_button))
                    .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
                    .setButtonText(getString(R.string.dialog_ok_button))
                    .setErrorButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            // click
                        }
                    })
                    .show();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }


    private void datePicker() {

        // Get Current Date
        Log.d("Get Current Date", "Get Current Date");
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        Log.d("hatırlatıcı zamanı", date_time + " " + hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
