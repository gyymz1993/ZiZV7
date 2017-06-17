package com.easeui.home.ui;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.easeui.home.adapter.HomeAadapter;
import com.hyphenate.Constant;
import com.hyphenate.DemoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.db.InviteMessgeDao;
import com.hyphenate.db.UserDao;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.runtimepermissions.PermissionsManager;
import com.hyphenate.runtimepermissions.PermissionsResultAction;
import com.hyphenate.ui.ChatActivity;
import com.hyphenate.ui.ContactListFragment;
import com.hyphenate.ui.ConversationListFragment;
import com.hyphenate.ui.GroupsActivity;
import com.hyphenate.ui.LoginActivity;
import com.hyphenate.ui.MainActivity;
import com.hyphenate.ui.SettingsFragment;
import com.hyphenate.util.EMLog;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.yangshao.helper.SystemBarHelper;
import com.yangshao.utils.L_;
import com.yangshao.utils.UIUtils;
import com.ys.lib.base.BaseMvpActivity;
import com.ys.lib.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import april.yun.ISlidingTabStrip;
import april.yun.JPagerSlidingTabStrip2;
import april.yun.other.JTabStyleDelegate;
import butterknife.BindView;

import static april.yun.other.JTabStyleBuilder.STYLE_DEFAULT;

/**
 * 创建人：gyymz1993
 * 创建时间：2017/5/29/3:12
 **/
public class HomeActivity extends BaseMvpActivity {

    @BindView(R.id.tab_buttom)
    JPagerSlidingTabStrip2 tabBtn;

    @BindView(R.id.pager)
    ViewPager mViewPager;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private InviteMessgeDao inviteMessgeDao;
    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    public void initHuanxin(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            DemoHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        requestPermissions();
        showExceptionDialogFromIntent(getIntent());
        inviteMessgeDao = new InviteMessgeDao(this);
        UserDao userDao = new UserDao(this);
        //contactListFragment = new ContactListFragment();
        //register broadcast receiver to receive the change of group from DemoHelper
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        //debug purpose only
        registerInternalDebugReceiver();

    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    RedPacketUtil.receiveRedPacketAckMessage(message);
                }
            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count

            }
        });
    }


    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!HomeActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(HomeActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e(TAG, "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    /**
     * debug purpose only, you can ignore this
     */
    private void registerInternalDebugReceiver() {
        internalDebugReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                DemoHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                finish();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(int code, String message) {
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter(getPackageName() + ".em_internal_debug");
        registerReceiver(internalDebugReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }


    @Override
    protected void initView() {
        super.initView();
        setupTabStrips();
        setupViewpager();
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        SystemBarHelper.tintStatusBar(this, UIUtils.getColor(R.color.colorBlack));
    }


    private void setupTabStrips() {
        setupStrip(tabBtn.getTabStyleDelegate(), STYLE_DEFAULT);
        tabBtn.getTabStyleDelegate()
                .setIndicatorHeight(0)
                .setDividerColor(Color.TRANSPARENT);
        tabBtn.getTabStyleDelegate()
                .setFrameColor(Color.TRANSPARENT)
                .setIndicatorColor(Color.TRANSPARENT)
                .setTabIconGravity(Gravity.TOP)//图标显示在top
                .setIndicatorHeight(-8)//设置的高小于0 会显示在tab顶部 否则底部
                .setDividerColor(Color.TRANSPARENT);
    }

    private void setupStrip(JTabStyleDelegate tabStyleDelegate, int type) {
        tabStyleDelegate.setJTabStyle(type)
                .setShouldExpand(true)
                .setFrameColor(Color.parseColor("#45C01A"))
                .setTabTextSize(/*UIUtils.getDimen(13)*/26)
                .setTextColor(Color.parseColor("#45C01A"), Color.GRAY)
                //.setTextColor(R.drawable.tabstripbg)
                .setDividerColor(Color.parseColor("#45C01A"))
                .setDividerPadding(0)
                .setUnderlineColor(Color.parseColor("#3045C01A"))
                .setUnderlineHeight(0)
                .setIndicatorColor(Color.parseColor("#7045C01A"))
                .setIndicatorHeight(/*UIUtils.getDimen(28)*/50);


    }

    private void setupViewpager() {
        int[] mSelectors = new int[]{R.drawable.hy_tab1,
                R.drawable.hy_tab2,
                R.drawable.hy_tab3, R.drawable.hy_tab4};
        HomeAadapter adapter = new HomeAadapter(getSupportFragmentManager(), mSelectors);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mSelectors.length);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        tabBtn.bindViewPager(mViewPager);
        showPromptMsg(tabBtn);

    }

    public void clearPromptMsg(ISlidingTabStrip slidingTabStrip) {
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            slidingTabStrip.setPromptNum(i, 0);
        }
    }


    public void showPromptMsg(ISlidingTabStrip slidingTabStrip) {
        slidingTabStrip.setPromptNum(1, 9).
                setPromptNum(0, 10).
                setPromptNum(2, -9).
                setPromptNum(3, 100);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.hy_activity_home;
    }
}
