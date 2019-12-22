package com.applet.jlf.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applet.jlf.R;

public class RouteAdapter extends RecyclerView.Adapter <RouteAdapter.RouteViewHolder> {

    private String[] result;

    public RouteAdapter(String[] result){
        this.result = result;
    }

    @NonNull
    @Override
    public RouteAdapter.RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_route,parent,false);

        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.RouteViewHolder holder, int position) {

            if(result[position]!=null){
                holder.stopName.setText(result[position]);
                holder.stepTitle.setText(""+position);
            }
        Log.i("Adapter","In");

    }

    @Override
    public int getItemCount() {
        return result.length;
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{


        TextView stopName;
        TextView stepTitle;

        public RouteViewHolder(View itemView) {

            super(itemView);

            stopName = (TextView) itemView.findViewById(R.id.title);
            stepTitle = (TextView) itemView.findViewById(R.id.step_num);

            stopName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
        }
    }
}
