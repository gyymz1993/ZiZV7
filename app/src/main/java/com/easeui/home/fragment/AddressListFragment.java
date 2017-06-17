package com.easeui.home.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easeui.home.adapter.AddressAdapter;
import com.easeui.home.base.EaseContactListFragment;
import com.easeui.home.presenter.AddressListPresenter;
import com.easeui.home.view.IAddressListView;
import com.google.gson.Gson;
import com.hyphenate.DemoHelper;
import com.hyphenate.chat.EMClient;
import com.hyphenate.db.InviteMessgeDao;
import com.hyphenate.db.UserDao;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.ui.AddContactActivity;
import com.hyphenate.ui.ChatActivity;
import com.hyphenate.ui.ContactListFragment;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.hyphenate.widget.ContactItemView;
import com.lsjr.zizisteward.Config;
import com.lsjr.zizisteward.MyApplication;
import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.bean.AddressBookBean;
import com.yangshao.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @version 通讯录
 * @author: gyymz1993
 * 创建时间：2017/5/29 13:14
 **/
public class AddressListFragment extends EaseContactListFragment<AddressListPresenter> implements IAddressListView {


    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_rv_addresslist)
    RecyclerView mRecyclerView;

    private String[] title = new String[]{"新的朋友", "删除好友",
            "手机通讯录", "名片夹", "自己"};
    private int[] icons = new int[]{R.drawable.hy_new_friends,
            R.drawable.hy_delete_friens, R.drawable.hy_address_book,
            R.drawable.ic_launcher_round, R.drawable.ic_launcher_round};

    List<AddressBookBean.FriendsBean> defaultFriendsBeans;
    private AddressAdapter mAddressAdapter;

    @Override
    protected AddressListPresenter createPresenter() {
        return new AddressListPresenter(this);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mToolbar.setTitle("通讯录");
            mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
            mToolbar.setNavigationIcon(R.drawable.title_back);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        defaultFriendsBeans = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            AddressBookBean.FriendsBean friendsBean = new AddressBookBean.FriendsBean();
            friendsBean.setName(title[i]);
            friendsBean.setPhoto(icons[i] + "");
            defaultFriendsBeans.add(friendsBean);
        }
        mAddressAdapter = new AddressAdapter(getContext(), defaultFriendsBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAddressAdapter);
        Map<String, String> map = new HashMap<>();
        map.put("OPT", "206");
        map.put("user_id", MyApplication.getUserId());
        createPresenter().loadAdressListforNet(map);
    }


    @Override
    public void onLoadAdressResult(String result) {
        AddressBookBean abBean = new Gson().fromJson(result, AddressBookBean.class);
        List<AddressBookBean.FriendsBean> friendsBeans = abBean.getFriends();
        defaultFriendsBeans.addAll(friendsBeans);
        mAddressAdapter.notifyDataSetChanged(defaultFriendsBeans);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
        //init list
//        for (int position=0;position<contactList.size();position++) {
//            EaseUser user = contactList.get(position);
//            if (user == null) return;
//            String username = user.getUsername();
//            String header = user.getInitialLetter();
//            EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
//        }

    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup viewGrop) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_addresslist, null);
    }


    private static final String TAG = ContactListFragment.class.getSimpleName();
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private ContactInfoSyncListener contactInfoSyncListener;
    private InviteMessgeDao inviteMessgeDao;


    @Override
    public void refresh() {
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.refresh();
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(getActivity());
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {

        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.setUpView();

        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contactSyncListener != null) {
            DemoHelper.getInstance().removeSyncContactListener(contactSyncListener);
            contactSyncListener = null;
        }

        if (blackListSyncListener != null) {
            DemoHelper.getInstance().removeSyncBlackListListener(blackListSyncListener);
        }

        if (contactInfoSyncListener != null) {
            DemoHelper.getInstance().getUserProfileManager().removeSyncContactInfoListener(contactInfoSyncListener);
        }
    }


    /**
     * delete contact
     */
    public void deleteContact(final EaseUser tobeDeleteUser) {
        String st1 = getResources().getString(R.string.deleting);
        final String st2 = getResources().getString(R.string.Delete_failed);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
                    // remove user from memory and database
                    UserDao dao = new UserDao(getActivity());
                    dao.deleteContact(tobeDeleteUser.getUsername());
                    DemoHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            contactList.remove(tobeDeleteUser);
                            //contactListLayout.refresh();

                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).start();

    }

    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (success) {
                                refresh();
                            } else {
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                }
            });
        }
    }

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (success) {
                        refresh();
                    }
                }
            });
        }

    }

}
