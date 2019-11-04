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
    List<Note> notebook = new ArrayList<>();
    Note newNote;
    final String TAG = "inMainActivity";

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
            newNote = null;
        }
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
        final ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notebook
        );
        layout.addView(newNote);
        notesList.setAdapter(arrayAdapter);
        layout.addView(notesList);
        setContentView(layout);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", new Note());
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", notebook.get(i));
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
