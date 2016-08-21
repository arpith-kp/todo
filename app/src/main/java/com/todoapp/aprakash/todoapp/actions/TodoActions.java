package com.todoapp.aprakash.todoapp.actions;

/**
 * Created by aprakash on 8/7/16.
 */
public interface TodoActions {
    String TODO_CREATE = "todo-create";
    String TODO_COMPLETE = "todo-complete";
    String TODO_DESTROY = "todo-destroy";
    String TODO_EDIT = "todo-edit";
    String TODO_DESTROY_COMPLETED = "todo-destroy-completed";
    String TODO_TOGGLE_COMPLETE_ALL = "todo-toggle-complete-all";
    String TODO_UNDO_COMPLETE = "todo-undo-complete";
    String TODO_UNDO_DESTROY = "todo-undo-destroy";

    String KEY_TEXT = "key-text";
    String KEY_DATE = "key-date";
    String KEY_ID = "key-id";
}
