package com.exwebtech.shravantest.modules.Post.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exwebtech.shravantest.R;
import com.exwebtech.shravantest.modules.Post.interfaces.ItemSelectedInterface;
import com.exwebtech.shravantest.modules.Post.model.HitsResponseModel;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<HitsResponseModel.HitsData> hitLists;
    public ItemSelectedInterface itemSelectedInterface;
    private Context mContext;
    private OnLoadMoreListener loadMoreListener;


    public RecyclerViewAdapter(Context context, List<HitsResponseModel.HitsData> hitLists) {
        this.hitLists = hitLists;
        this.mContext = context;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && loadMoreListener != null) {
            loadMoreListener.onAddMore();
        }

        HitsResponseModel.HitsData hitsData = hitLists.get(position);

        holder.titleTv.setText(hitsData.getTitle());
        holder.createdAtTv.setText(hitsData.getCreated_at());

        if (hitsData.isSelected()) {
            holder.switchCompat.setChecked(true);
        } else {
            holder.switchCompat.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO select/deselect post here
                hitsData.setSelected(!hitsData.isSelected());
                notifyItemChanged(holder.getAdapterPosition(), hitsData);
                itemSelectedInterface.onItemSelected(hitsData);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hitLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView titleTv;
        private AppCompatTextView createdAtTv;
        private SwitchCompat switchCompat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
            createdAtTv = itemView.findViewById(R.id.created_tv);
            switchCompat = itemView.findViewById(R.id.switch_btn);

        }
    }


    public interface OnLoadMoreListener {
        void onAddMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
