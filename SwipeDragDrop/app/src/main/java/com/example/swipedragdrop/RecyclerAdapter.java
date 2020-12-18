package com.example.swipedragdrop;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    List<String> moviesList;
    List<String> moviesListAll;


    public RecyclerAdapter(List<String> moviesList) {
        this.moviesList = moviesList;
        moviesListAll = new ArrayList<>(moviesList);
    }

    //    private static final String TAG = "RecyclerAdapter";
//    int count = 0;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.i(TAG, "onCreateViewHolder: " + count++);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowCountTextView.setText(String.valueOf(position));
        holder.textView.setText(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterList.addAll(moviesListAll);
            } else {
                for (String movie : moviesListAll) {
                    if (movie.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(movie);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moviesList.clear();
            moviesList.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private TextView rowCountTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    moviesList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
