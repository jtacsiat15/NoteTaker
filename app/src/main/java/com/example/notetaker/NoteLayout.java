/**
 * The layout for NoteActivity is defined programmatically here. This layout uses a two-column
 * grid layout. There are EditTexts for the user to input a title and content, and a Spinner
 * to choose the note's type. There is a button at the bottom of the screen to save the note
 * and return to the home screen
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v1.0 11/06/19
 *
 * Jalen Contributions:
 * Created Buttons and EditTexts for the noteActivity layout
 * Setup layout parameters
 */
package com.example.notetaker;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

/**
 * This class is used to create the note layout using gridLayout
 */
public class NoteLayout extends GridLayout {
    public NoteLayout(final Context context){
        super(context);
        setColumnCount(3);

        GridLayout.LayoutParams layoutParams = new LayoutParams();
        layoutParams.rowSpec = GridLayout.spec(1, 1, 1);
        layoutParams.columnSpec = GridLayout.spec(0, 3, 1);

        /*
         * creates title editText which will be displayed on the note listview on the main activity
         */
        final EditText noteTitle = new EditText(context);
        noteTitle.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0, 1, 1/2), GridLayout.spec(0, 2, 30)));
        noteTitle.setHint("Title");
        noteTitle.setId(R.id.noteTitle);
        noteTitle.setGravity(Gravity.TOP);
        addView(noteTitle);

        /*
         * adds spinner type used to allow the user to select the type of note
         */
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

        /*
         * adds EditText named content used to take notes to the screen
         */
        final EditText content = new EditText(context);
        content.setLayoutParams(layoutParams);
        content.setId(R.id.noteContent);
        content.setGravity(Gravity.TOP);
        content.setHint("Content");
        addView(content);

        /*
         * adds a button called doneButton to the screen
         */
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
