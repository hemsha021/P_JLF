package com.applet.jlf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applet.jlf.Activity.SearchPage;
import com.applet.jlf.R;

import static android.app.Activity.RESULT_OK;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private String[] result;
    private Context mContext;

    public SearchAdapter(String[] result, Context mContext){
        this.result = result;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search,parent,false);

        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {

        if(result[position]!=null){
            holder.stopName.setText(result[position]);
        }

    }

    @Override
    public int getItemCount() {
        return result.length;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView stopName;

        public SearchViewHolder(View itemView) {
            super(itemView);


            stopName = (TextView) itemView.findViewById(R.id.stop_name);

            stopName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SearchPage)mContext).sendPosition(getLayoutPosition());
                }
            });
        }
    }
}
