//package com.todoapp.aprakash.todoapp;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CursorAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.daimajia.swipe.SwipeLayout;
//import com.todoapp.aprakash.todoapp.stores.TodoStoreDBHelper;
//
///**
// * Created by aprakash on 8/31/16.
// */
//public class TodoCursorAdapterPlain extends CursorAdapter{
//
//    private final LayoutInflater inflater;
//
//
//    public TodoCursorAdapterPlain(Context context, Cursor c, int flags) {
//
//        super(context, c, flags);
//        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return inflater.inflate(R.layout.todo_row_swipe, parent, false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, final Cursor cursor) {
//        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe);
//        TextView todoText = (TextView) view.findViewById(R.id.row_text);
//        EditText todoEditText = (EditText) view.findViewById(R.id.row_text_edit);
//        EditText todoEditDate = (EditText) view.findViewById(R.id.row_date_edit);
//        Button todoDelete = (Button) view.findViewById(R.id.row_delete);
//        TextView todoDate = (TextView) view.findViewById(R.id.row_date);
//        LinearLayout editbar = (LinearLayout)view.findViewById(R.id.EditView);
//        Button todoEditSave = (Button) view.findViewById(R.id.row_save_editt);
//        try {
//
//            int t = cursor.getInt(cursor.getColumnIndex(TodoStoreDBHelper.KEY_TODO_TEXT));
//        } catch (Resources.NotFoundException ex)
//        {
//
//        }
//        todoText.setOnClickListener();
//        }
//    }
//}
//
//class onTextClickListener implements View.OnClickListener {
//    @Override
//    public void onClick(View view) {
//        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe);
//        final RelativeLayout poem
//                = (RelativeLayout) (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).inflate(R.layout.todo_row_swipe, null);
//
//
//    }
//}