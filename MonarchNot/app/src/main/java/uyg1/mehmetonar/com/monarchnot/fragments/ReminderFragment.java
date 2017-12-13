package uyg1.mehmetonar.com.monarchnot.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uyg1.mehmetonar.com.monarchnot.Entities.Reminder;
import uyg1.mehmetonar.com.monarchnot.R;
import uyg1.mehmetonar.com.monarchnot.util.ReminderAdapter;


@SuppressLint("ValidFragment")
public class ReminderFragment extends Fragment {

    ArrayList<Reminder> newsModelList;
    private Context mContext;
    private RecyclerView mRecyclerView;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    ReminderAdapter adapter;
    List<Reminder> rList;


    public ReminderFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reminder, container, false);
        mRecyclerView = view.findViewById(R.id.reminder_list);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        rList=new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("reminders").child(mUser.getUid());
        Log.d("databse url",""+mDatabase);
        Log.d("user url",""+mUser);
        adapter=new ReminderAdapter(rList);

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Reminder note = noteDataSnapshot.getValue(Reminder.class);
                    Log.d("databse url",""+mDatabase);
                    System.out.print(note);
                    rList.add(note);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error url",""+mDatabase);
            }
        });




        mRecyclerView.setAdapter(adapter);
        return view;
    }


}
