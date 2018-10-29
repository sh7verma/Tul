package utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.app.tul.ChatActivity;
import com.app.tul.ConversationActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.Db;
import interfaces.ChatListener;
import interfaces.FinishAct;
import interfaces.MessageListener;
import interfaces.ProfileListener;
import interfaces.RefreshDialogListener;
import interfaces.UnreadCountListener;
import interfaces.YouChooseProfileListener;
import model.ChatDialogModel;
import model.ChatMessageModel;
import model.ChatProfileModel;


public class FirebaseListeners {

    private static FirebaseListeners mFirebaseListeners;

    private ChatListener mChatLsitener;
    private RefreshDialogListener mRefreshDialogListener;
    private ProfileListener mProfileListener;
    private MessageListener mMessageListener;
    private UnreadCountListener mUnReadCountListener;
    //    private YouChooseProfileListener mYouChooseListener;
    private String mChatActivityDialogId;
    ArrayList<String> mAllDialogListenersArray = new ArrayList<>();


    private DatabaseReference mDialogReference = FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT);
    DatabaseReference mProfileReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mMessageReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mOtherProfileReference = FirebaseDatabase.getInstance().getReference();
    //    ChatProfileModel chatProfileModel;
//    String youchoose;
    Db db;
    Utils utils;
    public static Context mContext;

    String ownUserId;

    private ChildEventListener mFirebaseProfileListener;
    private ChildEventListener mFirebaseOtherProfileListener;
    private ChildEventListener mFirebaseChatListener;
    private ChildEventListener mFirebaseMessageListener;
    public static FinishAct finishAct;

    public static void setFinishAct(FinishAct mContexttr) {
        finishAct = mContexttr;
    }

    public static FirebaseListeners getInstance() {
        if (mFirebaseListeners == null) {
            mFirebaseListeners = new FirebaseListeners();
        }
        return mFirebaseListeners;
    }


    public void startProfileListener(String userId) {
        ownUserId = userId;
        mFirebaseProfileListener = mProfileReference.child(Constants.USER_ENDPOINT).orderByChild("user_id").equalTo(userId).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("PROFILE", dataSnapshot.toString());


                ChatProfileModel chatProfileModel = dataSnapshot.getValue(ChatProfileModel.class);
//                youchoose = chatProfileModel.getYou_choose_status();
                if (chatProfileModel.getChat_dialog_ids() != null) {
                    for (String chatDialogId : chatProfileModel.getChat_dialog_ids().keySet()) {
                        Log.d("ChatDialogIdChild:", chatDialogId);
                        if (!mAllDialogListenersArray.contains(chatDialogId)) {
                            db.addDialog(chatDialogId);
                            startChatListener(chatDialogId);
                            mAllDialogListenersArray.add(chatDialogId);
                        }
                        startChatListener(chatDialogId);
                    }
                }
                deleteExtraDialogs(chatProfileModel.getChat_dialog_ids());

//                if (mYouChooseListener != null)
//                    mYouChooseListener.onChildAdded(dataSnapshot.getValue(ChatProfileModel.class));

//                if (LandingFragment.landing != null) {
//                    utils.setString("you_choose_status", chatProfileModel.getYou_choose_status());
//                    LandingFragment.landing.showHideCounter(chatProfileModel.getYou_choose_status());
//                }

            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                ChatProfileModel chatProfileModel = dataSnapshot.getValue(ChatProfileModel.class);
//                youchoose = chatProfileModel.getYou_choose_status();
                if (chatProfileModel.getBlock_status() != null && chatProfileModel.getBlock_status().equalsIgnoreCase("1")) {
//            new UserBlockedDialog(mContext);
                    finishAct.onfinish("block");

                }

                if (utils.getString("access_token", "").equals(chatProfileModel.getAccess_token())) {
                    if (chatProfileModel.getChat_dialog_ids() != null) {
                        ArrayList<String> allDialogs = db.getDialog("");
                        for (String chatDialogId : chatProfileModel.getChat_dialog_ids().keySet()) {
                            Log.d("ChatDialogIdChild:", chatDialogId);
                            if (!mAllDialogListenersArray.contains(chatDialogId)) {
                                db.addDialog(chatDialogId);
                                startChatListener(chatDialogId);
                                mAllDialogListenersArray.add(chatDialogId);
                            }
                        }
                    }
//                    if (mYouChooseListener != null)
//                        mYouChooseListener.onChildChanged(dataSnapshot.getValue(ChatProfileModel.class));

//                    if (LandingFragment.landing != null)
//                        LandingFragment.landing.showHideCounter(chatProfileModel.getYou_choose_status());
                } else {

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startChatListener(String dialogId) {
        mFirebaseChatListener = mDialogReference.orderByChild("chat_dialog_id").equalTo(dialogId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("dialog_chat", String.valueOf(dataSnapshot));
                ChatDialogModel chatDialogModel = dataSnapshot.getValue(ChatDialogModel.class);
//                intializeDataBase(mContext);
                if (chatDialogModel.getDelete_outer_dialog() == null) {
                    String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
                    Map<String, String> deleteDialogMap = new HashMap<String, String>();
                    deleteDialogMap.put(particpantIdArray[0], "0");
                    deleteDialogMap.put(particpantIdArray[1], "0");
                    FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT)
                            .child(chatDialogModel.getChat_dialog_id()).child("delete_outer_dialog")
                            .setValue(deleteDialogMap);
                    chatDialogModel.setDelete_outer_dialog(deleteDialogMap);
                }
                db.addConversation(chatDialogModel);
                if (mChatLsitener != null)
                    mChatLsitener.onChildAdded(chatDialogModel);
                startMessageListener(chatDialogModel.getChat_dialog_id());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("dialog_chat", String.valueOf(dataSnapshot));
                ChatDialogModel chatDialogModel = dataSnapshot.getValue(ChatDialogModel.class);
                if (chatDialogModel.getDelete_outer_dialog() == null) {
                    String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
                    Map<String, String> deleteDialogMap = new HashMap<String, String>();
                    deleteDialogMap.put(particpantIdArray[0], "0");
                    deleteDialogMap.put(particpantIdArray[1], "0");
                    FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT)
                            .child(chatDialogModel.getChat_dialog_id()).child("delete_outer_dialog")
                            .setValue(deleteDialogMap);
                    chatDialogModel.setDelete_outer_dialog(deleteDialogMap);
                }
                db.addConversation(chatDialogModel);
                if (mChatLsitener != null)
                    mChatLsitener.onChildChanged(chatDialogModel);
                if (mUnReadCountListener != null)
                    mUnReadCountListener.getUnreadCount(dataSnapshot.getValue(ChatDialogModel.class));

                if (mRefreshDialogListener != null)
                    mRefreshDialogListener.onRefreshDialog();

//                if (LandingFragment.landing != null)
//                    LandingFragment.landing.showHideCounter(youchoose);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ChatDialogModel chatDialogModel = dataSnapshot.getValue(ChatDialogModel.class);
                if (mChatLsitener != null)
                    mChatLsitener.onChildDelete(chatDialogModel);
                db.deleteDialog(chatDialogModel.getChat_dialog_id());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void startMessageListener(final String dialogID) {
        mFirebaseMessageListener = mMessageReference.child(Constants.MESSAGES_ENDPOINT).child(dialogID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    ChatMessageModel chatMessageModel = dataSnapshot.getValue(ChatMessageModel.class);

                    if (!TextUtils.isEmpty(chatMessageModel.getMessage_id())) {
                        db.addMessage(dataSnapshot.getValue(ChatMessageModel.class));
                        if ((!chatMessageModel.getSender_id().equals(ownUserId)) && (chatMessageModel.getMessage_status().equals(Constants.SEND))) {

                            if (mChatActivityDialogId != null) {
                                if (!mChatActivityDialogId.equals(chatMessageModel.getChat_dialog_id()))
                                    FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGES_ENDPOINT).child(dialogID)
                                            .child(chatMessageModel.getMessage_id()).child("message_status")
                                            .setValue(Constants.DELEIVERED);
                            } else {
                                FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGES_ENDPOINT).child(dialogID)
                                        .child(chatMessageModel.getMessage_id()).child("message_status")
                                        .setValue(Constants.DELEIVERED);
                            }

//                            FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGES_ENDPOINT).child(dialogID)
//                                    .child(chatMessageModel.getMessage_id()).child("message_status")
//                                    .setValue(Constants.DELEIVERED);
                        }
                        if (mMessageListener != null) {
                            mMessageListener.onMessageAdded(dataSnapshot.getValue(ChatMessageModel.class));
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (mMessageListener != null) {
                    mMessageListener.onMessageChanged(dataSnapshot.getValue(ChatMessageModel.class));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startOtherProfileListener(String userId) {
        mFirebaseOtherProfileListener = mOtherProfileReference.child(Constants.USER_ENDPOINT).orderByChild("user_id").equalTo(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("profile List", dataSnapshot.toString());
                if (mProfileListener != null)
                    mProfileListener.onProfileChildChanged(dataSnapshot.getValue(ChatProfileModel.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("profile", dataSnapshot.toString());
                if (mProfileListener != null)
                    mProfileListener.onProfileChildChanged(dataSnapshot.getValue(ChatProfileModel.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void intializeDataBase(Context context) {
        mContext = context;
        if (db == null)
            db = new Db(context);
        if (utils == null)
            utils = new Utils(context);
    }

    public void intializeChatListener(ConversationActivity conversationActivity) {
        mChatLsitener = conversationActivity;
    }

    public void intializeRefreshListener(ChatActivity chatActivity) {
        mRefreshDialogListener = chatActivity;
    }

    public void intializeMessageListener(ChatActivity chatActivity, String dialogID) {
        mMessageListener = chatActivity;
        mChatActivityDialogId = dialogID;
    }

    public void intializeUnreadListener(ChatActivity chatActivity) {
        mUnReadCountListener = chatActivity;
    }

    public void intializeOtherProfileListener(ChatActivity chatActivity) {
        mProfileListener = chatActivity;
    }

//    public void intializeYouChooseListener(YouChooseFragment youchoose) {
//        mYouChooseListener = youchoose;
//    }

    public void removeOtherProfileListener() {
        if (mOtherProfileReference != null && mFirebaseOtherProfileListener != null)
            mOtherProfileReference.removeEventListener(mFirebaseOtherProfileListener);
    }

    public void removeAllListeners() {
        if (mProfileReference != null && mFirebaseProfileListener != null)
            mProfileReference.removeEventListener(mFirebaseProfileListener);
        if (mDialogReference != null && mFirebaseChatListener != null)
            mDialogReference.removeEventListener(mFirebaseChatListener);
        if (mMessageReference != null && mFirebaseMessageListener != null)
            mMessageReference.removeEventListener(mFirebaseMessageListener);
    }

    private void deleteExtraDialogs(Map<String, String> chatDialogIDs) {
        ArrayList<String> dbDialogIds = db.getDialog("");
        if (chatDialogIDs != null) {
            ArrayList<String> firebaseDialogIds = new ArrayList<>();
            for (String chatDialogId : chatDialogIDs.keySet()) {
                firebaseDialogIds.add(chatDialogId);
            }
            dbDialogIds.removeAll(firebaseDialogIds);
        }
        for (int i = 0; i < dbDialogIds.size(); i++) {
            db.deleteDialog(dbDialogIds.get(i));
        }
    }
}
