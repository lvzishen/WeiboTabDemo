package com.ifengart.tablistdemo.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by lvzishen on 16/10/9.
 */
public abstract class RecyclerViewLoadData extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager layoutManager;
    private int lastVisibleItem;
    private int firstVisbleItem;
    private int totalItemCount;
    private int visibleItemCount;

    public RecyclerViewLoadData(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 获取到最后一个可见的item
        if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            //得到当前显示的第一个item的位置
            firstVisbleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            //如果第一个可见的item为大于等于1则显示悬浮tab
            if (firstVisbleItem >= 1) {
                showTab();
            } else {
                hideTab();
            }
            getFirstVisiblePosition(firstVisbleItem);
        }
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        // 获取item的总数
        totalItemCount = layoutManager.getItemCount();
        //可见的item数
        visibleItemCount = layoutManager.getChildCount();
//        Log.e("totalItemCount", totalItemCount + "");
//        Log.e("visibleItemCount", visibleItemCount + "");
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem >= totalItemCount - 1 && visibleItemCount > 0) {
            // 此处调用加载更多回调接口的回调方法
            int state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if (state == FooterView.Loading || state == FooterView.TheEnd) {
                Log.d("state not allow", "the state is Loading/The End, just wait..");
                return;
            }
            loadMoreData(recyclerView);
        }
    }


    public abstract void loadMoreData(RecyclerView recyclerView);

    public abstract void getFirstVisiblePosition(int firstVisiblePosition);

    public abstract void showTab();

    public abstract void hideTab();

}
