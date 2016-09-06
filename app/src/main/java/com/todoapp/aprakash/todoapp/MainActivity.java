package com.todoapp.aprakash.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ImageButton;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

import com.daimajia.swipe.util.Attributes;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.todoapp.aprakash.todoapp.TodoRecyclerAdapter;

import com.todoapp.aprakash.todoapp.actions.ActionsCreator;
import com.todoapp.aprakash.todoapp.dispatcher.Dispatcher;
import com.todoapp.aprakash.todoapp.stores.TodoStore;
import com.todoapp.aprakash.todoapp.stores.TodoStoreDBHelper;
import com.todoapp.aprakash.todoapp.utils.DividerItemDecoration;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    private EditText mainInput;
    private ViewGroup mainLayout;
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private TodoStore todoStore;
    private TodoRecyclerAdapterSwipe listSwipeAdapter;
//    private TodoCursorRecyclerAdapterSwipeCopy listSwipeAdapter;
    private CheckBox mainCheck;
    public static final String DATE_PICKER = "DATE_PICKER";
    private Calendar dateChosen = Calendar.getInstance();
    private static Context mContext;

    private TodoStoreDBHelper dbHelper;

    public static Context getmContext(){
        return mContext;
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };
    private RecyclerView.Adapter mAdapter;

    // ----
    Calendar todaysDatePickerCaldendar = Calendar.getInstance();
    DatePickerDialog todaysDatePickerDialog = DatePickerDialog.newInstance(
            this,
            todaysDatePickerCaldendar.get(Calendar.YEAR),
            todaysDatePickerCaldendar.get(Calendar.MONTH),
            todaysDatePickerCaldendar.get(Calendar.DAY_OF_MONTH)
    );

    @BindView(R.id.todoDate)
    TextView mainDate;


    @OnClick(R.id.todoDate)
    public void clickSetDate() {
        todaysDatePickerDialog.show(getFragmentManager(), DATE_PICKER);
    }
    // ---
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); // --- set date here
        initDependencies();
        setupView();

    }
    // ---
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        dateChosen.set(Calendar.YEAR, year);
        dateChosen.set(Calendar.MONTH, monthOfYear);
        dateChosen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        setTextViewUnderlined(mainDate, df.format(dateChosen.getTime()));
    }
    // ---

    private void initDependencies() {
        dispatcher = Dispatcher.get(new Bus());
        actionsCreator = ActionsCreator.get(dispatcher);
        mContext = getApplicationContext();
        dbHelper = TodoStoreDBHelper.getInstance(mContext);
        todoStore = TodoStore.get(dispatcher, dbHelper);
    }

    private void setupView() {
        mainLayout = ((ViewGroup) findViewById(R.id.main_layout));
        mainInput = (EditText) findViewById(R.id.main_input);

        ImageButton mainAdd = (ImageButton) findViewById(R.id.main_add);
        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
                resetMainInput();
            }
        });
//        mainCheck = (CheckBox) findViewById(R.id.main_checkbox);
//        mainCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkAll();
//            }
//        });
        Button mainClearCompleted = (Button) findViewById(R.id.main_clear_completed);
        mainClearCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCompleted();
            }
        });


        RecyclerView mainList = (RecyclerView) findViewById(R.id.main_list);
        mainList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.setItemAnimator(new FadeInLeftAnimator());

        listSwipeAdapter = new TodoRecyclerAdapterSwipe(this, actionsCreator);
        ((TodoRecyclerAdapterSwipe) listSwipeAdapter).setMode(Attributes.Mode.Single);
        mainList.setAdapter(listSwipeAdapter);

        mainList.setOnScrollListener(onScrollListener);
        updateUI();

    }

    private void updateUI() {

        listSwipeAdapter.setItems(dbHelper.getAllTodoFromDb());
        if (todoStore.canUndo()) {
            Snackbar snackbar = Snackbar.make(mainLayout, "Element deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionsCreator.undoDestroy();
                }
            });
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dispatcher.register(this);
        dispatcher.register(todoStore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dispatcher.unregister(this);
        dispatcher.unregister(todoStore);
    }

    private void addTodo() {

        if (validateInput()) {
            actionsCreator.create(getInputText(), getInputDate());

//            Intent addItem = new Intent(this, AddMember.class);
//            startActivity(addItem);
//            actionsCreator.create(getInputText(), dateChosen);
        }
    }

    private void checkAll() {
        actionsCreator.toggleCompleteAll();
    }

    private void clearCompleted() {
        actionsCreator.destroyCompleted();
    }

    private void resetMainInput() {
        mainInput.setText("");
        mainDate.setText("Add Date");

    }

    private boolean validateInput() {
        return !TextUtils.isEmpty(getInputText());
    }

    private String getInputText() {
        return mainInput.getText().toString();
    }

    private String getInputDate(){
        StringBuilder dateStored = new StringBuilder();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        if (mainDate.getText() == null){
            dateStored.append(df.format(dateChosen.getTime()));
        }
        else{
            dateStored.append(df.format(dateChosen.getTime()));
        }
        return dateStored.toString();
    }

    private void setTextViewUnderlined(TextView textView, String string) {
        SpannableString content = new SpannableString(string);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    @Subscribe
    public void onTodoStoreChange(TodoStore.TodoStoreChangeEvent event) {
        updateUI();
    }

}




