package com.example.apphoctiengtrung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessonList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Lesson lesson);
    }

    public LessonAdapter(List<Lesson> lessonList, OnItemClickListener listener) {
        this.lessonList = lessonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.bind(lesson, listener);
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView title, hskLevel, description, premiumTag;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_lesson_title);
            hskLevel = itemView.findViewById(R.id.text_view_lesson_hsk);
            description = itemView.findViewById(R.id.text_view_lesson_description);
            premiumTag = itemView.findViewById(R.id.text_view_premium_tag);
        }

        public void bind(final Lesson lesson, final OnItemClickListener listener) {
            title.setText(lesson.getTitle());
            hskLevel.setText(lesson.getHskLevel());
            description.setText(lesson.getDescription());

            if (lesson.isPremium()) {
                premiumTag.setVisibility(View.VISIBLE);
            } else {
                premiumTag.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(lesson));
        }
    }
}
