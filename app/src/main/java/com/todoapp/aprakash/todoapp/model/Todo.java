package com.todoapp.aprakash.todoapp.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aprakash on 8/7/16.
 */
public class Todo implements Cloneable, Comparable<Todo> {
    long id;
    boolean complete;

    public void setText(String text) {
        this.text = text;
    }

    String text;
    String date;


    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Todo(){}

    @Override
    public String toString(){
        return "Text"+this.getText()+"Date"+this.getDate();
    }

    public Todo(long id, String text, String date) {
        this.id = id;
        this.text = text;
        this.complete = false;
        this.date = date;
    }

    public Todo(long id, String text, boolean complete, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    @Override
    public Todo clone()  {
        return new Todo(id, text, complete, date);
    }

    @Override
    public int compareTo(Todo todo) {
        if (id == todo.getId()) {
            return 0;
        } else if (id < todo.getId()) {
            return -1;
        } else {
            return 1;
        }
    }
}
