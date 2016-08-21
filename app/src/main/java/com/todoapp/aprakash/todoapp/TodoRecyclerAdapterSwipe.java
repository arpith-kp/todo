package com.todoapp.aprakash.todoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.todoapp.aprakash.todoapp.actions.ActionsCreator;
import com.todoapp.aprakash.todoapp.model.Todo;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoRecyclerAdapterSwipe extends RecyclerSwipeAdapter<TodoRecyclerAdapterSwipe.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView todoText;
        public Button todoDelete;
        public TextView todoDate;
        SwipeLayout swipeLayout;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            todoText = (TextView) itemView.findViewById(R.id.row_text);
            todoDelete = (Button) itemView.findViewById(R.id.row_delete);
            todoDate = (TextView) itemView.findViewById(R.id.row_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "onItemSelected: " + todoText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static ActionsCreator actionsCreator;
    private List<Todo> todos;
    private Context mContext;

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public TodoRecyclerAdapterSwipe(Context context, ActionsCreator actionsCreator) {
        this.todos = new ArrayList<>();
        TodoRecyclerAdapterSwipe.actionsCreator = actionsCreator;
        this.mContext = context;
    }

    public void setItems(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_swipe, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Todo item = todos.get(position);
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
                DateFormat df = new SimpleDateFormat("MM/dd/yy", Locale.US);
                Date parsedDate = null;
                String x = item.getDate();
                try {
                    parsedDate = df.parse(x);
                }catch (ParseException p){
                    System.out.println(p);
                }
                actionsCreator.edit(item.getId(), item.getText(), x);
                Toast.makeText(mContext, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.todoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                actionsCreator.destroy(item.getId());
                mItemManger.closeAllItems();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, todos.size());
            }
        });

        viewHolder.todoText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if (!hasFocus) {

                    String newText = ((EditText) v).getText()
                            .toString();
                    item.setText(newText);
                }
        }
        }
        );
        viewHolder.todoText.setText(item.getText());
        viewHolder.todoDate.setText(item.getDate().toString());

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}