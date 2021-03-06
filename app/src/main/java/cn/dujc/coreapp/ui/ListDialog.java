package cn.dujc.coreapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.dujc.core.adapter.BaseAdapter;
import cn.dujc.core.adapter.BaseQuickAdapter;
import cn.dujc.core.adapter.BaseViewHolder;
import cn.dujc.core.ui.impl.BaseListDialog;
import cn.dujc.core.util.StringUtil;

public class ListDialog extends BaseListDialog {

    private final List<String> mList = new ArrayList<>();

    public ListDialog(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public BaseQuickAdapter initAdapter() {
        return new BaseAdapter<String>(android.R.layout.simple_list_item_1, mList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        };
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        super.initBasic(savedInstanceState);
        mRootView.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void loadMore() {
        loadData(mList.size());
    }

    @Override
    public void reload() {
        loadData(0);
    }

    private void loadData(int start) {
        if (start == 0) mList.clear();
        final int size = mList.size();
        for (int index = 0; index < 20; index++) {
            mList.add(StringUtil.format("index %d", index + size));
        }
        notifyDataSetChanged(mList.size() >= 50);
    }
}
