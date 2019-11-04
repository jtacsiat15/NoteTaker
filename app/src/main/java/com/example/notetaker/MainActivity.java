package com.example.notetaker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST_CODE = 1;
    final String TAG = "inMainActivity";
    String title;
    String content;
    String type;
    List<Note> notebook = new ArrayList<>();
    ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            notebook
    );
    //Note note = new Note();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            title = data.getStringExtra("title");
            content = data.getStringExtra("content");
            type = data.getStringExtra("type");
//            note.setTitle(title);
//            note.setType(type);
//            note.setContent(content);
            Log.d(TAG, "\n\ngetting extras\n\n" + "Title: " + title + " content: " + content + " Type: " + type);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        Button newNote = new Button(this);
        ListView notesList = new ListView(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        newNote.setText(R.string.add_note);
        newNote.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//        final List<Note> notebook = new ArrayList<>();
//        final ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                notebook
//        );
        layout.addView(newNote);
        notesList.setAdapter(arrayAdapter);
        layout.addView(notesList);
        setContentView(layout);

        //notebook.add(note);
        //notebook.add("two");
        //notebook.add("three");

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add second activity to use explicit intent
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                //Intent intent = getIntent();
                Note note = new Note();
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                note.setTitle(intent.getStringExtra("title"));
                //intent.putExtra("type", note.getType());
                note.setContent(intent.getStringExtra("content"));
                //Log.d("inNewNote", "Title: " + note.getTitle() + " content: " + note.getContent() + "Type: " + note.getType());
                notebook.add(note);
            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO add second activity to use explicit intent
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete a Note")
                        .setMessage("Are you sure you want to delete your " + adapterView.getItemAtPosition(i) + " note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO remove note from list
                                //notebook.remove(1);
                                //arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return false;
            }
        });
    }
}
