package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.ConversationActivity;
import com.app.tul.DashBoardActivity;
import com.app.tul.HistoryActivity;
import com.app.tul.ListYourTulActivity;
import com.app.tul.OwnProfileActivity;
import com.app.tul.PaymentActivity;
import com.app.tul.R;
import com.app.tul.Sales.BecomeSellerActivity;
import com.app.tul.Sales.SalesSearchActivity;
import com.app.tul.SearchActivity;
import com.app.tul.SearchResultActivity;
import com.app.tul.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import api.RetrofitClient;
import customviews.CircleTransform;
import database.Db;
import dialogs.VerifyEmailDialog;
import interfaces.SwitchClickInterface;
import model.ChatDialogModel;
import model.TransactionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Connection_Detector;
import utils.Constants;
import utils.Utils;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BOTTOM = 2;
    private static final int REFRESH_MARKERS = 5;
    SwitchClickInterface mSwitch;
    private String mNavTitles[];
    private int mIcons[];
    private DrawerLayout dlNavigation;
    private RecyclerView rvMenu;
    private Context mContext;
    private Utils utils;
    private int mWidth, mHeight;
    private Db db;

    public NavigationAdapter(Context con, String[] Titles, int[] Icons, DrawerLayout drawer, RecyclerView mRecyclerView,
                             SwitchClickInterface mSwitchClick) {
        mNavTitles = Titles;
        mIcons = Icons;
        mContext = con;
        dlNavigation = drawer;
        rvMenu = mRecyclerView;
        utils = new Utils(mContext);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        db = new Db(mContext);
        this.mSwitch = mSwitchClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vhItem;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_BOTTOM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_bottom, parent, false);
            vhItem = new ViewHolder(v, viewType);
            return vhItem;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.Holderid == 0) {
            ////header
            holder.txtName.setText(utils.getString("user_name", ""));
            holder.txtRate.setText(String.valueOf(utils.getInt("rating", 0)));
            if (!TextUtils.isEmpty(utils.getString("profile_pic", ""))) {
                Picasso.with(mContext).load(utils.getString("profile_pic", "")).resize(Constants.dpToPx(40), Constants.dpToPx(40))
                        .transform(new CircleTransform())
                        .centerCrop().placeholder(R.mipmap.ic_no_image)
                        .into(holder.imgProfile);
            } else {
                Picasso.with(mContext).load(R.mipmap.ic_no_image).resize(Constants.dpToPx(40), Constants.dpToPx(40))
                        .transform(new CircleTransform())
                        .centerCrop().placeholder(R.mipmap.ic_no_image)
                        .into(holder.imgProfile);
            }

            holder.llHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                    Intent intent = new Intent(mContext, OwnProfileActivity.class);
                    ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                    } else {
//                        Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
//                    }
                    dlNavigation.closeDrawer(Gravity.LEFT);
                }
            });

        } else if (holder.Holderid == 1) {
            ///// item
            holder.txtMenu.setText(mNavTitles[position - 1]);

            /// prashnat sir code
            if (mNavTitles[position - 1].equals("Inbox")) {
                Map<String, ChatDialogModel> totalConversationMap = new LinkedHashMap<>();
                ArrayList<String> conversationKeys = new ArrayList<>();

                totalConversationMap = db.getAllConversation();
                for (String chatDialogId : totalConversationMap.keySet()) {
                    ChatDialogModel chatDialogModel = new ChatDialogModel();
                    chatDialogModel = totalConversationMap.get(chatDialogId);
                    if (chatDialogModel.getLast_message().equals(Constants.CHAT_REGEX)) {

                    } else {
                        if (chatDialogModel.getUnread_count().get(utils.getString("user_id", "")) != null) {
                            if (!chatDialogModel.getUnread_count().get(utils.getString("user_id", "")).equals("0")) {
                                conversationKeys.add(chatDialogId);
                            }
                        }
                    }
                }
                Log.d("Unreada", String.valueOf(conversationKeys.size()));

                if (conversationKeys.size() > 0) {
                    holder.txtBageCount.setVisibility(View.VISIBLE);
                    if (conversationKeys.size() > 99) {
                        holder.txtBageCount.setText("99+");
                    } else {
                        holder.txtBageCount.setText(String.valueOf(conversationKeys.size()));
                    }
                } else {
                    holder.txtBageCount.setVisibility(View.GONE);
                }
            } else {
                holder.txtBageCount.setVisibility(View.GONE);
            }
            /////
            holder.imgIcon.setImageResource(mIcons[position - 1]);
            if (position == 1)
                holder.viewTop.setVisibility(View.VISIBLE);
            else
                holder.viewTop.setVisibility(View.GONE);

            if (position == mNavTitles.length && utils.getInt(Constants.USER_LOGIN_MODE, 0) == 1) {
                holder.llItem.setVisibility(View.INVISIBLE);
            } else {
                holder.llItem.setVisibility(View.VISIBLE);
            }

            if (position == 4) {
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
                    holder.llItem.setVisibility(View.GONE);
                } else {
                    holder.llItem.setVisibility(View.VISIBLE);
                }
            }

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Position = ", position + "");
                    if (position == 2) {
                        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {

                            if (Constants.TITLE != null) {
                                /// filter applied
                                Intent searchIntent = new Intent(mContext, SearchResultActivity.class);
                                ((Activity) mContext).startActivity(searchIntent);
                                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            } else {
                                /// filter not applied
                                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                                ((Activity) mContext).startActivity(searchIntent);
                                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                        } else {
                            if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) != Constants.USER_RENTAL) {
                                Intent searchIntent = new Intent(mContext, SalesSearchActivity.class);
                                ((Activity) mContext).startActivity(searchIntent);
                                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            }
                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 3) {
//                        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {

                        Intent intent = new Intent(mContext, HistoryActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                        } else {
//                            Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
//
//                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 4) {
                        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {
                            Intent intent = new Intent(mContext, ConversationActivity.class);
                            ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);

                    } else if (position == 5) {
//                        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {

                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                        } else {
//                            Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
//                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);

                    } else if (position == 6) {
                        Intent intent = new Intent(mContext, SettingsActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    } else if (position == 7) {
                        if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_RENTAL) {

                            Constants.CURRENT_DATE = "";
                            Intent intent = new Intent(mContext, DashBoardActivity.class);
                            ((Activity) mContext).startActivityForResult(intent, REFRESH_MARKERS);
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        } else {
                            Toast.makeText(mContext, R.string.Work_in_progress, Toast.LENGTH_SHORT).show();
                        }
                        dlNavigation.closeDrawer(Gravity.LEFT);

                    } else if (position == 1) {
                        dlNavigation.closeDrawer(Gravity.LEFT);
                    }
                }
            });
        } else if (holder.Holderid == 2) {
            // bottom
            if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
                if (utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER) {
//                    holder.txtBecome.setVisibility(View.GONE);
                    holder.txtBecome.setText(R.string.switch_to_rental);
                } else {
                    holder.txtBecome.setVisibility(View.VISIBLE);
                }
            } else {
                if (utils.getInt("lender", 0) == 1) {
//                    holder.txtBecome.setVisibility(View.GONE);
                    holder.txtBecome.setText(R.string.switch_to_sales);
                } else {
                    holder.txtBecome.setVisibility(View.VISIBLE);
                }
            }
            holder.txtBecome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (utils.getInt(Constants.BLOCKSTATUS, 0) == 0) {
                        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
                            if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 1) {
                                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
                                    if (utils.getInt(Constants.ISSELLER, 0) == Constants.IS_SELLER) {
                                        holder.txtBecome.setText(R.string.switch_to_rental);
                                        mSwitch.onSwitchClick();

                                    } else {
//                                      holder.txtBecome.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(mContext, BecomeSellerActivity.class);
                                        ((Activity) mContext).startActivity(intent);
                                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    }

                                } else {
                                    if (utils.getInt("lender", 0) == 1) {
                                        holder.txtBecome.setText(R.string.switch_to_sales);
                                        mSwitch.onSwitchClick();
                                    } else {
//                                        holder.txtBecome.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(mContext, ListYourTulActivity.class);
                                        ((Activity) mContext).startActivity(intent);
                                        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    }

                                }
                                dlNavigation.closeDrawer(Gravity.LEFT);
                            } else {
                                hitEmailVerifyAPI();
                            }
                        } else
                            Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
                    } else {
                        userBlockDialog();
                    }
                }
            });
        }
    }

    private void hitEmailVerifyAPI() {
        Call<TransactionModel> call = RetrofitClient.getInstance().getTransactionPercentage(utils.getString("access_token", ""));
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if (response.body().getResponse() != null) {
                    Log.e("Transaction %age = ", response.body().getResponse());
                    utils.setString("transaction_percentage", response.body().getResponse());
                    utils.setInt(Constants.ISEMAILSKIP, response.body().getIs_email_skip());
                    utils.setInt(Constants.EMAIL_VERIFY, response.body().getEmail_verify());

                    if (utils.getInt(Constants.EMAIL_VERIFY, 0) == 0) {
                        new VerifyEmailDialog(mContext, mWidth, mContext.getResources().getString(R.string.verify_email)).showDialog();
                    }

                } else {
                    Toast.makeText(mContext, R.string.something_was_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {

            }
        });
    }

    private void userBlockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.contact_admin_tul)
                .setCancelable(false)
                .setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
                        String[] recipients = {"info@thetulapp.com"};
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "CONTACT");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        mContext.startActivity(intent);
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public int getItemCount() {
        return mNavTitles.length + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        if (isPositionBottom(position))
            return TYPE_BOTTOM;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionBottom(int position) {
        return position == (mNavTitles.length + 1);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        /// header
        LinearLayout llHeader;
        ImageView imgProfile;
        TextView txtName, txtRate;

        /// item
        LinearLayout llItem;
        ImageView imgIcon;
        TextView txtBageCount;
        TextView txtMenu;
        View viewTop;

        /// bottom
        TextView txtBecome;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_HEADER) {
                llHeader = (LinearLayout) itemView.findViewById(R.id.ll_header);
                imgProfile = (ImageView) itemView.findViewById(R.id.img_profile);

                txtName = (TextView) itemView.findViewById(R.id.txt_name);
                txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                txtRate = (TextView) itemView.findViewById(R.id.txt_rate);
                txtRate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
                Holderid = 0;
            } else if (ViewType == TYPE_ITEM) {
                llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
                imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);

                txtMenu = (TextView) itemView.findViewById(R.id.txt_menu);
                txtMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

                txtBageCount = (TextView) itemView.findViewById(R.id.txt_bageCount);
                txtBageCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.030));

                viewTop = (View) itemView.findViewById(R.id.view_top);
                Holderid = 1;
            } else if (ViewType == TYPE_BOTTOM) {
                txtBecome = (TextView) itemView.findViewById(R.id.txt_become);
                txtBecome.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
                if (utils.getInt(Constants.USER_LOGIN_MODE, Constants.USER_RENTAL) == Constants.USER_BUY) {
                    txtBecome.setText(R.string.become_a_seller);
                }
                Holderid = 2;
            }
        }
    }


}
