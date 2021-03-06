package cn.dujc.core.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.dujc.core.R;
import cn.dujc.core.app.Core;
import cn.dujc.core.initializer.toolbar.IToolbar;
import cn.dujc.core.permission.AppSettingsDialog;
import cn.dujc.core.permission.IOddsPermissionOperator;
import cn.dujc.core.ui.impl.FragmentShellActivity;
import cn.dujc.core.util.LogUtil;
import cn.dujc.core.util.SerializableTransfer;

/**
 * 基本的UI类方法，包括activity和fragment
 * Created by du on 2018/2/14.
 */
public interface IBaseUI {

    View getViewV();

    int getViewId();

    void initBasic(Bundle savedInstanceState);

    void rootViewSetup(View rootView);

    View getRootView();

    public static interface WithToolbar extends IBaseUI {
        /**
         * 自增的request code，每个跳转都是forresult的跳转，那么今后只要记住跳转方法{@link #starter().go(Class)}返回的int值
         * ，即为本次跳转产生的request code，从此不再管理request code，且不会再重复，因为不管在什么界面跳转，每次跳转都用了不同的request code（当然，崩溃重启的情况例外）
         */
        int[] _INCREMENT_REQUEST_CODE = {1};

        IStarter starter();

        /**
         * 获取传参的工具类，自1.1.1-alpha1后，fragment和activity传参方式不同
         * ，但fragment能获取到fragment.getArguments()以及fragment.getActivity().getIntent().getExtras()的并集
         */
        IParams extras();

        IPermissionKeeper permissionKeeper();

        void permissionKeeperSetup();

        View initToolbar(ViewGroup parent);

        TitleCompat getTitleCompat();

        View createRootView(View contentView);

        @IToolbar.Style
        int toolbarStyle();
    }

    public static interface IContextCompat {
        void startActivityForResult(Intent intent, int requestCode);

        Context context();

        void finish();

        int checkSelfPermission(String permission);

        boolean shouldShowRequestPermissionRationale(String permission);

        void requestPermissions(String[] permissions, int requestCode);

        Bundle extras();
    }

    public static interface IStarter {

        int getRequestCode(Class<?> activityForward);

        int newRequestCode(Class<?> activity);

        int go(Class<? extends Activity> activity);

        int go(Class<? extends Activity> activity, boolean finishThen);

        /**
         * 跳转到一个fragment，会自动给它套一个{@link FragmentShellActivity}然后跳转
         */
        int goFragment(Class<? extends Fragment> fragment);

        int go(Intent intent);

        int go(Intent intent, boolean finishThen);

        /**
         * 讲参数保存给Fragment
         */
        void toFragmentArgs(Fragment fragment);

        IStarter clear();

        IStarter putAll(Bundle bundle);

        IStarter withSelfExtra();

        IStarter with(String key, String param);

        IStarter with(String key, byte param);

        IStarter with(String key, char param);

        IStarter with(String key, short param);

        IStarter with(String key, int param);

        IStarter with(String key, float param);

        IStarter with(String key, double param);

        IStarter with(String key, long param);

        IStarter with(String key, boolean param);

        IStarter with(String key, CharSequence param);

        IStarter with(String key, Parcelable param);

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        IStarter with(String key, Size param);

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        IStarter with(String key, SizeF param);

        IStarter with(String key, Parcelable[] param);

        IStarter withParcelableArrayList(String key, ArrayList<? extends Parcelable> param);

        IStarter with(String key, SparseArray<? extends Parcelable> param);

        IStarter withIntegerArrayList(String key, ArrayList<Integer> param);

        IStarter withStringArrayList(String key, ArrayList<String> param);

        IStarter withCharSequenceArrayList(String key, ArrayList<CharSequence> param);

        IStarter with(String key, Serializable param);

        IStarter with(String key, byte[] param);

        IStarter with(String key, short[] param);

        IStarter with(String key, char[] param);

        IStarter with(String key, int[] param);

        IStarter with(String key, float[] param);

        IStarter with(String key, double[] param);

        IStarter with(String key, long[] param);

        IStarter with(String key, boolean[] param);

        IStarter with(String key, CharSequence[] param);

        IStarter with(String key, Bundle param);

        /**
         * 这个方案，会将大数据以key为文件名，直接保存到cache文件夹，所以不同的大数据，key应该不同，否则会被覆盖。
         * 同时这个key，只有再次被使用到才会重新覆盖，否则一次保存，处处使用
         */
        IStarter withLargeData(String key, Serializable param);
    }

    public static interface IParams {
        public <T> T get(String key, T defaultValues, Class<T> clazz);

        public <T> T get(String key, T defaultValues);

        /**
         * 如果需要获取基本类型，最好需要给默认值{@link #get(String, Object, Class)}
         */
        public <T> T get(String key, Class<T> clazz);

        /**
         * 如果需要获取基本类型，最好需要给默认值{@link #get(String, Object)}
         */
        public <T> T get(String key);

        public <T> T getLargeData(String key, T defaultValues);
    }

    public static interface IPermissionKeeper {

        void requestPermissionsNormal(int requestCode, String... permission);

        void requestPermissions(int requestCode, @StringRes int title, @StringRes int message, String... permission);

        void requestPermissions(int requestCode, String title, String message, String... permission);

        boolean hasPermission(String... permissions);

        void handOnActivityResult(int requestCode);

        void handOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void setSettingsDialog(IPermissionSettingsDialog settingsDialog);

        void setOddsPermissionOperator(IOddsPermissionOperator permissionOperator);

        IOddsPermissionOperator getOddsPermissionOperator();
    }

    public static interface IPermissionSettingsDialog {
        void showSettingsDialog(IContextCompat context, String title, String message);
    }

    public static interface IPermissionKeeperCallback {
        void onGranted(int requestCode, List<String> permissions);

        void onDenied(int requestCode, List<String> permissions);
    }

    public static class IContextCompatActivityImpl implements IContextCompat {
        private final Activity mActivity;

        public IContextCompatActivityImpl(Activity activity) {
            mActivity = activity;
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            mActivity.startActivityForResult(intent, requestCode);
        }

        @Override
        public Context context() {
            return mActivity;
        }

        @Override
        public void finish() {
            mActivity.finish();
        }

        @Override
        public int checkSelfPermission(String permission) {
            return ActivityCompat.checkSelfPermission(context(), permission);
        }

        @Override
        public boolean shouldShowRequestPermissionRationale(String permission) {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
        }

        @Override
        public void requestPermissions(String[] permissions, int requestCode) {
            ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
        }

        @Override
        public Bundle extras() {
            Intent intent = mActivity.getIntent();
            return intent == null ? null : intent.getExtras();
        }
    }

    public static class IContextCompatFragmentImpl implements IContextCompat {
        private final Fragment mFragment;

        public IContextCompatFragmentImpl(Fragment fragment) {
            mFragment = fragment;
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            mFragment.startActivityForResult(intent, requestCode);
        }

        @Override
        public Context context() {
            return mFragment.getContext();
        }

        @Override
        public void finish() {
            final FragmentActivity activity = mFragment.getActivity();
            if (activity != null && !activity.isFinishing()) activity.finish();
        }

        @Override
        public int checkSelfPermission(String permission) {
            return ActivityCompat.checkSelfPermission(context(), permission);
        }

        @Override
        public boolean shouldShowRequestPermissionRationale(String permission) {
            return mFragment.shouldShowRequestPermissionRationale(permission);
        }

        @Override
        public void requestPermissions(String[] permissions, int requestCode) {
            mFragment.requestPermissions(permissions, requestCode);
        }

        @Override
        public Bundle extras() {
            return mFragment.getArguments();
        }
    }

    public static class IStarterImpl implements IStarter {

        private final Bundle mBundle = new Bundle();
        private final Map<Class<?>, Integer> mRequestCodes = new ArrayMap<>();
        private final IContextCompat mContext;

        public IStarterImpl(Activity activity) {
            this(new IContextCompatActivityImpl(activity));
        }

        public IStarterImpl(Fragment fragment) {
            this(new IContextCompatFragmentImpl(fragment));
        }

        public IStarterImpl(IContextCompat context) {
            mContext = context;
        }

        //跳转
        private static int go(IContextCompat context, Intent intent, int requestCode, boolean finishThen) {
            context.startActivityForResult(intent, requestCode);
            if (finishThen) {
                context.finish();
            }
            return requestCode;
        }

        @Override
        public int getRequestCode(Class<?> activityForward) {
            final Integer integer = mRequestCodes.get(activityForward);
            return integer == null ? -1 : integer;
        }

        @Override
        public int newRequestCode(Class<?> activity) {
            int requestCode = WithToolbar._INCREMENT_REQUEST_CODE[0]++;
            if (requestCode >= 0xffff) {
                requestCode = WithToolbar._INCREMENT_REQUEST_CODE[0] = 1;
            }
            LogUtil.d("------------ request code = " + requestCode);
            mRequestCodes.put(activity, requestCode);
            return requestCode;
        }

        @Override
        public int go(Class<? extends Activity> activity) {
            return go(activity, false);
        }

        @Override
        public int go(Class<? extends Activity> activity, boolean finishThen) {
            Intent intent = new Intent(mContext.context(), activity);
            if (mBundle.size() > 0) {
                intent.putExtras(mBundle);
            }
            int requestCode = newRequestCode(activity);
            return IStarterImpl.go(mContext, intent, requestCode, finishThen);
        }

        @Override
        public int goFragment(Class<? extends Fragment> fragment) {
            return FragmentShellActivity.start(this, fragment);
        }

        @Override
        public int go(Intent intent) {
            return go(intent, false);
        }

        @Override
        public int go(Intent intent, boolean finishThen) {
            final ComponentName component = intent.getComponent();
            int requestCode = 0;
            if (component != null) {
                final Class<?> activity;
                try {
                    activity = Class.forName(component.getClassName());
                    requestCode = newRequestCode(activity);
                } catch (ClassNotFoundException e) {
                    if (Core.DEBUG) e.printStackTrace();
                } catch (ClassCastException e) {
                    if (Core.DEBUG) e.printStackTrace();
                }
            }
            return IStarterImpl.go(mContext, intent, requestCode, finishThen);
        }

        @Override
        public void toFragmentArgs(Fragment fragment) {
            if (fragment != null) {
                fragment.setArguments(mBundle);
            }
        }

        @Override
        public IStarter clear() {
            mBundle.clear();
            return this;
        }

        @Override
        public IStarter putAll(Bundle bundle) {
            mBundle.putAll(bundle);
            return this;
        }

        @Override
        public IStarter withSelfExtra() {
            mBundle.putAll(mContext.extras());
            return this;
        }

        @Override
        public IStarter with(String key, String param) {
            mBundle.putString(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, byte param) {
            mBundle.putByte(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, char param) {
            mBundle.putChar(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, short param) {
            mBundle.putShort(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, int param) {
            mBundle.putInt(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, float param) {
            mBundle.putFloat(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, double param) {
            mBundle.putDouble(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, long param) {
            mBundle.putLong(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, boolean param) {
            mBundle.putBoolean(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, CharSequence param) {
            mBundle.putCharSequence(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, Parcelable param) {
            mBundle.putParcelable(key, param);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public IStarter with(String key, Size param) {
            mBundle.putSize(key, param);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public IStarter with(String key, SizeF param) {
            mBundle.putSizeF(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, Parcelable[] param) {
            mBundle.putParcelableArray(key, param);
            return this;
        }

        @Override
        public IStarter withParcelableArrayList(String key, ArrayList<? extends Parcelable> param) {
            mBundle.putParcelableArrayList(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, SparseArray<? extends Parcelable> param) {
            mBundle.putSparseParcelableArray(key, param);
            return this;
        }

        @Override
        public IStarter withIntegerArrayList(String key, ArrayList<Integer> param) {
            mBundle.putIntegerArrayList(key, param);
            return this;
        }

        @Override
        public IStarter withStringArrayList(String key, ArrayList<String> param) {
            mBundle.putStringArrayList(key, param);
            return this;
        }

        @Override
        public IStarter withCharSequenceArrayList(String key, ArrayList<CharSequence> param) {
            mBundle.putCharSequenceArrayList(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, Serializable param) {
            mBundle.putSerializable(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, byte[] param) {
            mBundle.putByteArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, short[] param) {
            mBundle.putShortArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, char[] param) {
            mBundle.putCharArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, int[] param) {
            mBundle.putIntArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, float[] param) {
            mBundle.putFloatArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, double[] param) {
            mBundle.putDoubleArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, long[] param) {
            mBundle.putLongArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, boolean[] param) {
            mBundle.putBooleanArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, CharSequence[] param) {
            mBundle.putCharSequenceArray(key, param);
            return this;
        }

        @Override
        public IStarter with(String key, Bundle param) {
            mBundle.putBundle(key, param);
            return this;
        }

        @Override
        public IStarter withLargeData(String key, Serializable param) {
            File data = new File(mContext.context().getCacheDir(), key);
            if (!data.getParentFile().exists()) data.getParentFile().mkdirs();
            else if (data.exists()) data.delete();
            new SerializableTransfer(data).save(param);
            return this;
        }

    }

    public static class BaseParamsImpl implements IParams {

        final Bundle mBundle;
        final IContextCompat mContext;

        BaseParamsImpl(IContextCompat context, Bundle bundle) {
            mContext = context;
            mBundle = bundle == null ? new Bundle() : bundle;
        }

        @Override
        @Nullable
        public <T> T get(String key, T defaultValues, Class<T> clazz) {
            if (mBundle != null && key != null) {
                final Object obj = mBundle.get(key);
                if (clazz != null && clazz.isInstance(obj)) {
                    return clazz.cast(obj);
                } else if (obj != null) {
                    try {
                        return (T) obj;
                    } catch (ClassCastException e) {
                        if (Core.DEBUG) e.printStackTrace();
                    }
                }
            }
            return defaultValues;
        }

        @Override
        public <T> T get(String key, T defaultValues) {
            return get(key, defaultValues, null);
        }

        @Override
        @Nullable
        public <T> T get(String key, Class<T> clazz) {
            return get(key, null, clazz);
        }

        @Override
        @Nullable
        public <T> T get(String key) {
            return get(key, null, null);
        }

        @Override
        public <T> T getLargeData(String key, T defaultValues) {
            T result = defaultValues;
            File data = new File(mContext.context().getCacheDir(), key);
            if (data.exists()) {
                try {
                    result = (T) new SerializableTransfer(data).read();
                } catch (Exception e) {
                    if (Core.DEBUG) e.printStackTrace();
                }
            }
            return result;
        }
    }

    public static class ActivityParamsImpl extends BaseParamsImpl {
        ActivityParamsImpl(Activity activity) {
            super(new IContextCompatActivityImpl(activity)
                    , activity != null && activity.getIntent() != null ? activity.getIntent().getExtras() : null);
        }
    }

    public static class FragmentParamsImpl extends BaseParamsImpl {
        FragmentParamsImpl(Fragment fragment) {
            super(new IContextCompatFragmentImpl(fragment)
                    , fragment != null && fragment.getActivity() != null && fragment.getActivity().getIntent() != null ? fragment.getActivity().getIntent().getExtras() : null);
            if (fragment != null && fragment.getArguments() != null)
                mBundle.putAll(fragment.getArguments());
        }
    }

    public static class IPermissionKeeperImpl implements IPermissionKeeper {

        private final IContextCompat mContext;
        private final IPermissionKeeperCallback mCallback;
        private IOddsPermissionOperator mPermissionOperator;
        private IPermissionSettingsDialog mSettingsDialog;
        private String[] mLastRequestedPermissions = null;
        private int mLastRequestCode = -1;

        public IPermissionKeeperImpl(Activity activity, IPermissionKeeperCallback callback) {
            this(new IContextCompatActivityImpl(activity), callback);
        }

        public IPermissionKeeperImpl(Fragment fragment, IPermissionKeeperCallback callback) {
            this(new IContextCompatFragmentImpl(fragment), callback);
        }

        public IPermissionKeeperImpl(IContextCompat context, IPermissionKeeperCallback callback) {
            mContext = context;
            mCallback = callback;
            mSettingsDialog = new IPermissionSettingsDialogImpl();
        }

        @Override
        public void requestPermissionsNormal(int requestCode, String... permission) {
            requestPermissions(requestCode
                    , mContext.context().getString(R.string.core_title_settings_dialog)
                    , mContext.context().getString(R.string.core_rationale_ask_again)
                    , permission);
        }

        @Override
        public void requestPermissions(int requestCode, @StringRes int title, @StringRes int message, String... permission) {
            final String titleStr = mContext.context().getString(title != 0 ? title : R.string.core_title_settings_dialog);
            final String messageStr = mContext.context().getString(message != 0 ? message : R.string.core_rationale_ask_again);
            requestPermissions(requestCode, titleStr, messageStr, permission);
        }

        @Override
        public void requestPermissions(int requestCode, String title, String message, String... permissions) {
            mLastRequestedPermissions = null;
            mLastRequestCode = -1;
            if (permissions == null) return;
            mLastRequestedPermissions = permissions;
            mLastRequestCode = requestCode;
            // 以下为自定义的权限处理逻辑
            if (mPermissionOperator != null
                    && mPermissionOperator.useOddsPermissionOperate(mContext.context())
            ) {//使用自定义权限操作
                final boolean hasPermission = mPermissionOperator.requestPermissions(requestCode, title, message, permissions);
                if (mPermissionOperator.doneHere(mContext.context(), permissions)) {
                    if (mCallback != null) {
                        if (hasPermission) {
                            mCallback.onGranted(requestCode, Arrays.asList(permissions));
                        } else {
                            mCallback.onDenied(requestCode, Arrays.asList(permissions));
                        }
                    }
                    return;//是否需要就此结束
                }
            }
            //以上结束自定义的权限逻辑，以下是正确、正常的权限处理逻辑
            if (hasPermission(permissions)) {
                if (mCallback != null) mCallback.onGranted(requestCode, Arrays.asList(permissions));
            } else {
                boolean showHint = false;
                for (String permission : permissions) {
                    showHint = showHint || mContext.shouldShowRequestPermissionRationale(permission);
                }
                if (mSettingsDialog != null && showHint && (mPermissionOperator == null //系统api需要显示对话框，并且没有设置特异权限的操作
                        || !mPermissionOperator.useOddsPermissionOperate(mContext.context()) //设置了特异权限操作，但是不符合特异权限使用条件
                        || mPermissionOperator.showConfirmDialog(permissions))) {//特异权限允许使用对话框
                    mSettingsDialog.showSettingsDialog(mContext, title, message);
                } else {
                    mContext.requestPermissions(permissions, requestCode);
                }
            }
        }

        @Override
        public boolean hasPermission(String... permissions) {
            return IPermissionKeeperImpl.hasPermission(mContext.context(), permissions);
        }

        @Override
        public void handOnActivityResult(int requestCode) {
            if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE
                    && mLastRequestedPermissions != null) {
                handleGrantedOrDenied(mContext.context(), mCallback, mLastRequestedPermissions, null, mLastRequestCode);
            }
        }

        @Override
        public void handOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            handleGrantedOrDenied(mContext.context(), mCallback, permissions, grantResults, requestCode);
        }

        @Override
        public void setSettingsDialog(IPermissionSettingsDialog settingsDialog) {
            mSettingsDialog = settingsDialog;
        }

        @Override
        public void setOddsPermissionOperator(IOddsPermissionOperator permissionOperator) {
            mPermissionOperator = permissionOperator;
        }

        @Override
        public IOddsPermissionOperator getOddsPermissionOperator() {
            return mPermissionOperator;
        }

        private static boolean hasPermission(Context context, String... permissions) {
            boolean has = permissions != null && permissions.length > 0;
            if (has) {
                for (String permission : permissions) {
                    has = has && ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
                    //has = has && PermissionChecker.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
                }
            }
            return has;
        }

        private static void handleGrantedOrDenied(Context context, IPermissionKeeperCallback callback, String[] permissions, int[] grantResults, int requestCode) {
            if (context == null || callback == null || permissions == null) return;

            final List<String> granted = new ArrayList<>();
            final List<String> denied = new ArrayList<>();

            if (grantResults == null) {
                for (String perm : permissions) {
                    if (hasPermission(context, perm)) {
                        granted.add(perm);
                    } else {
                        denied.add(perm);
                    }
                }
            } else {
                for (int index = 0, length = permissions.length; index < length; index++) {
                    String perm = permissions[index];
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        granted.add(perm);
                    } else {
                        denied.add(perm);
                    }
                }
            }

            /*if (!granted.isEmpty()) {
                callback.onGranted(requestCode, granted);
            }

            if (!denied.isEmpty()) {
                callback.onDenied(requestCode, denied);
            }*/
            if (denied.isEmpty() && !granted.isEmpty()) {
                callback.onGranted(requestCode, granted);
            } else {
                callback.onDenied(requestCode, denied);
            }
        }

    }

    public static class IPermissionSettingsDialogImpl implements IPermissionSettingsDialog {

        @Override
        public void showSettingsDialog(IContextCompat context, String title, String message) {
            final AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(context);
            if (!TextUtils.isEmpty(title)) builder.setTitle(title);
            if (!TextUtils.isEmpty(message)) builder.setRationale(message);
            builder.build().show();
        }
    }
}
