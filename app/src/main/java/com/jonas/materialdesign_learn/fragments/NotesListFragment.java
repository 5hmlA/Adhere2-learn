package com.jonas.materialdesign_learn.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonas.materialdesign_learn.R;
import com.jonas.yun_library.adapter.AwesomeRecvAdapter;
import com.jonas.yun_library.adapter.RecyclerHolder;
import com.jonas.yun_library.models.Note;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gordon Wong on 7/18/2015.
 * <p>
 * Generic fragment displaying a list of notes.
 */
public abstract class NotesListFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract int getNumColumns();

    protected abstract int getNumItems();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // Setup list
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(),
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapte = getAdapte(recyclerView);
        recyclerView.setAdapter(adapte);

        return view;
    }

    protected RecyclerView.Adapter getAdapte(RecyclerView recyclerView) {
        return new AwesomeRecvAdapter<Note>(generateNotes(recyclerView.getContext(), getNumItems()), getLayoutResId()) {

            @Override
            public void convert(RecyclerHolder holder, int position, Note itemData) {
                refreshHolder(holder, itemData);
            }
        };
    }

    protected List<Note> generateNotes(Context context, int numNotes) {
        ArrayList<Note> notes = new ArrayList<>(numNotes);
        for (int i = 0; i < numNotes; i++) {
            notes.add(Note.randomNote(context));
        }
        return notes;
    }

    protected void refreshHolder(RecyclerHolder holder, Note itemData) {
        Note noteModel = itemData;
        String title = noteModel.getTitle();
        String note = noteModel.getNote();
        String info = noteModel.getInfo();
        int infoImage = noteModel.getInfoImage();
        int color = noteModel.getColor();

        // Set text
        holder.setText(R.id.note_title, title);
        holder.setText(R.id.note_text, note);
        holder.setText(R.id.note_info, info);

        // Set image
        holder.setImageResource(R.id.note_info_image, infoImage);

        // Set visibilities
        holder.setVisibility(R.id.note_title, TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        holder.setVisibility(R.id.note_text, TextUtils.isEmpty(note) ? View.GONE : View.VISIBLE);
        holder.setVisibility(R.id.note_info_layout, TextUtils.isEmpty(info) ? View.GONE : View.VISIBLE);

        // Set padding
        int paddingTop = (holder.getVisibility(R.id.note_title) != View.VISIBLE) ? 0
                : holder.itemView.getContext().getResources()
                .getDimensionPixelSize(R.dimen.note_content_spacing);
        TextView noteTextView = holder.getView(R.id.note_text);
        noteTextView.setPadding(noteTextView.getPaddingLeft(), paddingTop,
                noteTextView.getPaddingRight(), noteTextView.getPaddingBottom());

        // Set background color
        ((CardView) holder.itemView).setCardBackgroundColor(color);
    }
}
