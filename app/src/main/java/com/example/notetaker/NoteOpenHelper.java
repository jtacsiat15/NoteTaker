package com.example.notetaker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteFunTag";

    private static final String DATABASE_NAME = "noteDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTE = "Note";
    private static final String ID = "_id"; // _id is for use with adapters later
    private static final String TYPE = "note_type";
    private static final String CONTENT = "content";
    static final String TITLE = "title";

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "CREATE TABLE " + TABLE_NOTE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                TYPE + " TEXT, " +
                CONTENT + " TEXT)";
        Log.d(TAG, "Create Table: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertNote(Note note) {
        String sqlInsert = "INSERT INTO " + TABLE_NOTE + " VALUES(null, '" +
                note.getTitle() + "', '" +
                note.getType() + "', '" +
                note.getContent() + "')";
        Log.d(TAG, "insertNote: " + sqlInsert);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }

    public Note getNoteById(int id) {
        Note note = new Note();
        String sqlSelect = "SELECT * FROM " + TABLE_NOTE +
                " WHERE _id = " + id;
        Log.d(TAG, "selectAll: " + sqlSelect);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        if (cursor.moveToNext()) {
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setType(cursor.getString(2));
            note.setContent(cursor.getString(3));
            db.close();
            return note;
        }

        db.close();
        return null;
    }

    public Cursor getAllNotes() {
        String sqlSelect = "SELECT * FROM " + TABLE_NOTE;
        Log.d(TAG, "selectAll: " + sqlSelect);

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlSelect, null);

        return cursor;
    }

    public void deleteAllNotes() {
        String sqlDelete = "DELETE FROM " + TABLE_NOTE;
        Log.d(TAG, "deleteAllNotes: " + sqlDelete);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }


    public void deleteNote(int id) {
        String sqlDelete = "DELETE FROM " + TABLE_NOTE +
                " WHERE " + ID + " = " + id;
        Log.d(TAG, "deleteNote: " + sqlDelete);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

    public void updateNote(int id, Note note) {
        String sqlUpdate = "UPDATE " + TABLE_NOTE +
                " SET " + TITLE + " = '" + note.getTitle() + "', " +
                TYPE  + " = '" + note.getType() + "', " +
                CONTENT + " = '" + note.getContent() +
                "' WHERE " + ID + " = " + id;
        Log.d(TAG, "updateNote: " + sqlUpdate);

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }
}
