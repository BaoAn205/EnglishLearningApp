package com.example.apphoctiengtrung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {

    private final List<String> suggestions;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onSuggestionClick(String suggestion);
    }

    public SuggestionAdapter(List<String> suggestions, OnItemClickListener listener) {
        this.suggestions = suggestions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        String suggestion = suggestions.get(position);
        holder.bind(suggestion, listener);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView suggestionTextView;

        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            suggestionTextView = itemView.findViewById(R.id.text_view_suggestion);
        }

        public void bind(final String suggestion, final OnItemClickListener listener) {
            suggestionTextView.setText(suggestion);
            itemView.setOnClickListener(v -> listener.onSuggestionClick(suggestion));
        }
    }
}
