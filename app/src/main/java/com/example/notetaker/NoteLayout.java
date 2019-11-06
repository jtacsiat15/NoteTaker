package com.example.notetaker;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

public class NoteLayout extends GridLayout {
    static final String TAG = "inNoteLayout";

    public NoteLayout(final Context context){
        super(context);
        setColumnCount(3);

        GridLayout.LayoutParams layoutParams = new LayoutParams();
        //layoutParams.columnSpec = GridLayout.spec(0, 3, 1);
        layoutParams.width = LayoutParams.MATCH_PARENT;

        //creates title editText
        final EditText noteTitle = new EditText(context);
        noteTitle.setLayoutParams(layoutParams);
        noteTitle.setHint("Title");
        noteTitle.setId(R.id.noteTitle);
        addView(noteTitle);

        //adds spinner type
        final Spinner noteType = new Spinner(context);
        noteType.setLayoutParams(layoutParams);
        noteType.setId(R.id.noteType);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.noteTypesArray,
                android.R.layout.simple_spinner_item);
        noteType.setAdapter(spinnerArrayAdapter);
        addView(noteType);

        //adds content to take notes with
        final EditText content = new EditText(context);
        content.setLayoutParams(layoutParams);
        content.setId(R.id.noteContent);

        content.setHint("Content");
        addView(content);

        //adds done button
        Button doneButton = new Button(context);
        content.setLayoutParams(layoutParams);
        doneButton.setId(R.id.doneButton);
        doneButton.setText("Done");
        addView(doneButton);
    }

}
