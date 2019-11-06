package com.example.notetaker;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

public class MainLayout extends GridLayout {
    public MainLayout(Context context) {
        super(context);

        ListView notesListView;
        Button newNoteButton;

        setColumnCount(1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.rowSpec = GridLayout.spec(0, 1,1/2);
        layoutParams.columnSpec = GridLayout.spec(0, 1, 1);

        newNoteButton = new Button(context);
        newNoteButton.setId(R.id.newNoteButton);
        newNoteButton.setText(R.string.add_note);
        newNoteButton.setLayoutParams(layoutParams);
        this.addView(newNoteButton);

        GridLayout.LayoutParams notesListLayout = new GridLayout.LayoutParams();
        notesListLayout.rowSpec = GridLayout.spec(1, 1, 1);
        notesListLayout.columnSpec = GridLayout.spec(0, 1, 1);
        notesListView = new ListView(context);
        notesListView.setId(R.id.notesListView);
        notesListLayout.setGravity(Gravity.TOP);
        notesListView.setLayoutParams(notesListLayout);
        this.addView(notesListView);
    }
}
