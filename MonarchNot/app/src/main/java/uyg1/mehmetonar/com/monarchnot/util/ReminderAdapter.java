package uyg1.mehmetonar.com.monarchnot.util;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uyg1.mehmetonar.com.monarchnot.Entities.Reminder;
import uyg1.mehmetonar.com.monarchnot.R;

/**
 * Created by mehme on 13.12.2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {

    List<Reminder> reminderList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rowTitle;
        public TextView rowDesc;
        public TextView rowPubDate;
        public TextView rowPriority;
        public TextView rowRemindDate;

        public MyViewHolder(View view) {
            super(view);
            rowTitle = (TextView) view.findViewById(R.id.row_title);
            rowDesc = (TextView) view.findViewById(R.id.row_desc);
            rowPubDate = (TextView) view.findViewById(R.id.row_pubdate);
            rowPriority=view.findViewById(R.id.row_priority);
            rowRemindDate=view.findViewById(R.id.row_rdate);
        }
    }

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reminder r = reminderList.get(position);
        holder.rowTitle.setText(r.getTitle());
        holder.rowDesc.setText(r.getBody());
        holder.rowPubDate.setText(r.getDate());
        holder.rowPriority.setText(r.getpriority());
        if (r.getpriority().equalsIgnoreCase("yüksek")){
            holder.rowPriority.setBackgroundColor(Color.parseColor("#ff0000"));
//            holder.rowPriority.setTextColor(Color.parseColor("fff"));
        }else if(r.getpriority().equalsIgnoreCase("orta")){
            holder.rowPriority.setBackgroundColor(Color.BLUE);
         //   holder.rowPriority.setTextColor(Color.parseColor("fff"));
        }else if(r.getpriority().equalsIgnoreCase("düşük")){
            holder.rowPriority.setBackgroundColor(Color.GREEN);
       //     holder.rowPriority.setTextColor(Color.parseColor("fff"));
        }
        holder.rowRemindDate.setText(r.getreminingDate());

    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowr,parent, false);
        return new MyViewHolder(v);
    }
}

