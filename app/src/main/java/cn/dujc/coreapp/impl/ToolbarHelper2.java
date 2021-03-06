package cn.dujc.coreapp.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import cn.dujc.core.initializer.toolbar.IToolbar;
import cn.dujc.core.ui.IBaseUI;
import cn.dujc.coreapp.R;
import cn.dujc.coreapp.ui.ListActivity;

public class ToolbarHelper2 implements IToolbar {

    @Override
    public View normal(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.toolbar_normal2, parent, false);
    }

    @Override
    public int statusBarColor(Context context) {
        return ContextCompat.getColor(context, R.color.colorAccent);
    }

    @Override
    public int statusBarMode() {
        return LIGHT;
    }

    @Override
    public int toolbarStyle() {
        return LINEAR;
    }

    @Override
    public List<Class<? extends IBaseUI>> include() {
        List<Class<? extends IBaseUI>> exc = new ArrayList<>();
        exc.add(ListActivity.class);
        return exc;
    }

    @Override
    public List<Class<? extends IBaseUI>> exclude() {
        return null;
    }

}
