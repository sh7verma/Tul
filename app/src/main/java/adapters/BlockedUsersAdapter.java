package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.tul.BlockedUsersActivity;
import com.app.tul.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import interfaces.YouChooseProfileListener;
import model.ChatDialogModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 14/12/17.
 */

public class BlockedUsersAdapter extends RecyclerView.Adapter<BlockedUsersAdapter.ViewHolder> {

    private Context con;
    private Utils utils;
    private int mWidth, mHeight;
    HashMap<String, ChatDialogModel> mConversationMap;
    ArrayList<String> mConversationKeys;
    BlockedUsersActivity activity;

    public BlockedUsersAdapter(Context con, HashMap<String, ChatDialogModel> conversationMap, ArrayList<String> conversationKeys) {
        this.con = con;
        utils = new Utils(con);
        activity= (BlockedUsersActivity) con;
        mConversationMap = conversationMap;
        mConversationKeys = conversationKeys;
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blocked_users, parent, false);
        ViewHolder vhItem = new ViewHolder(v);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ChatDialogModel chatDialogModel = mConversationMap.get(mConversationKeys.get(position));

        String particpantIdArray[] = chatDialogModel.getParticipant_ids().split(",");
        String particpantId = null;

        for (int i = 0; i < particpantIdArray.length; i++) {
            if (!particpantIdArray[i].equals(utils.getString("user_id", ""))) {
                particpantId = particpantIdArray[i];
            }
        }

        holder.txtName.setText(chatDialogModel.getName().get(particpantId));

        final String finalParticpantId = particpantId;

        holder.txtUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBlockDialog(finalParticpantId,chatDialogModel);
             }
        });

    }

    void unBlockDialog(final String finalParticpantId, final ChatDialogModel chatDialogModel) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(con);
        alertDialog.setMessage(R.string.unblock_warning);
        alertDialog.setPositiveButton(R.string.UNBLOCK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ENDPOINT).child(chatDialogModel.getChat_dialog_id())
                        .child("block_status").child(finalParticpantId).setValue("0");

                activity.updateAdapter(chatDialogModel.getChat_dialog_id());
                dialog.dismiss();

            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return mConversationMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtUnblock;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.tv_name);
            txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

            txtUnblock = (TextView) itemView.findViewById(R.id.tv_unblock);
            txtUnblock.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.03));
            txtUnblock.setPadding(mWidth / 42, mWidth / 42, mWidth / 42, mWidth / 42);

            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            llMain.setPadding(0, mHeight / 28, 0, mHeight / 28);

        }
    }
}
