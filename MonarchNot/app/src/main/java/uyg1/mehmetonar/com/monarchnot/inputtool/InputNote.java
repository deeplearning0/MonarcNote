package uyg1.mehmetonar.com.monarchnot.inputtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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

public class InputNote extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mTitle,mTextBody;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.input_note);
        mTitle =findViewById(R.id.note_title);
        mTextBody=findViewById(R.id.note_body);





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
        myRef=database.getReference("notes").child(mUser.getUid());
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.done:
                saveNote();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        final String noteTitle=mTitle.getText().toString();
        final String noteBody=mTextBody.getText().toString();
        Date currentTime = Calendar.getInstance().getTime();


        String uid = mUser.getUid();


        if (!TextUtils.isEmpty(noteBody)&& !TextUtils.isEmpty(noteBody)){
            Log.w("saving note",""+noteTitle+  " "+noteBody+" "+currentTime);
           DatabaseReference noteRef= myRef.push();
            Log.e("saving note",""+myRef);
           noteRef.child("title").setValue(noteTitle);
           noteRef.child("body").setValue(noteBody);
           noteRef.child("date").setValue(currentTime.toString());


            new AwesomeSuccessDialog(this)
                    .setTitle(R.string.note)
                    .setMessage(R.string.note_message)
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
                    .setTitle(R.string.note)
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
}
