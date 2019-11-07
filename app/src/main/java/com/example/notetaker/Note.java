/**
 * @author Alex Giacobbi and Jalen Tacsiat
 * Alex contributions:
 * made Note Serializable
 */

package com.example.notetaker;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Note implements Serializable {
    private String title;
    private String content;
    private String type;

    public Note(){
        this.title="";
        this.content="";
        this.type="";
    }
    public Note(String title, String content, String type){
        this.title = type;
        this.content = content;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title.length() > 0 ? this.title : "[empty title]";
    }
}
