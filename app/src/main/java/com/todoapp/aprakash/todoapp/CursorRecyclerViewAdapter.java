//package com.todoapp.aprakash.todoapp;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.support.v4.widget.CursorAdapter;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.daimajia.swipe.SwipeLayout;
//import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
//import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
//import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
//import com.daimajia.swipe.util.Attributes;
//
//import java.util.List;
//
///**
// * Created by aprakash on 8/24/16.
// */
//public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements SwipeItemMangerInterface, SwipeAdapterInterface {
//    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
//    private Context mContext;
//
//    private Cursor mCursor;
//
//    private boolean mAutoRequery;
//    private boolean mDataValid;
//    private int mRowIdColumn;
//
//    private int mFlags;
//    protected CursorRecyclerViewAdapter(Context context, Cursor c, boolean autoRequery) {
//        this.mContext = context;
//        this.mDataValid = c != null;
//        this.mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
//
//        this.mCursor = c;
//        this.mAutoRequery = autoRequery;
//    }
//
//    public Cursor getCursor() {
//        return mCursor;
//    }
//
//    public int getCount() {
//        if (mDataValid && mCursor != null) {
//            return mCursor.getCount();
//        } else {
//            return 0;
//        }
//    }
//
//    public Object getItem(int position) {
//        if (mDataValid && mCursor != null) {
//            mCursor.moveToPosition(position);
//            return mCursor;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void setHasStableIds(boolean hasStableIds) {
//        super.setHasStableIds(true);
//    }
//
//    public long getItemId(int position) {
//        if (mDataValid && mCursor != null) {
//            if (mCursor.moveToPosition(position)) {
//                return mCursor.getLong(mRowIdColumn);
//            } else {
//                return 0;
//            }
//        } else {
//            return 0;
//        }
//    }
//
//
//    protected CursorRecyclerViewAdapter(Context context, Cursor c, int flags) {
//        this.mContext = context;
//        this.mCursor = c;
//        this.mFlags = flags;
//    }
//    @Override
//    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);
//
//    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);
//
//    @Override
//    public void onBindViewHolder(VH viewHolder, final int position){
//        if (!mDataValid) {
//            throw new IllegalStateException("this should only be called when the cursor is valid");
//        }
//        if (!mCursor.moveToPosition(position)) {
//            throw new IllegalStateException("couldn't move cursor to position " + position);
//        }
//
//        if (mDataValid){
//            mItemManger.updateConvertView(viewHolder, position);
//        }
//        onBindViewHolder(viewHolder, mCursor);
//    };
//
//    @Override
//    public void openItem(int position) {
//        mItemManger.openItem(position);
//    }
//
//    @Override
//    public void closeItem(int position) {
//        mItemManger.closeItem(position);
//    }
//
//    @Override
//    public void closeAllExcept(SwipeLayout layout) {
//        mItemManger.closeAllExcept(layout);
//    }
//
//    @Override
//    public List<Integer> getOpenItems() {
//        return mItemManger.getOpenItems();
//    }
//
//    @Override
//    public List<SwipeLayout> getOpenLayouts() {
//        return mItemManger.getOpenLayouts();
//    }
//
//    @Override
//    public void removeShownLayouts(SwipeLayout layout) {
//        mItemManger.removeShownLayouts(layout);
//    }
//
//    @Override
//    public boolean isOpen(int position) {
//        return mItemManger.isOpen(position);
//    }
//
//    @Override
//    public Attributes.Mode getMode() {
//        return mItemManger.getMode();
//    }
//
//    @Override
//    public void setMode(Attributes.Mode mode) {
//        mItemManger.setMode(mode);
//    }
//}
