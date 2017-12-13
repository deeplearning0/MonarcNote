package uyg1.mehmetonar.com.monarchnot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uyg1.mehmetonar.com.monarchnot.fragments.MemoFragment;
import uyg1.mehmetonar.com.monarchnot.fragments.ReminderFragment;
import uyg1.mehmetonar.com.monarchnot.inputtool.InputNote;
import uyg1.mehmetonar.com.monarchnot.inputtool.InputReminders;
import uyg1.mehmetonar.com.monarchnot.signandlogin.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser muser;
    private Animation fabb_open, fab_close, rotate_forward, rotate_backward;
    private FloatingActionButton fab1, fab2, fab;
    private Boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("MyNote");*/

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);


        fabb_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fabb_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        muser = mAuth.getCurrentUser();
        Log.d("current user", "" + muser);
        if (muser != null) {

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.listFragment, new MemoFragment(getApplicationContext())).commit();

        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.memo_navi:
                    ft.replace(R.id.listFragment, new MemoFragment(getApplicationContext())).commit();

                    return true;
                case R.id.reminder_navi:
                    ft.replace(R.id.listFragment, new ReminderFragment(getApplicationContext())).commit();

                    return true;

            }

            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
//reminders
                startActivity(new Intent(getApplicationContext(), InputReminders.class));
                animateFAB();
                break;
            case R.id.fab2:
//note
                startActivity(new Intent(getApplicationContext(), InputNote.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
                animateFAB();
                break;
        }

    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fabb_open);
            fab2.startAnimation(fabb_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }
}
