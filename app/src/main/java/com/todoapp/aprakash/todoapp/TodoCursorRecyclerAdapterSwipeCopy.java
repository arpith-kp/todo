package com.todoapp.aprakash.todoapp;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.CursorSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemAdapterMangerImpl;
import com.todoapp.aprakash.todoapp.model.Todo;

import java.util.List;

public class TodoCursorRecyclerAdapterSwipeCopy extends CursorSwipeAdapter {

    private Context mContext;
    private Todo item;
    private TextView todoText = null;
    private Button todoDelete;
    private Button todoEditSave;
    private TextView todoDate=null;
    private final LayoutInflater inflater;
    private onTextClickListener textListener;
    private onDeleteClickListener deleteListener;

    private SwipeLayout swipeLayout;
    private LinearLayout editbar=null ;
    private EditText todoEditDate;
    private EditText todoEditText;
    private List<Todo> todos;
    LoaderManager loaderManager;
    public SwipeLayout todo_swipe=null;
    private int position = 0;

    public TodoCursorRecyclerAdapterSwipeCopy(Context c, Cursor cursor, boolean autoRequery){
        super(c, cursor, autoRequery);
        this.mContext = c;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    class onDeleteClickListener implements  View.OnClickListener {
        @Override
        public void onClick(View v) {
            closeItem((Integer) v.getTag() - 1);
        }
    }

    class onTextClickListener implements  View.OnClickListener {
        @Override
        public void onClick(View v) {
            System.out.println(Looper.getMainLooper());
            todo_swipe = (SwipeLayout) inflater.inflate(R.layout.todo_row_swipe, null);
            todoText = (TextView) todo_swipe.findViewById(R.id.row_text);
            todoDate = (TextView) todo_swipe.findViewById(R.id.row_date);
            editbar = (LinearLayout) todo_swipe.findViewById(R.id.EditView);


            todoDate.setVisibility(View.GONE);

            todoText.setVisibility(View.GONE);
            editbar.setVisibility(View.GONE);
        }
    };

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        onBindVeiw(view, context, cursor);

    }
    @Override
    public void closeAllItems(){

    }

    public void setItems(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder{
        public TextView todoText;
        public Button todoDelete;
        public Button todoEditSave;
        public TextView todoDate;

        SwipeLayout swipeLayout;
        LinearLayout editbar ;
        public EditText todoEditDate;
        public EditText todoEditText;


        public SimpleViewHolder(View itemView) {
//            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            todoText = (TextView) itemView.findViewById(R.id.row_text);
            todoEditText = (EditText) itemView.findViewById(R.id.row_text_edit);
            todoEditDate = (EditText) itemView.findViewById(R.id.row_date_edit);
            todoDelete = (Button) itemView.findViewById(R.id.row_delete);
            todoDate = (TextView) itemView.findViewById(R.id.row_date);
            editbar = (LinearLayout)itemView.findViewById(R.id.EditView);
            todoEditSave = (Button) itemView.findViewById(R.id.row_save_editt);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "onItemSelected: " + todoText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        boolean convertViewIsNull = convertView == null;
//        final SimpleViewHolder holder;
//        View v = super.getView(position, convertView, parent);
//        if (convertViewIsNull){
//
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.todo_row_swipe, parent, false);
//            holder = new SimpleViewHolder(convertView);
//            convertView.setTag(holder);
//            holder.todoText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    holder.todoDate.setVisibility(view.GONE);
//                    holder.todoText.setVisibility(view.GONE);
//                    holder.editbar.setVisibility(view.VISIBLE);
//                    if (item.getDate() !=null){
//                        holder.todoEditDate.setText(item.getDate().toString());}
//                    if (item.getText() !=null){
//                        holder.todoEditText.setText(item.getText());}
//
//                }
//
//            });
//            holder.todoEditSave.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String newText = holder.todoEditText.getText().toString();
////                    actionsCreator.editText(item.getId(),newText);
//                    holder.todoText.setText(item.getText());
//                    holder.todoDate.setText(item.getDate().toString());
//                    holder.todoDate.setVisibility(view.VISIBLE);
//                    holder.todoText.setVisibility(view.VISIBLE);
//                    holder.editbar.setVisibility(view.GONE);
//                }
//            });
//
//            holder.todoDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mItemManger.removeShownLayouts(holder.swipeLayout);
////                    actionsCreator.destroy(item.getId());
//                    mItemManger.closeAllItems();
////                    notifyItemRemoved(position);
////                    notifyItemRangeChanged(position, todos.size());
//                }
//            });
//        }
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_row_swipe, parent, false);
    }


//   @Override
//   public void onClick(View view){
//       switch (view.getId()){
//           case R.id.row_text:
//               SimpleViewHolder viewHolder = (SimpleViewHolder) view.getTag();
//               viewHolder.todoDate.setVisibility(view.GONE);
//               viewHolder.todoText.setVisibility(view.GONE);
//               viewHolder.editbar.setVisibility(view.VISIBLE);
//               if (item.getDate() !=null){
//               viewHolder.todoEditDate.setText(item.getDate().toString());}
//               if (item.getText() !=null){
//                   viewHolder.todoEditText.setText(item.getText());}
//               return;
//           case R.id.row_save_editt:
//               SimpleViewHolder viewHolder_1 = (SimpleViewHolder) view.getTag();
//               String newText = viewHolder_1.todoEditText.getText().toString();
//               viewHolder_1.todoText.setText(item.getText());
//               viewHolder_1.todoDate.setText(item.getDate().toString());
//               viewHolder_1.todoDate.setVisibility(view.VISIBLE);
//               viewHolder_1.todoText.setVisibility(view.VISIBLE);
//               viewHolder_1.editbar.setVisibility(view.GONE);
//               //actionsCreator.editText(item.getId(),newText);
//               return;
//           case R.id.row_delete:
//               //actionsCreator.destroy(item.getId());
//               return;
//
//       }
//   }
    protected void onBindVeiw(View view, Context context, Cursor cursor){
        SimpleViewHolder viewHolder = (SimpleViewHolder) view.getTag();
        textListener = new onTextClickListener();
        deleteListener = new onDeleteClickListener();
        if (viewHolder == null){
            viewHolder = new SimpleViewHolder(view);

        }

        viewHolder.editbar.setVisibility(view.VISIBLE);
        viewHolder.todoText.setOnClickListener(textListener);
        viewHolder.todoDelete.setOnClickListener(deleteListener);
//        item = Todo.fromCursor(cursor);
//
//        if (item.getText() != null){
//            viewHolder.todoText.setText(item.getText());}
//        if (item.getDate() != null){
//            viewHolder.todoDate.setText(item.getDate().toString());}
//        viewHolder.todoText.addTextChangedListener(null);
//        viewHolder.todoText.addTextChangedListener(viewHolder.myCustomEditTextListener);







        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
