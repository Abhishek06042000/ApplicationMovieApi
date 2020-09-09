package com.example.themovieapiwork.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapiwork.R;
import com.example.themovieapiwork.model.Review.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public void setReviews(List<Review> review) {
        this.reviews = review;
        notifyDataSetChanged();

    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.author)
        TextView authorView;
        @BindView(R.id.content)
        TextView contentView;

        private ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Review review) {
            authorView.setText(review.getAuthor());
            contentView.setText(review.getContent());
        }
    }
}
