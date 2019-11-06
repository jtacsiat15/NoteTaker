package com.example.notetaker;

import android.content.Context;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

public class MainLayout extends GridLayout {
    ListView notesListView;
    Button newNoteButton;

    public MainLayout(Context context) {
        super(context);

        setColumnCount(1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;

        newNoteButton = new Button(context);
        newNoteButton.setId(R.id.newNoteButton);
        newNoteButton.setText(R.string.add_note);
        newNoteButton.setLayoutParams(layoutParams);
        this.addView(newNoteButton);

        notesListView = new ListView(context);
        notesListView.setId(R.id.notesListView);
        //notesListView.setLayoutParams(layoutParams);
        this.addView(notesListView);
    }
}
