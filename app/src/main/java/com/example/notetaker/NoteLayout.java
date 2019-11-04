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
    public NoteLayout(final Context context){
        super(context);
        setColumnCount(2);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        this.setLayoutParams(layoutParams);

        final String TAG = "inNoteLayout";

        //creates title editText
        final EditText noteTitle = new EditText(context);
        noteTitle.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0, 1, 1), GridLayout.spec(0)));
        noteTitle.setHint("Title");
        noteTitle.setId(R.id.noteTitle);
        addView(noteTitle);

        //adds spinner type
        final Spinner noteType = new Spinner(context);
        noteType.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0, 1, 1), GridLayout.spec(1)));
        noteType.setId(R.id.noteType);
        String[] typeArray = getResources().getStringArray(R.array.noteTypesArray);
        Log.d(TAG, "Creating Spinner: " + typeArray[0]);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(context,R.array.noteTypesArray,android.R.layout.simple_spinner_item);
        noteType.setAdapter(spinnerArrayAdapter);
        addView(noteType);

        //adds content to take notes with
        final EditText content = new EditText(context);
        content.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(1, 1, 5), GridLayout.spec(0, 2)));
        content.setId(R.id.noteContent);

        content.setHint("Content");
        addView(content);

        //adds done button
        Button doneButton = new Button(context);
        content.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(2, 1, 1), GridLayout.spec(0, 2)));
        doneButton.setId(R.id.doneButton);
        doneButton.setText("Done");
        addView(doneButton);
    }

}
