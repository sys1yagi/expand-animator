package com.sys1yagi.android.expandanimator.sample.view;

import com.sys1yagi.android.expandanimator.sample.R;
import com.sys1yagi.android.expandanimator.sample.databinding.ViewItemBinding;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItem> {

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    List<String> titles = new ArrayList<>();

    OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            // no op
        }
    };

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void add(String title) {
        titles.add(title);
    }

    @Override
    public ListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListItem(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ListItem holder, int position) {
        ViewItemBinding binding = DataBindingUtil.bind(holder.itemView);
        binding.title.setText(titles.get(position));
        binding.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
