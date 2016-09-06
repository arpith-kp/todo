package com.todoapp.aprakash.todoapp.stores;

/**
 * Created by aprakash on 8/7/16.
 */

import com.todoapp.aprakash.todoapp.MainActivity;
import com.todoapp.aprakash.todoapp.actions.Action;
import com.todoapp.aprakash.todoapp.actions.TodoActions;
import com.todoapp.aprakash.todoapp.dispatcher.Dispatcher;
import com.todoapp.aprakash.todoapp.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.squareup.otto.Subscribe;


public class TodoStore extends Store {

    private static TodoStore instance;
    private final List<Todo> todos;
    private Todo lastDeleted;


    protected TodoStore(Dispatcher dispatcher, TodoStoreDBHelper dbHelper) {
        super(dispatcher, dbHelper);
        todos = new ArrayList<>();



    }
    public static TodoStore get(Dispatcher dispatcher, TodoStoreDBHelper dbHelper) {
        if (instance == null) {
            instance = new TodoStore(dispatcher, dbHelper);
        }
        return instance;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public boolean canUndo() {
        return lastDeleted != null;
    }


    @Override
    @Subscribe
    @SuppressWarnings("unchecked")
    public void onAction(Action action) {
        long id;
        switch (action.getType()) {
            case TodoActions.TODO_CREATE:
                String text = ((String) action.getData().get(TodoActions.KEY_TEXT));
                String date = ((String) action.getData().get(TodoActions.KEY_DATE));
                create(text, date);
//                create(text, date);
                emitStoreChange();
                break;

            case TodoActions.TODO_DESTROY:
                String textName = ((String) action.getData().get(TodoActions.KEY_TEXT));
                destroy(textName);
                emitStoreChange();
                break;

            case TodoActions.TODO_EDIT:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                String editText = ((String) action.getData().get(TodoActions.KEY_TEXT));
                String editDate = ((String) action.getData().get(TodoActions.KEY_DATE));
                edit(id, editText, editDate);
                emitStoreChange();
                break;

            case TodoActions.TODO_UNDO_DESTROY:
                undoDestroy();
                emitStoreChange();
                break;

            case TodoActions.TODO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(id, true);
                emitStoreChange();
                break;

            case TodoActions.TODO_UNDO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(id, false);
                emitStoreChange();
                break;

            case TodoActions.TODO_DESTROY_COMPLETED:
                destroyCompleted();
                emitStoreChange();
                break;

            case TodoActions.TODO_TOGGLE_COMPLETE_ALL:
                updateCompleteAll();
                emitStoreChange();
                break;

        }

    }

    private void destroyCompleted() {
        dbHelper.deleteAllTodosFromDb();
    }

    private void updateCompleteAll() {
        if (areAllComplete()) {
            updateAllComplete(false);
        } else {
            updateAllComplete(true);
        }
    }

    private boolean areAllComplete() {
        for (Todo todo : todos) {
            if (!todo.isComplete()) {
                return false;
            }
        }
        return true;
    }

    private void updateAllComplete(boolean complete) {
        for (Todo todo : todos) {
            todo.setComplete(complete);
        }
    }

    private void updateComplete(long id, boolean complete) {
        Todo todo = getById(id);
        if (todo != null) {
            todo.setComplete(complete);
        }
    }

    private void undoDestroy() {
        if (lastDeleted != null) {
            addElement(lastDeleted.clone());
            lastDeleted = null;
        }
    }

    private void create(String text,String date) {
        long id = System.currentTimeMillis();
        Todo todo = new Todo(id, text, date);
        addElement(todo);
        Collections.sort(todos);

    }

//    private void destroy(long id) {
//        todos.addAll(dbHelper.getAllTodoFromDb());
//        Iterator<Todo> iter = todos.iterator();
//        while (iter.hasNext()) {
//            Todo todo = iter.next();
//            if (todo.getId() == id) {
//                dbHelper.deleteTodoFromDb(todo);
////                dbHelper.deleteAllTodosFromDb();
//                lastDeleted = todo.clone();
//                iter.remove();
//                break;
//            }
//        }
//    }

    private void destroy(String textName) {
        todos.addAll(dbHelper.getAllTodoFromDb());
        Iterator<Todo> iter = todos.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.getText().equals(textName)) {
                dbHelper.deleteTodoFromDb(todo);
//                dbHelper.deleteAllTodosFromDb();
                lastDeleted = todo.clone();
                iter.remove();
                break;
            }
        }
    }

    private void edit(long id, String updatedText, String updatedDate) {
        Todo todo = getById(id);
        if (todo != null) {
            if (todo.getId() == id){
                todos.remove(todo);
                dbHelper.deleteTodoFromDb(todo);
                long updatedId = System.currentTimeMillis();
                Todo updatedTodo = new Todo(updatedId, updatedText, updatedDate);
                todos.add(updatedTodo);
                dbHelper.addTodoToDb(updatedTodo);
                Collections.sort(todos);
            }

        }
    }

    private Todo getById(long id) {
        todos.addAll(dbHelper.getAllTodoFromDb());
        Iterator<Todo> iter = todos.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }


    private void addElement(Todo clone) {
        TodoStore todoStore =  TodoStore.get(dispatcher, dbHelper);

        dbHelper.addTodoToDb(clone);
        todos.add(clone);
        Collections.sort(todos);
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new TodoStoreChangeEvent();
    }

    public class TodoStoreChangeEvent implements StoreChangeEvent {
    }
}
