/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easeui.home.base;

import android.app.ProgressDialog;
import android.os.Handler;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;
import com.ys.lib.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * contact list
 * 
 */
public abstract class EaseContactListFragment<P extends BasePresenter> extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";
    protected List<EaseUser> contactList;
    protected EditText query;
    protected Handler handler = new Handler();
    //protected EaseContactList contactListLayout;
    protected boolean isConflict;
    private Map<String, EaseUser> contactsMap;

    @Override
    protected void setUpView() {
        EMClient.getInstance().addConnectionListener(connectionListener);
        contactList = new ArrayList<>();
        getContactList();
        //init list
        //contactListLayout.init(contactList);
    }



    /**
     * move user to blacklist
     */
    protected void moveToBlacklist(final String username){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st1 = getResources().getString(R.string.Is_moved_into_blacklist);
        final String st2 = getResources().getString(R.string.Move_into_blacklist_success);
        final String st3 = getResources().getString(R.string.Move_into_blacklist_failure);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //move to blacklist
                    EMClient.getInstance().contactManager().addUserToBlackList(username,false);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
                            refresh();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st3, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
        
    }
    
    // refresh ui
    public void refresh() {
        getContactList();
        //contactListLayout.refresh();
    }
    

    @Override
    public void onDestroy() {
        EMClient.getInstance().removeConnectionListener(connectionListener);
        super.onDestroy();
    }
    

    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        contactList.clear();
        if(contactsMap == null){
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager().getBlackListUsernames();
            while (iterator.hasNext()) {
                Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")){
                    if(!blackList.contains(entry.getKey())){
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        contactList.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if(lhs.getInitialLetter().equals(rhs.getInitialLetter())){
                    return lhs.getNick().compareTo(rhs.getNick());
                }else{
                    if("#".equals(lhs.getInitialLetter())){
                        return 1;
                    }else if("#".equals(rhs.getInitialLetter())){
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });

    }
    
    
    
    protected EMConnectionListener connectionListener = new EMConnectionListener() {
        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        }
        
        @Override
        public void onConnected() {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    onConnectionConnected();
                }

            });
        }
    };
    private EaseContactListItemClickListener listItemClickListener;
    
    
    protected void onConnectionDisconnected() {
        
    }
    
    protected void onConnectionConnected() {
        
    }
    
    /**
     * set contacts map, key is the hyphenate id
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EaseUser> contactsMap){
        this.contactsMap = contactsMap;
    }
    
    public interface EaseContactListItemClickListener {
        /**
         * on click event for item in contact list 
         * @param user --the user of item
         */
        void onListItemClicked(EaseUser user);
    }
    
    /**
     * set contact list item click listener
     * @param listItemClickListener
     */
    public void setContactListItemClickListener(EaseContactListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }
    
}
