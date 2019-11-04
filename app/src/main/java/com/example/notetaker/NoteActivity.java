package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteLayout noteLayout = new NoteLayout(this);
        setContentView(noteLayout);
        final Note note = new Note();
        final EditText noteTitle = (EditText)findViewById(R.id.noteTitle);
        final EditText content = (EditText)findViewById(R.id.noteContent);
        final Spinner noteType = (Spinner)findViewById(R.id.noteType);

        Intent intent = getIntent();
        if(intent != null){
            String title =  intent.getStringExtra("title");
            String noteContent = intent.getStringExtra("content");
            String type = intent.getStringExtra("type");
            noteTitle.setText(title);
            content.setText(noteContent);
            //noteType.
        }

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
        Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setTitle(noteTitle.getText().toString());
                note.setContent(content.getText().toString());
                Log.d(TAG, "Title: " + note.getTitle() + " Content: " + note.getContent() + " Type: " +  note.getType());
                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                intent.putExtra("type", note.getType());
                setResult(RESULT_OK, intent);
                NoteActivity.this.finish();
            }
        });
    }
}
