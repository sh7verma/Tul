package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.AfterWalkthroughActivity;
import com.app.tul.ChatActivity;
import com.app.tul.ListingTulDetailActivity;
import com.app.tul.OtherUserProfileActivity;
import com.app.tul.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import api.RetrofitClient;
import customviews.CircleTransform;
import database.Db;
import model.ChatDialogModel;
import model.CheckChatModel;
import model.NearByTulListingModel;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.CustomLoadingDialog;
import utils.FirebaseListeners;
import utils.Utils;

/**
 * Created by applify on 11/1/2017.
 */

public class NearByTulListingAdapter extends RecyclerView.Adapter<NearByTulListingAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int DELETE = 2;
    private static final int VIEW_PROG = 3;
    ArrayList<NearByTulListingModel.ResponseBean> mData;
    LinearLayout.LayoutParams viewParms, cvParms;
    Db db;
    boolean isChatDisables;
    private Context con;
    private Utils utils;
    private int mWidth, mHeight, mTulImageHeight, mProfileImageDimen, mTulImageWidth;

    public NearByTulListingAdapter(Context con, ArrayList<NearByTulListingModel.ResponseBean> mData) {
        this.con = con;
        this.mData = mData;
        utils = new Utils(con);
        db = new Db(con);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        mTulImageHeight = mHeight / 3;
        mTulImageWidth = mWidth - (mWidth / 64);
        mProfileImageDimen = mWidth / 8;

        cvParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTulImageHeight);
        cvParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 64);

        viewParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParms.setMargins(mWidth / 18, 0, mWidth / 18, 0);
    }

    @Override
    public NearByTulListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_shortlist, parent, false);
            vhItem = new NearByTulListingAdapter.ViewHolder(v, viewType);
            return vhItem;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            vhItem = new NearByTulListingAdapter.ViewHolder(v, viewType);
            return vhItem;
        }
    }

    @Override
    public void onBindViewHolder(final NearByTulListingAdapter.ViewHolder holder, final int position) {
        if (holder.Holderid == 1) {

            if (mData.get(position).getUser_id() == Integer.parseInt(utils.getString("user_id", "")))
                holder.imgChat.setVisibility(View.INVISIBLE);
            else
                holder.imgChat.setVisibility(View.VISIBLE);

            holder.txtOwnerName.setText(mData.get(position).getOwner());

            holder.txtTittle.setText(mData.get(position).getTitle());

            holder.txtPrice.setText(mData.get(position).getCurrency()+ " " + mData.get(position).getPrice());

            holder.srbTul.setRating(mData.get(position).getRating());

            if (mData.get(position).getAttachment().get(0).getType().equals(Constants.IMAGE_TEXT)) {
                holder.imgVideoPlay.setVisibility(View.GONE);
                Picasso.with(con).load(mData.get(position).getAttachment().get(0).getAttachment())
                        .resize(mTulImageWidth, mTulImageHeight).centerCrop().placeholder(R.drawable.primary_ripple)
                        .into(holder.imgTul);
            } else if (mData.get(position).getAttachment().get(0).getType().equals(Constants.VIDEO_TEXT)) {
                holder.imgVideoPlay.setVisibility(View.VISIBLE);
                Picasso.with(con).load(mData.get(position).getAttachment().get(0).getThumbnail())
                        .resize(mTulImageWidth, mTulImageHeight)
                        .centerCrop().placeholder(R.drawable.primary_ripple)
                        .into(holder.imgTul);
            }

            Picasso.with(con).load(mData.get(position).getOwner_pic())
                    .resize(mProfileImageDimen, mProfileImageDimen)
                    .centerCrop().placeholder(R.mipmap.ic_small_ph_tul)
                    .transform(new CircleTransform())
                    .into(holder.imgProfile);

            holder.imgChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        CustomLoadingDialog.getLoader().showLoader(con);
                        int sortArray[] = new int[]{
                                mData.get(position).getUser_id(), Integer.parseInt(utils.getString("user_id", ""))
                        };
                        Arrays.sort(sortArray);
                        String dialogueId = String.valueOf(sortArray[0]) + "," + String.valueOf(sortArray[1]);
                        updateToServer(dialogueId, mData.get(position));
                    } else {
                        loginFirst();
                    }
                }
            });

            holder.llRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.PROFILE_ID = 0;
                    Intent intent = new Intent(con, ListingTulDetailActivity.class);
                    intent.putExtra("data", mData.get(position));
                    intent.putExtra("position", position);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(((Activity) con),
                                holder.imgTul, con.getString(R.string.transition));
                        ((Activity) con).startActivityForResult(intent, DELETE, option.toBundle());
                    } else {
                        ((Activity) con).startActivityForResult(intent, DELETE);
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                    }
                }
            });

            holder.llNameInside.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, OtherUserProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getUser_id());
                        intent.putExtra("other_user_name", mData.get(position).getOwner());
                        intent.putExtra("other_user_pic", mData.get(position).getOwner_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        loginFirst();
                    }
                }
            });

            holder.imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.ISGUEST, 0) == 0) {
                        Constants.PROFILE_ID = mData.get(position).getUser_id();
                        Intent intent = new Intent(con, OtherUserProfileActivity.class);
                        intent.putExtra("other_user_id", mData.get(position).getUser_id());
                        intent.putExtra("other_user_name", mData.get(position).getOwner());
                        intent.putExtra("other_user_pic", mData.get(position).getOwner_pic());
                        con.startActivity(intent);
                        ((Activity) con).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        loginFirst();
                    }
                }
            });
        }
    }

    private void loginFirst() {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.Login_First)
                .setCancelable(false)
                .setPositiveButton(R.string.confrim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent start = new Intent(con, AfterWalkthroughActivity.class);
//                        start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        start.putExtra(Constants.REG_GUEST, 1);
                        ((Activity) con).startActivity(start);
//                        ((Activity) con).finish();
                        ((Activity) con).overridePendingTransition(R.anim.slideup_in, R.anim.slideup_out);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) != null ? TYPE_ITEM : VIEW_PROG;
    }

    public void regitserChatDialog(String dialogueId, NearByTulListingModel.ResponseBean responseBean) {

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        String key = mRef.child(Constants.CHAT_ENDPOINT).push().getKey();

        long time = System.currentTimeMillis();
        Log.e("Current Time = ", time + "");
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        ChatDialogModel mChatDialog = new ChatDialogModel();

//      particpantId = user_id;
        mChatDialog.setChat_dialog_id(key);
        mChatDialog.setLast_message(Constants.CHAT_REGEX);
        mChatDialog.setLast_message_id("");
        mChatDialog.setLast_message_sender_id("");
        mChatDialog.setLast_message_time(String.valueOf(new Date(time).getTime()));
        mChatDialog.setParticipant_ids(dialogueId);
        mChatDialog.setDialog_created_time(String.valueOf(new Date(time).getTime()));

        /// delete dialog time
        Map<String, String> map = new HashMap<String, String>();
        map.put(utils.getString("user_id", ""), String.valueOf(new Date(time).getTime()));
        map.put(String.valueOf(responseBean.getUser_id()), String.valueOf(new Date(time).getTime()));
        mChatDialog.setDelete_dialog_time(map);

        /// access_token
        Map<String, String> mapAccessToken = new HashMap<String, String>();
        mapAccessToken.put(utils.getString("user_id", ""), utils.getString("access_token", ""));
        mapAccessToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getAccess_token());
        mChatDialog.setAccess_token(mapAccessToken);

        /// platform status
        Map<String, String> mapPlatformStatus = new HashMap<String, String>();
        mapPlatformStatus.put(utils.getString("user_id", ""), String.valueOf(Constants.PLATFORM_STATUS));
        mapPlatformStatus.put(String.valueOf(responseBean.getUser_id()), responseBean.getPlatform_status());
        mChatDialog.setPlatform_status(mapPlatformStatus);

        /// profile_pic
        Map<String, String> mapProfilePic = new HashMap<String, String>();
        mapProfilePic.put(utils.getString("user_id", ""), utils.getString("profile_pic", ""));
        mapProfilePic.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner_pic());
        mChatDialog.setProfile_pic(mapProfilePic);

        /// device_token
        Map<String, String> mapDeviceToken = new HashMap<String, String>();
        mapDeviceToken.put(utils.getString("user_id", ""), utils.getString("device_token", ""));
        mapDeviceToken.put(String.valueOf(responseBean.getUser_id()), responseBean.getDevice_token());
        mChatDialog.setPush_token(mapDeviceToken);

        /// unread count
        Map<String, String> mapUnreadCount = new HashMap<String, String>();
        mapUnreadCount.put(utils.getString("user_id", ""), "0");
        mapUnreadCount.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setUnread_count(mapUnreadCount);

        /// name
        Map<String, String> mapName = new HashMap<String, String>();
        mapName.put(utils.getString("user_id", ""), utils.getString("user_name", ""));
        mapName.put(String.valueOf(responseBean.getUser_id()), responseBean.getOwner());
        mChatDialog.setName(mapName);

        /// mute
        Map<String, String> mapMute = new HashMap<String, String>();
        mapMute.put(utils.getString("user_id", ""), Constants.UNMUTE);
        mapMute.put(String.valueOf(responseBean.getUser_id()), Constants.UNMUTE);
        mChatDialog.setMute(mapMute);

        //block status
        Map<String, String> blockmap = new HashMap<String, String>();
        blockmap.put(utils.getString("user_id", ""), "0");
        blockmap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setBlock_status(blockmap);

        //Delete Dialog status
        Map<String, String> deleteDialogMap = new HashMap<String, String>();
        deleteDialogMap.put(utils.getString("user_id", ""), "0");
        deleteDialogMap.put(String.valueOf(responseBean.getUser_id()), "0");
        mChatDialog.setDelete_outer_dialog(deleteDialogMap);

        updateProfileDialogs(key, String.valueOf(responseBean.getUser_id()));

//        updateToServer(key, responseBean.getUser_id());

        mRef.child(Constants.CHAT_ENDPOINT).child(key).setValue(mChatDialog);
        FirebaseListeners.getInstance().startChatListener(key);
        db.addConversation(mChatDialog);

        /// updating dialog to server
        dialogApi(key, String.valueOf(responseBean.getUser_id()));

        Intent inChat = new Intent(con, ChatActivity.class);
        inChat.putExtra("dialog_id", mChatDialog.getChat_dialog_id());
        inChat.putExtra("name", responseBean.getOwner());
        inChat.putExtra("other_block_status", mChatDialog.getBlock_status().get(responseBean.getUser_id()));
        inChat.putExtra("my_block_status", mChatDialog.getBlock_status().get(utils.getString("user_id", "")));

        con.startActivity(inChat);
        CustomLoadingDialog.getLoader().dismissLoader();
    }

    private void updateProfileDialogs(final String dialogId, final String otherUserId) {
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child(Constants.USER_ENDPOINT).child(utils.getString("user_id", ""))
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);

        mRef.child(Constants.USER_ENDPOINT).child(otherUserId)
                .child("chat_dialog_ids").child(dialogId).setValue(dialogId);
        db.addDialog(dialogId);
    }

    private void updateToServer(final String dialogueId, final NearByTulListingModel.ResponseBean responseBean) {

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        final Query query = mRef.child(Constants.CHAT_ENDPOINT).orderByChild("participant_ids").equalTo("" + dialogueId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isChatDisabled(responseBean.getId(), dialogueId, responseBean, dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError);
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    void dialogApi(String dialogId, String otherUserId) {
        Call<String> call = RetrofitClient.getInstance().updateDialog(utils.getString("access_token", ""), dialogId, otherUserId);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }


    void isChatDisabled(int id, final String dialogueId, final NearByTulListingModel.ResponseBean responseBean, final DataSnapshot dataSnapshot) {
        Call<CheckChatModel> call = RetrofitClient.getInstance().checkChat(utils.getString("access_token", ""), String.valueOf(id));
        call.enqueue(new retrofit2.Callback<CheckChatModel>() {
            @Override
            public void onResponse(Call<CheckChatModel> call, Response<CheckChatModel> response) {
                CustomLoadingDialog.getLoader().dismissLoader();
                if (response != null) {
                    if (response.body().getResponse().getIs_chat_block() == 0) {
                        if (dataSnapshot.getValue() == null) {
                            regitserChatDialog(dialogueId, responseBean);
                        } else {
                            ChatDialogModel mChat = null;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                mChat = data.getValue(ChatDialogModel.class);
                            }
                            Intent inChat = new Intent(con, ChatActivity.class);
                            inChat.putExtra("dialog_id", mChat.getChat_dialog_id());
                            inChat.putExtra("name", responseBean.getOwner());
                            inChat.putExtra("other_block_status", mChat.getBlock_status().get(String.valueOf(responseBean.getUser_id())));
                            inChat.putExtra("my_block_status", mChat.getBlock_status().get(utils.getString("user_id", "")));
                            con.startActivity(inChat);
                        }
                    } else {
                        if (response.body().getResponse().getUser_block_status() == 1) {
                            // I am Blocked
                            utils.setInt(Constants.BLOCKSTATUS, 1);
                            Toast.makeText(con, R.string.contact_admin_user, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getOwner_block_status() == 1) {
                            //Other is Blocked
                            Toast.makeText(con, R.string.owner_is_blocked, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResponse().getAdmin_pause_status() == 1) {
                            //Place Blocked
                            Toast.makeText(con, R.string.place_is_blocked, Toast.LENGTH_SHORT).show();
                        }
                    }
                    utils.setInt(Constants.BLOCKSTATUS, response.body().getResponse().getUser_block_status());

                } else {
                    Toast.makeText(con, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckChatModel> call, Throwable t) {
                CustomLoadingDialog.getLoader().dismissLoader();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        int Holderid;
        LinearLayout llName, llRating, llNameInside;
        TextView txtTittle, txtPrice, txtOwner, txtOwnerName;
        SimpleRatingBar srbTul;
        CardView cvTul;
        ImageView imgTul, imgProfile, imgChat, imgVideoPlay;
        View view1;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
                cvTul = (CardView) itemView.findViewById(R.id.cv_tul);
                cvTul.setLayoutParams(cvParms);

                imgTul = (ImageView) itemView.findViewById(R.id.img_tul);

                view1 = (View) itemView.findViewById(R.id.view_1);
                view1.setLayoutParams(viewParms);

                txtTittle = (TextView) itemView.findViewById(R.id.txt_tittle);
                txtTittle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));
                txtTittle.setPadding(mWidth / 32, 0, mWidth / 5, mWidth / 28);

                txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
                txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.043));
                txtPrice.setPadding(0, 0, mWidth / 32, mWidth / 32);

                txtOwner = (TextView) itemView.findViewById(R.id.txt_owner);
                txtOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));

                LinearLayout.LayoutParams nameParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                nameParms.setMargins(mWidth / 32, 0, mWidth / 32, mWidth / 32);
                llName = (LinearLayout) itemView.findViewById(R.id.ll_name);
                llName.setLayoutParams(nameParms);

                llNameInside = (LinearLayout) itemView.findViewById(R.id.ll_name_inside);

                txtOwnerName = (TextView) itemView.findViewById(R.id.txt_owner_name);
                txtOwnerName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                LinearLayout.LayoutParams profileParms = new LinearLayout.LayoutParams(mProfileImageDimen, mProfileImageDimen);
                profileParms.setMargins(mWidth / 64, 0, mWidth / 32, 0);
                imgProfile = (ImageView) itemView.findViewById(R.id.img_profile);
                imgProfile.setLayoutParams(profileParms);

                imgVideoPlay = (ImageView) itemView.findViewById(R.id.img_video_play);

                imgChat = (ImageView) itemView.findViewById(R.id.img_chat);

                srbTul = (SimpleRatingBar) itemView.findViewById(R.id.srb_tul);
                srbTul.setPadding(0, mWidth / 32, mWidth / 32, 0);

                llRating = (LinearLayout) itemView.findViewById(R.id.ll_rating);
                Holderid = 1;
            } else {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
                Holderid = 0;
            }
        }
    }

}
