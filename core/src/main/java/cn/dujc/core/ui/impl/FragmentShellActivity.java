package cn.dujc.core.ui.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.dujc.core.R;
import cn.dujc.core.app.Core;
import cn.dujc.core.initializer.toolbar.IToolbar;
import cn.dujc.core.ui.BaseActivity;

public class FragmentShellActivity extends BaseActivity {

    private static final String KEY_FRAGMENT_CLASS = "KEY_FRAGMENT_CLASS";
    public static final String EXTRA_FULL_SCREEN = "EXTRA_FULL_SCREEN", EXTRA_TOOLBAR_STYLE = "EXTRA_TOOLBAR_STYLE", EXTRA_STATUS_BAR_COLOR = "EXTRA_STATUS_BAR_COLOR", EXTRA_STATUS_DARK_MODE = "EXTRA_STATUS_DARK_MODE", EXTRA_TITLE = "EXTRA_TITLE";

    public static int start(IStarter starter, Class<? extends Fragment> fragment) {
        if (fragment != null) {
            starter.with(KEY_FRAGMENT_CLASS, fragment.getName());
        }
        return starter != null ? starter.go(FragmentShellActivity.class) : 0;
    }

    public static Intent load(Context context, Class<? extends Fragment> fragment) {
        final Intent intent = new Intent(context, FragmentShellActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_FRAGMENT_CLASS, fragment.getName());
        intent.putExtras(bundle);
        return intent;
    }

    private boolean mFullScreen = false;
    private Boolean mDarkMode = null;
    @IToolbar.Style
    private int mStyle = IToolbar.LINEAR;
    private Integer mStatusColor = null;
    private String mTitle;

    @Override
    public int getViewId() {
        return R.layout.core_fragment_shell_activity;
    }

    @Override
    protected boolean fullScreen() {
        return mFullScreen;
    }

    @IToolbar.Style
    @Override
    public int toolbarStyle() {
        return mStyle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mFullScreen = extras().get(EXTRA_FULL_SCREEN, mFullScreen);
        mDarkMode = extras().get(EXTRA_STATUS_DARK_MODE, mDarkMode);
        mStyle = extras().get(EXTRA_TOOLBAR_STYLE, mStyle);
        mStatusColor = extras().get(EXTRA_STATUS_BAR_COLOR, mStatusColor);
        mTitle = extras().get(EXTRA_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        if (mFullScreen) {
            getTitleCompat().setFakeStatusBarColor(Color.TRANSPARENT);
        } else if (mStatusColor != null) {
            getTitleCompat().setFakeStatusBarColor(mStatusColor);
        }
        if (mDarkMode != null) getTitleCompat().setStatusBarMode(mDarkMode);
        if (!TextUtils.isEmpty(mTitle)) setTitle(mTitle);
        try {
            final Fragment fragment = (Fragment) Class.forName(extras().get(KEY_FRAGMENT_CLASS, String.class))
                    .newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.core_fl_fragment_container, fragment)
                    .commit();
        } catch (InstantiationException e) {
            if (Core.DEBUG) e.printStackTrace();
        } catch (IllegalAccessException e) {
            if (Core.DEBUG) e.printStackTrace();
        } catch (ClassNotFoundException e) {
            if (Core.DEBUG) e.printStackTrace();
        } catch (ClassCastException e) {
            if (Core.DEBUG) e.printStackTrace();
        } catch (NullPointerException e) {
            if (Core.DEBUG) e.printStackTrace();
        }
    }

}
