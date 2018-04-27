package com.me.travelapp.Adapters;

/**
 * Created by HP-USER on 27-04-2018.
 */

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.me.travelapp.POJO.BucketList;
import com.me.travelapp.R;

import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;


public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.MyViewHolder> {

    private Context context;
    private List<BucketList> bucketlists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            place = view.findViewById(R.id.place);
            checkBox = view.findViewById(R.id.bl_checkbox);

        }
    }


    public BucketListAdapter(Context context, List<BucketList> bucketLists) {
        this.context = context;
        this.bucketlists = bucketLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bucketlist_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BucketList bucketlist = bucketlists.get(position);

        holder.place.setText(bucketlist.getPlace());
        Log.e("database", "onBindViewHolder: "+bucketlist.getPlace()+"=="+bucketlist.getValue() );

        if(bucketlist.getValue().equalsIgnoreCase("false")){
        holder.checkBox.setChecked(false);
            holder.place.setPaintFlags(0);

        }
        else {
            holder.checkBox.setChecked(true);
            holder.place.setPaintFlags(holder.place.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    @Override
    public int getItemCount() {
        return bucketlists.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}