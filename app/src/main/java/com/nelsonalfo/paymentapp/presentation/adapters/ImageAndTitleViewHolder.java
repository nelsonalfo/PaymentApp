package com.nelsonalfo.paymentapp.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nelsonalfo.paymentapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ImageAndTitleViewHolder<T> extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.image)
    ImageView image;

    public ImageAndTitleViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bind(T data);
}
