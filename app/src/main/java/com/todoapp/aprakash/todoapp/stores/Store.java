package com.todoapp.aprakash.todoapp.stores;

import com.todoapp.aprakash.todoapp.actions.Action;
import com.todoapp.aprakash.todoapp.dispatcher.Dispatcher;

/**
 * Created by aprakash on 8/7/16.
 */
public abstract class Store {

    final Dispatcher dispatcher;
    final TodoStoreDBHelper dbHelper;

    protected Store(Dispatcher dispatcher, TodoStoreDBHelper dbHelper) {
        this.dispatcher = dispatcher;
        this.dbHelper = dbHelper;
    }

    void emitStoreChange() {
        dispatcher.emitChange(changeEvent());
    }

    abstract StoreChangeEvent changeEvent();
    public abstract void onAction(Action action);

    public interface StoreChangeEvent {}
}
