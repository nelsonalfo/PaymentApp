package com.nelsonalfo.paymentapp.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nelsonalfo.paymentapp.R;

import java.util.List;

public abstract class ImageAndTitleRecyclerViewAdapter<T> extends RecyclerView.Adapter<ImageAndTitleViewHolder<T>> {
    private List<T> dataSource;
    private Listener<T> listener;

    public ImageAndTitleRecyclerViewAdapter(List<T> dataSource, Listener<T> listener) {
        this.dataSource = dataSource;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageAndTitleViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        final View viewItem = LayoutInflater.from(context).inflate(R.layout.item_image_and_title, viewGroup, false);

        return getViewHolder(viewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageAndTitleViewHolder<T> imageAndTitleViewHolder, int position) {
        final T item = dataSource.get(position);

        imageAndTitleViewHolder.bind(item);
        imageAndTitleViewHolder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

    public abstract ImageAndTitleViewHolder<T> getViewHolder(View view);

    public interface Listener<T>{
        void onItemSelected(T item);
    }
}
