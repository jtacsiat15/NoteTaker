package com.example.notetaker;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.util.Log;
import android.view.Gravity;
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
        layoutParams.rowSpec = GridLayout.spec(1, 1, 1);
        layoutParams.columnSpec = GridLayout.spec(0, 3, 1);

        //creates title editText
        final EditText noteTitle = new EditText(context);
        noteTitle.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0, 1, 1/2), GridLayout.spec(0, 2, 30)));
        noteTitle.setHint("Title");
        noteTitle.setId(R.id.noteTitle);
        noteTitle.setGravity(Gravity.TOP);
        addView(noteTitle);

        //adds spinner type
        final Spinner noteType = new Spinner(context);
        noteType.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0, 1, 1/2), GridLayout.spec(2, 1, 1)));
        noteType.setId(R.id.noteType);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.noteTypesArray,
                android.R.layout.simple_spinner_item);
        noteType.setAdapter(spinnerArrayAdapter);
        noteType.setGravity(Gravity.TOP);
        addView(noteType);

        //adds content to take notes with
        final EditText content = new EditText(context);
        content.setLayoutParams(layoutParams);
        content.setId(R.id.noteContent);
        content.setGravity(Gravity.TOP);

        content.setHint("Content");
        addView(content);

        //adds done button
        Button doneButton = new Button(context);
        GridLayout.LayoutParams buttonParams = new LayoutParams();
        buttonParams.rowSpec = GridLayout.spec(2, 1, 1/2);
        buttonParams.columnSpec = GridLayout.spec(0, 3, 1);
        doneButton.setLayoutParams(buttonParams);
        content.setLayoutParams(layoutParams);
        doneButton.setId(R.id.doneButton);
        doneButton.setGravity(Gravity.CENTER);
        doneButton.setText("Done");
        addView(doneButton);
    }

}
