package com.example.a2020_hack.src.searchresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2020_hack.R;
import com.example.a2020_hack.src.searchresult.models.SearchPublicTransResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.CustomViewHolder> {

    private Context mContext;
    private ArrayList<SearchPublicTransResult> mArrayList;

    public SearchResultAdapter(Context context, ArrayList<SearchPublicTransResult> arrayList){
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_result, parent, false);
        SearchResultAdapter.CustomViewHolder viewHolder = new SearchResultAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.mTextViewPrice.setText(mArrayList.get(position).getInfo().getPayment()+"원");
        holder.mTextViewTime.setText(mArrayList.get(position).getInfo().getTotalTime()+"분");
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewPrice;
        TextView mTextViewTime;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTextViewPrice = itemView.findViewById(R.id.list_search_result_tv_price);
            this.mTextViewTime = itemView.findViewById(R.id.list_search_result_tv_time);
        }
    }
}
