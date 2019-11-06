package com.example.notetaker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
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
    static final String TAG = "inMainActivity";

    ArrayAdapter<Note> arrayAdapter;
    ListView notes;
    Button createNewNote;
    List<Note> notebook;
    Note newNote;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            newNote = (Note)data.getSerializableExtra("note");

            Log.d(TAG, "Title: " + newNote.getTitle() + " content: " + newNote.getContent() + " Type: " + newNote.getType());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (newNote != null) {
            notebook.add(newNote);
            arrayAdapter.notifyDataSetChanged();
            Log.d(TAG, "onResume: " + notebook.toString());
            newNote = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainLayout layout = new MainLayout(this);
        setContentView(layout);

        notebook = new ArrayList<>();
        createNewNote = findViewById(R.id.newNoteButton);
        notes = findViewById(R.id.notesListView);
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notebook
        );

//        final List<Note> notebook = new ArrayList<>();
//        final ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                notebook
//        );
        notes.setAdapter(arrayAdapter);

        createNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", new Note());
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", notebook.get(i));
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        notes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete a Note")
                        .setMessage("Are you sure you want to delete your " + adapterView.getItemAtPosition(i) + " note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO remove note from list
                                Log.d(TAG, "onClick: " + i);
                                notebook.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return false;
            }
        });
    }
}
