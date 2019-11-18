/**
 * This program is the main activity of a note taking app. It displays notes to a user and
 * allows the user to select a note to edit, create a new note, or select a note to delete
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 * No sources to cite.
 *
 * @author Alex Giacobbi and Jalen Tacsiat
 * @version v1.0 11/06/19
 *
 * Alex contributions:
 * Designed activity listeners
 * Formed Intents that are passed between activities
 * Created data structure to store notes in
 *
 * Jalen contributions:
 * Initially connected MainActivity class with NoteActivityClass
 * Created note Class
 * Created NoteLayout class for the noteActivity
 */
package com.example.notetaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main activity of our notes app. Here, users will be able
 * to create a new note, view a list of their existing notes, select a note to
 * edit and delete a note by long pressing. When a user is finished creating or
 * editing a note, they will return to this screen and see their note added to
 * the list.
 */
public class MainActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST_CODE = 1;
    static final String TAG = "inMainActivity";

    private SimpleCursorAdapter cursorAdapter;


    /**
     * When user finishes creating note, gets index and note from intent extras
     * @param requestCode a request code
     * @param resultCode activity result code
     * @param data intent received as result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null) {
                Note newNote = (Note) data.getSerializableExtra("note");
                int id = data.getIntExtra("id", -1);
                NoteOpenHelper helper = new NoteOpenHelper(this);

                Log.d(TAG, "id: " + id);

                if (id == -1) {
                    helper.insertNote(newNote);
                    Cursor cursor = helper.getAllNotes();
                    cursorAdapter.changeCursor(cursor);
                } else {
                    helper.updateNote(id, newNote);
                    Cursor cursor = helper.getAllNotes();
                    cursorAdapter.changeCursor(cursor);
                }
            }
        }
    }


    /**
     * Runs when app's main activity is created. Sets up click listeners for the new note
     * button and the adapter and listeners for the listview containing the notes. Initializes
     * a notebook to store notes in
     * @param savedInstanceState bundle containing saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainLayout layout = new MainLayout(this);
        setContentView(layout);

        final ListView notes = findViewById(R.id.notesListView);
        final NoteOpenHelper helper = new NoteOpenHelper(this);
        Cursor cursor = helper.getAllNotes();

        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_activated_1,
                cursor,
                new String[] {NoteOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0 // leave default
        );
        notes.setAdapter(cursorAdapter);

        notes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        notes.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.deleteMenuItem:
                        SparseBooleanArray checked = notes.getCheckedItemPositions();
                        NoteOpenHelper helper = new NoteOpenHelper(MainActivity.this);
                        Log.d(TAG, "onActionItemClicked: " + checked.toString());

                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.valueAt(i)) {
                                int id = (int) cursorAdapter.getItemId(checked.keyAt(i));
                                helper.deleteNote(id);
                                Log.d(TAG, "onActionItemClicked: " + id + ", " + i);
                            }
                        }

                        Cursor cursor = helper.getAllNotes();
                        cursorAdapter.changeCursor(cursor);
                        actionMode.finish(); // exit CAM
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Click listener for the array adapter. Creates an intent to pass the selected note
             * to NoteActivity and the index of the note to be edited in the ArrayList.
             * @param adapterView adapter view for our note list
             * @param view the listview that user is selecting from
             * @param i index selected
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                int id = (int) cursorAdapter.getItemId(i);
                Log.d(TAG, "onItemClick POSITION: " + i);
                Note note = helper.getNoteById(id);

                intent.putExtra("note", note);
                intent.putExtra("id", id);
                Log.d(TAG, "NoteID: " + id);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        /*notes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            *//**
             * Executes when a user long clicks on an element. Shows popup menu to delete
             * an item in a list. If user confirms, item is removed from the list
             * @param adapterView adapter view of the list
             * @param view a listview containing notes
             * @param i index of click
             * @param l
             * @return true if event is handled, false otherwise
             *//*
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                final int deleteIndex = i;
                final long id = cursorAdapter.getItemId(i);

                alertBuilder.setTitle("Delete a Note")
                        .setMessage("Are you sure you want to delete your " + adapterView.getItemAtPosition(i) + " note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            *//**
                             * Deletes a note when user confirms delete in popup dialog box
                             * @param dialogInterface a dialog box
                             * @param i
                             *//*
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(TAG, "delete index: " + i);
                                helper.deleteNote((int)id);
                                cursorAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return true;
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String TAG = "itemSelectedTag";
        switch (id) {
            case R.id.addMenuItem:
                //add a note
                Log.d(TAG, "in add");
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("note", new Note());
                intent.putExtra("index", -1);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                //Toast.makeText(this, "add item", Toast.LENGTH_SHORT);
                return true;
            case R.id.deleteMenuItem:
                //delete items
                Log.d(TAG, "in delete");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete all Notes")
                        .setMessage("Are you sure you want to delete all notes?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NoteOpenHelper helper = new NoteOpenHelper(MainActivity.this);
                                helper.deleteAllNotes();
                                Cursor cursor = helper.getAllNotes();
                                cursorAdapter.changeCursor(cursor);
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();

                //Toast.makeText(this, "delete item", Toast.LENGTH_SHORT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
