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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import uyg1.mehmetonar.com.monarchnot.Entities.Note;
import uyg1.mehmetonar.com.monarchnot.R;


@SuppressLint("ValidFragment")
public class MemoFragment extends Fragment {

    ArrayList<Note> newsModelList;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    public DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    View view;

    @SuppressLint("ValidFragment")
    public MemoFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("notes").child(mUser.getUid());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_memo, container, false);

        mRecyclerView=  view.findViewById(R.id.memo_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mDatabase==null){
            Toast.makeText(mContext, "veri tabanı hatası", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseRecyclerAdapter<Note, MemoHolder> adapter = new FirebaseRecyclerAdapter<Note, MemoHolder>(
                    Note.class,
                    R.layout.row,
                    MemoHolder.class,
                    mDatabase
            ) {
                @Override
                protected void populateViewHolder(MemoHolder viewHolder, Note model, int position) {

                    viewHolder.setBaslik(model.getTitle());
                    viewHolder.setAciklama(model.getBody());
                    viewHolder.setDate(model.getDate());


                }


            };

            mRecyclerView.setAdapter(adapter);

        }


    }

    public static class MemoHolder extends RecyclerView.ViewHolder {
        View mView;

        public MemoHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setBaslik(String baslik) {
            TextView tBaslik = (TextView) mView.findViewById(R.id.row_title);
            tBaslik.setText(baslik);
        }

        public void setAciklama(String aciklama) {
            TextView tAciklama = (TextView) mView.findViewById(R.id.row_desc);
            tAciklama.setText(aciklama);
        }

        public void setDate(String kullanici) {
            TextView tKullanici = (TextView) mView.findViewById(R.id.row_pubdate);
            tKullanici.setText(kullanici);
        }


    }

}


