package com.todoapp.aprakash.todoapp.stores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.todoapp.aprakash.todoapp.MainActivity;
import com.todoapp.aprakash.todoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoStoreDBHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todosDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TODOS = "todos";
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_TEXT = "text";
    private static final String KEY_TODO_DATE = "date";

    private static TodoStoreDBHelper sInstance;

    public static synchronized TodoStoreDBHelper getInstance(Context context) {

        if (sInstance == null) {
//            sInstance = new TodoStoreDBHelper(context.getApplicationContext());
            sInstance = new TodoStoreDBHelper(MainActivity.getmContext());
        }
        return sInstance;
    }

    private TodoStoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOS_TABLE = "CREATE TABLE " + TABLE_TODOS +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," +
                KEY_TODO_TEXT + " TEXT,"
                + KEY_TODO_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_TODOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(db);
        }
    }

    public void addTodoToDb(Todo item) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_DATE, item.getDate());
            values.put(KEY_TODO_TEXT, item.getText());
            db.insertOrThrow(TABLE_TODOS, null, values);
            db.setTransactionSuccessful();


        }catch (Exception e) {
            System.out.println(e);
            //do nothing
        }finally {
            db.endTransaction();
        }
    }

    public List<Todo> getAllTodoFromDb() {
        List<Todo> todos = new ArrayList<>();


        String TODOS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODOS
                        );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODOS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Todo newItem = new Todo();
                    newItem.setText(cursor.getString(cursor.getColumnIndex(KEY_TODO_TEXT)));
                    newItem.setDate(cursor.getString(cursor.getColumnIndex(KEY_TODO_DATE)));

                    todos.add(newItem);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
            //do nothing
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return todos;


    }


    public void deleteAllTodosFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODOS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }
}