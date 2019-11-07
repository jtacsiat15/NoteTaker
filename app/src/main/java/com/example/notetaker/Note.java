package com.example.notetaker;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Note class that contains fields for each note object
 *
 */
public class Note implements Serializable {
    private String title;
    private String content;
    private String type;

    /**
     * DVC for note class
     * Gives fields blank values
     */
    public Note(){
        this.title="";
        this.content="";
        this.type="";
    }

    /**
     * EVC for note class
     * @param title user input from title editText
     * @param content user input from content editText
     * @param type user selection from listView
     */
    public Note(String title, String content, String type){
        this.title = type;
        this.content = content;
        this.type = type;
    }

    /**
     * @return a string containing the type of note the user selected
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the field type to the user input
     * @param type user selection for type of note
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return a string containing the contents of the notes
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the field to the users input from the content editText
     * @param content string containing what the user entered in the content editText
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return string containing what the user entered in the title editText
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title field to the user input from the title editText
     * @param title string containing what the user enter in the title editText
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the title of the note so it can be displayed in the listView
     */
    @NonNull
    @Override
    public String toString() {
        return title.length() > 0 ? this.title : "[empty title]";
    }
}
