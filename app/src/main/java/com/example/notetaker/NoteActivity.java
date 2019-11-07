/**
 * @author Alex Giacobbi and Jalen Tacsiat
 * Alex contributions:
 * mangaged loading in note from intent
 * formed intents to pass back to MainActivity
 * set spinner to type specified in note
 */

package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * This class is the activity for users to edit a note. Users can change the title,
 * content, type and save the note when they are finished.
 */
public class NoteActivity extends AppCompatActivity {
    final String TAG = "inNoteActivity";
    static final int PERSONAL = 0;
    static final int SCHOOL = 1;
    static final int WORK = 2;
    static final int OTHER = 3;

    private Note note;
    private int index;

    /**
     * When activity is initialized, note is gathered from intent and view is updated
     * to reflect current note contents. Sets up click listener for done button and
     * connects all view elements from layout
     * @param savedInstanceState bundle saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteLayout noteLayout = new NoteLayout(this);
        setContentView(noteLayout);
        final EditText noteTitle = findViewById(R.id.noteTitle);
        final EditText content = findViewById(R.id.noteContent);
        final Spinner noteType = findViewById(R.id.noteType);
        index = -1;

        Intent intent = getIntent();
        if(intent != null){
            note = (Note)intent.getSerializableExtra("note");
            index = intent.getIntExtra("index", -1);
            noteTitle.setText(note.getTitle());
            content.setText(note.getContent());
        }

        noteTitle.setText(note.getTitle());
        content.setText(note.getContent());
        noteType.setSelection(getAdapterIndex());

        noteType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Sets the note type when a new type is selected from the spinner
             * @param adapterView adapter view for type selection spinner
             * @param view spinner element
             * @param i index of spinner selection
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                note.setType(selection);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When user clicks 'Done', gets all changes from the edittexts and updates the
             * note object then passes the note object and index in an intent back to main
             * activity. Finishes NoteActivity
             * @param view done button
             */
            @Override
            public void onClick(View view) {
                note.setTitle(noteTitle.getText().toString());
                note.setContent(content.getText().toString());
                Log.d(TAG, "Title: " + note.getTitle() + " Content: " + note.getContent() + " Type: " +  note.getType());

                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("note", note);
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                NoteActivity.this.finish();
            }
        });
    }

    /**
     * Gets the index in the adapter of a string type
     * @return int indicating index in spinner of a certain type
     */
    private int getAdapterIndex() {
        switch (note.getType()) {
            case "Other":
                return OTHER;
            case "School":
                return SCHOOL;
            case "Work":
                return WORK;
            default:
                return PERSONAL;
        }
    }
}
