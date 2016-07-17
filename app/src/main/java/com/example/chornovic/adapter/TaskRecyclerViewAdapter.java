package com.example.chornovic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.chornovic.R;
import com.example.chornovic.model.Task;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Юрий on 07.07.2016.
 */
public class TaskRecyclerViewAdapter extends RecyclerSwipeAdapter<TaskRecyclerViewAdapter.ViewHolder> implements RealmChangeListener {
//    private List<Task> mTaskList;
   private Realm mRealm;
    private final RealmResults<Task> mTaskList;
    private static ClickListener clickListener;

    @Override
    public void onChange(Object element) {

    }

    public interface ClickListener {
        void onEditClick(int position, View v);
        void onTimeClickItem(int position, View v);
        void onBackgroundColorItem(int position, View v);
        void onResetTaskStart(int position, View v);
        void onResetTaskEnd(int position, View v);
        void onDeleteClickItem(int position, View v);
    }
    public void setOnEditClickListener(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }
    public void setOnBackgroundColorItem(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }
    public void setOnTimeClickItem(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }
    public void setOnResetTaskStart(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }
    public void setOnResetTaskEnd(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }public void setOnDeleteClickItem(ClickListener clickListener) {
        TaskRecyclerViewAdapter.clickListener = clickListener;
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    public TaskRecyclerViewAdapter(RealmResults<Task> tasks) {
        this.mTaskList = tasks;
//        mTaskList.addChangeListener(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        return new ViewHolder(v);
    }
    public void clearData() {
        // clear the data
        mTaskList.removeChangeListeners();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        clickListener.onBackgroundColorItem(position,holder.mSwipeLayout);

         holder.mName.setText(mTaskList.get(position).getName());
         holder.mComment.setText(mTaskList.get(position).getComment());
         holder.mTime.setText(mTaskList.get(position).getTime());
        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // Drag From Left
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.mSwipeLayout.findViewById(R.id.swipe_right));
        holder.mSwipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                clickListener.onTimeClickItem(position,layout);
            }
        });
        // Drag From Right
        holder.mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.mSwipeLayout.findViewById(R.id.swipe_left));
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(holder.mSwipeLayout);
//                Task mTask = mTaskList.get(position);
                clickListener.onDeleteClickItem(position,view);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mTaskList.size());
                mItemManger.closeAllItems();

            }
        });
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onEditClick(position, view);
            }
        });
        holder.mResetTaskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onResetTaskStart(position,v);
            }
        });
        holder.mResetTaskEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onResetTaskEnd(position,v);
            }
        });
        holder.mResetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.mResetEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mComment;
        TextView mTime;
        TextView mEdit;
        TextView mDelete;
        TextView mResetTaskStart;
        TextView mResetTaskEnd;
        SwipeLayout mSwipeLayout;
        Button mResetEnd;
        Button mResetStart;

        public ViewHolder(View itemView) {
            super(itemView);
            mSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mName = (TextView)itemView.findViewById(R.id.nameTextView);
            mComment =(TextView)itemView.findViewById(R.id.commentTextView);
            mTime = (TextView)itemView.findViewById(R.id.dateTAsk);
            mEdit = (TextView)itemView.findViewById(R.id.tvEdit);
            mDelete = (TextView)itemView.findViewById(R.id.tvDelete);
            mResetTaskStart = (TextView)itemView.findViewById(R.id.tvReset_Task_Start);
            mResetTaskEnd = (TextView)itemView.findViewById(R.id.tvReset_Task_End);
            mResetEnd = (Button) itemView.findViewById(R.id.reset_end);
            mResetStart = (Button) itemView.findViewById(R.id.reset_start);
        }
    }
}
