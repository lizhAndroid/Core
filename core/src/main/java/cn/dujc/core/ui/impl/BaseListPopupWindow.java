package cn.dujc.core.ui.impl;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import cn.dujc.core.R;
import cn.dujc.core.adapter.BaseQuickAdapter;
import cn.dujc.core.initializer.refresh.IRefreshListener;
import cn.dujc.core.ui.BasePopupWindow;

public abstract class BaseListPopupWindow extends BasePopupWindow implements IBaseList.UI {

    private static class ListImpl extends AbsImpl {
        private final BaseListPopupWindow mUi;
        View mRootView;

        public ListImpl(BaseListPopupWindow ui) {
            super(ui);
            mUi = ui;
        }

        @Override
        public View findViewById(int id) {
            return mUi.findViewById(id);
        }

        @Override
        public Context context() {
            return mUi.mContext;
        }

        @Override
        public View getRootView() {
            return mRootView;
        }
    }

    private final IBaseList mBaseList;

    public BaseListPopupWindow(Context context) {
        super(context);
        mBaseList = new ListImpl(this);
    }

    @Override
    public int getViewId() {
        return R.layout.core_base_list_layout;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        if (mBaseList instanceof ListImpl) ((ListImpl) mBaseList).mRootView = mRootView;
        mBaseList.initBasic(savedInstanceState);
    }

    @Override
    public void onDestroy_() {
        mBaseList.onDestroy_();
    }

    @Override
    public void coordinateRefreshAndAppbar() {
        mBaseList.coordinateRefreshAndAppbar();
    }

    @Override
    public void loadAtFirst() {
        mBaseList.loadAtFirst();
    }

    @Override
    public void loadFailure() {
        mBaseList.loadFailure();
    }

    @Override
    public void refreshDone() {
        mBaseList.refreshDone();
    }

    @Override
    public void loadDone(boolean dataDone, boolean endGone) {
        mBaseList.loadDone(dataDone, endGone);
    }

    @Override
    public void notifyDataSetChanged(boolean done) {
        mBaseList.notifyDataSetChanged(done);
    }

    @Override
    public void notifyDataSetChanged(boolean dataDone, boolean endGone) {
        mBaseList.notifyDataSetChanged(dataDone, endGone);
    }

    @Nullable
    @Override
    public <T> T getItem(int position) {
        return mBaseList.getItem(position);
    }

    @Override
    public void recyclerViewOtherSetup() {
        mBaseList.recyclerViewOtherSetup();
    }

    @Override
    public void recyclerViewSetupBeforeAdapter() {
        mBaseList.recyclerViewSetupBeforeAdapter();
    }

    @Override
    public void recyclerViewSetupBeforeLayoutManager() {
        mBaseList.recyclerViewSetupBeforeLayoutManager();
    }

    @Override
    public void doubleClickTitleToTop() {
        mBaseList.doubleClickTitleToTop();
    }

    @Nullable
    @Override
    public RecyclerView.LayoutManager initLayoutManager() {
        return mBaseList.initLayoutManager();
    }

    @Nullable
    @Override
    public BaseQuickAdapter getAdapter() {
        return mBaseList.getAdapter();
    }

    @Nullable
    @Override
    public RecyclerView getRecyclerView() {
        return mBaseList.getRecyclerView();
    }

    @Nullable
    @Override
    public <T extends View> T getSwipeRefreshLayout() {
        return mBaseList.getSwipeRefreshLayout();
    }

    @Override
    public void showRefreshing() {
        mBaseList.showRefreshing();
    }

    @Override
    public void refreshEnable(boolean enable) {
        mBaseList.refreshEnable(enable);
    }

    @Override
    public <T extends View> T initRefresh(View innerView) {
        return mBaseList.initRefresh(innerView);
    }

    @Override
    public void setOnRefreshListener(IRefreshListener onRefreshListener) {
        mBaseList.setOnRefreshListener(onRefreshListener);
    }

}
