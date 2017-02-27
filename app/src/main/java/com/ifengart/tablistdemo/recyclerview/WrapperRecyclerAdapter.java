package com.ifengart.tablistdemo.recyclerview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lvzishen on 16/10/8.
 */
public abstract class WrapperRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List list = new ArrayList<>();
    protected Context context;
//    /**
//     * 头布局
//     */
//    private View headView;
//    /**
//     * 头布局Tab
//     */
//    private View headViewTab;
    /**
     * 头布局
     * key: Integer; value: object
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    /**
     * 头布局Tab
     */
    private View headViewTab;
    /**
     * 脚布局
     */
    private FooterView footView;

//    /**
//     * 头布局数量
//     */
//    public int headViewCount = 0;
    /**
     * 脚布局数量
     */
    public int footViewCount = 0;

    public int getFootViewCount() {
        return footViewCount;
    }

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 3;


    private FootViewHolder footViewHolder;

    public void setList(List listData) {
        this.list.clear();
        this.list.addAll(listData);
        this.notifyDataSetChanged();
    }

    public WrapperRecyclerAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new HeadViewHolder(mHeaderViews.get(viewType));
        }
        else if (viewType == TYPE_FOOTER) {
            return footViewHolder;
        } else {
            return onCreateViewHolderNormal(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolderNormal(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        position = getRealPosition(position);
        onBindViewHolderNormal(holder, position);
    }

    public abstract void onBindViewHolderNormal(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return list.size() + mHeaderViews.size() + footViewCount;


    }

    public void addHeaderView(View head) {
        this.mHeaderViews.put(mHeaderViews.size() + TYPE_HEADER, head);
        notifyItemInserted(mHeaderViews.size() - 1);
    }


    public void addFooterView(FooterView footView) {
        this.footView = footView;
        footViewCount++;
        footViewHolder = new FootViewHolder(footView);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeFooterView() {
        if (footView != null) {
            footViewCount = 0;
            footViewHolder = null;
            footView = null;
            notifyItemRemoved(getItemCount() - 1);
        }

    }

    public void removeHeaderView() {
        if (mHeaderViews.size() > 0) {
//            headViewCount = 0;
            mHeaderViews.clear();
            for (int i = 0; i < mHeaderViews.size(); i++) {
                notifyItemRemoved(i);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mHeaderViews.size()) {
            return mHeaderViews.keyAt(position);
        }
        if (position == getItemCount() - 1 && footView != null) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }

        return getItemViewTypeNormal(getRealPosition(position));
    }

    public abstract int getItemViewTypeNormal(int position);

    /**
     * 得到真实的position
     *
     * @return
     */
    private int getRealPosition(int position) {
        return position = position - mHeaderViews.size();
    }


    public void setState(int status) {
        if (footViewHolder != null) {
            footView.setState(status);
        }
    }


    /**
     * @param errorListener 脚布局加载失败
     */
    public void setErrorListener(FooterView.ErrorListener errorListener) {
        footView.setOnErrorListener(errorListener);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanSize = 1; //每个griditem 占1份  一行有3个网格item 每个spansize为1  一个item占满则需要返回3
                    if (position == 0 && mHeaderViews != null && mHeaderViews.size() > 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (position == getItemCount() - 1 && footView != null && footViewCount > 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return spanSize;
                }
            });


        }


    }

    public int getState() {
        if (footView != null) {
            return footView.getStatus();
        }
        return 0;
    }


    class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }


    }

    class FootViewHolder extends RecyclerView.ViewHolder {


        public FootViewHolder(View itemView) {
            super(itemView);
        }

    }


}
