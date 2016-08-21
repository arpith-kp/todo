package com.todoapp.aprakash.todoapp.actions;

import com.todoapp.aprakash.todoapp.dispatcher.Dispatcher;
import com.todoapp.aprakash.todoapp.model.Todo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aprakash on 8/7/16.
 */
public class ActionsCreator {

    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    public void create(String text, String date) {
        dispatcher.dispatch(
                TodoActions.TODO_CREATE,
                TodoActions.KEY_TEXT,
                text,
                TodoActions.KEY_DATE,
                date
        );

    }

    public void editText(long id, String text) {
        dispatcher.dispatch(
                TodoActions.TODO_EDIT,
                TodoActions.KEY_ID, id,
                TodoActions.KEY_TEXT,
                text

        );

    }

    public void editDate(long id, String date){
        dispatcher.dispatch(
                TodoActions.TODO_EDIT,
                TodoActions.KEY_ID, id,
                TodoActions.KEY_DATE,
                date
        );
    }
    public void destroy(long id) {
        dispatcher.dispatch(
                TodoActions.TODO_DESTROY,
                TodoActions.KEY_ID, id
        );
    }

    public void undoDestroy() {
        dispatcher.dispatch(
                TodoActions.TODO_UNDO_DESTROY
        );
    }

    public void toggleComplete(Todo todo) {
        long id = todo.getId();
        String actionType = todo.isComplete() ? TodoActions.TODO_UNDO_COMPLETE : TodoActions.TODO_COMPLETE;

        dispatcher.dispatch(
                actionType,
                TodoActions.KEY_ID, id
        );
    }

    public void toggleCompleteAll() {
        dispatcher.dispatch(TodoActions.TODO_TOGGLE_COMPLETE_ALL);
    }

    public void destroyCompleted() {
        dispatcher.dispatch(TodoActions.TODO_DESTROY_COMPLETED);
    }
}