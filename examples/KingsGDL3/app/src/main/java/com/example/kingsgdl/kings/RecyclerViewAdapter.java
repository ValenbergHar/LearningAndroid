package com.example.kingsgdl.kings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingsgdl.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<King> kingList;
    private List<King> kingSearchList;
    private Context context;
    private OnItemClickListener mListener;
    private int lastPosition = -1;

    public RecyclerViewAdapter(List<King> kingList, Context context) {
        this.kingList = kingList;
        this.context = context;
        kingSearchList = new ArrayList<>(kingList);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.king_one_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        King king = kingList.get(position);
        String getKingName = kingList.get(position).getKingName();


        Drawable colorId;
        int priority = Integer.valueOf(king.getId());
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getDrawable(R.drawable.gradient1);
                break;
            case 2:
                colorId = holder.itemView.getResources().getDrawable(R.drawable.gradient2);
                break;
            default:
                colorId = holder.itemView.getResources().getDrawable(R.drawable.gradient3);
                break;
        }
        holder.constraintLayout.setBackgroundDrawable(colorId);


        Spanned spanned = HtmlCompat.fromHtml(getKingName, HtmlCompat.FROM_HTML_MODE_COMPACT);
        holder.txt_king_name.setText(spanned);


//            holder.txt_king_name.setText(kingList.get(position).getKingName());

        holder.txt_king_date.setText(String.valueOf(kingList.get(position).getKingDateReign()));
//        holder.txt_occupation.setText(kingList.get(position).get);
        Glide.with(context).
                load(kingList.get(position).
                        getKingPhotos().
                        get(0)).
                into(holder.iv_king_picture);
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPosition = position;
        }
    }


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<King> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(kingSearchList);
            } else {
                for (King king : kingSearchList) {
                    if (king.getKingName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(king);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            kingList.clear();
            kingList.addAll((Collection<? extends King>) results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getFilter() {
        return filter;
    }

    @Override
    public int getItemCount() {
        return kingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_king_picture;
        TextView txt_king_name;
        TextView txt_king_date;
        TextView txt_occupation;
        ConstraintLayout constraintLayout;
        CardView parent;


        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            iv_king_picture = itemView.findViewById(R.id.iv_king_picture);
            txt_king_name = itemView.findViewById(R.id.txt_king_name);
            txt_king_date = itemView.findViewById(R.id.txt_date);
            txt_occupation = itemView.findViewById(R.id.txt_occupation);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutCard);

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
