package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NoteActivity extends AppCompatActivity {
    final String TAG = "inNoteActivity";
    final Note note = new Note();
    static final int PERSONAL = 0;
    static final int SCHOOL = 1;
    static final int WORK = 2;
    static final int OTHER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteLayout noteLayout = new NoteLayout(this);
        setContentView(noteLayout);
        final EditText noteTitle = findViewById(R.id.noteTitle);
        final EditText content = findViewById(R.id.noteContent);
        final Spinner noteType = findViewById(R.id.noteType);

        Intent intent = getIntent();
        if(intent != null){
            String title =  intent.getStringExtra("title");
            String noteContent = intent.getStringExtra("content");
            String type = intent.getStringExtra("type");
            noteTitle.setText(title);
            content.setText(noteContent);
        }

        noteTitle.setText(note.getTitle());
        content.setText(note.getContent());
        noteType.setSelection(getAdapterIndex());

        noteType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            @Override
            public void onClick(View view) {
                note.setTitle(noteTitle.getText().toString());
                note.setContent(content.getText().toString());
                Log.d(TAG, "Title: " + note.getTitle() + " Content: " + note.getContent() + " Type: " +  note.getType());

                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("note", note);
                setResult(RESULT_OK, intent);
                NoteActivity.this.finish();
            }
        });
    }

    private int getAdapterIndex() {
        if (note.getType().equals("Other")) {
            return OTHER;
        } else if (note.getType().equals("School")) {
            return SCHOOL;
        } else if (note.getType().equals("Work")) {
            return WORK;
        }
        return PERSONAL;
    }
}
