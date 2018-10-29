package services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.tul.AfterLoginSelectMode;
import com.app.tul.BroadCastDialog;
import com.app.tul.ChatActivity;
import com.app.tul.CreateProfileActivity;
import com.app.tul.LandingActivity;
import com.app.tul.PendingTaskActivity;
import com.app.tul.PushBookingTulDetailActivity;
import com.app.tul.R;
import com.app.tul.RateYourExperienceActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;
import utils.Constants;
import utils.Utils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Utils utils;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
        utils = new Utils(getApplicationContext());
        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(data);
        }
    }

    private void sendNotification(Map<String, String> messageBody) {
        Intent intent = null;
        String message = messageBody.get("message");
        int notificationId;
        if (messageBody.get("push_type").equalsIgnoreCase("14")) {
            notificationId = utils.getInt("notificationId", 0);
            notificationId++;
            utils.setInt("notificationId", notificationId);
        } else {
            notificationId = Integer.parseInt(messageBody.get("id"));
        }

        if (utils.getInt("ring_notification", 0) == 0) {
            if (messageBody.get("push_type").equalsIgnoreCase("1")) {
                /// user books a tul push on lender side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_rented");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("2")) {
                /// user cancelled booking push on lender side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PendingTaskActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("3")) {
                /// lender cancelled booking push on user side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PendingTaskActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("4")) {
                /// lender marked as  received push on user side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PendingTaskActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("5")) {
                /// user mark received a tul push on lender side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_rented");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("6")) {
                /// user mark not received  push on lender side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_rented");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("7")) {
                /// user marked returned push on lender side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PendingTaskActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("8")) {
                /// lender mark not received  a tul push on user side.

                Intent broadCastIntent = new Intent(Constants.BROADCAST);
                broadCastIntent.putExtra("booking_id", messageBody.get("booking_id"));
                broadCastIntent.putExtra("tool_id", messageBody.get("tool_id"));
                broadCastIntent.putExtra("push_type", messageBody.get("push_type"));
                broadcaster.sendBroadcast(broadCastIntent);

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_owner");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("11")) {
                /// 24 hours prior to the booking date: Lender

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_rented");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("12")) {
                /// 24 hours prior to the booking date: User

                Intent notificationIntent = new Intent(Constants.NOTIFICATION);
                broadcaster.sendBroadcast(notificationIntent);

                intent = new Intent(this, PushBookingTulDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("path", "show_owner");
                intent.putExtra("push_id", messageBody.get("id"));

                ringNotification(intent, message, notificationId, messageBody.get("title"));
            } else if (messageBody.get("push_type").equalsIgnoreCase("14")) {

                if (messageBody.get("access_token").equals(utils.getString("access_token", ""))) {
                    intent = new Intent(this, ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("dialog_id", messageBody.get("chat_dialog_id"));
                    intent.putExtra("name", messageBody.get("username"));
                    intent.putExtra("other_block_status", "0");
                    intent.putExtra("my_block_status", "0");
                    intent.putExtra("from_push", "1");

                    if (utils.getInt("inside_chat", 0) == 0) {
                        ringNotification(intent, message, notificationId, messageBody.get("username"));
                    } else if (utils.getInt("inside_chat", 0) == 1) {
                        if (!messageBody.get("chat_dialog_id").equals(utils.getString("inside_dialog_id", ""))) {
                            silentNotification(intent, message, notificationId, messageBody.get("username"));
                        }
                    }
                }
            } else if (messageBody.get("push_type").equalsIgnoreCase("15")) {
                if (utils.getString("access_token", "").equals(messageBody.get("access_token"))) {
                    if (utils.getInt(Constants.ISEMAILSKIP, 0) == 1) {
                        ///email verified inside the app
                        utils.setInt(Constants.EMAIL_VERIFY, 1);
                        utils.setInt(Constants.ISEMAILSKIP, 0);

                        utils.setString("email", messageBody.get("email"));
                        utils.setString(Constants.UNVERIFIED_EMAIL, "");
                        intent = new Intent(this, LandingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ringNotification(intent, message, notificationId, messageBody.get("title"));

                        Intent notificationIntent = new Intent(Constants.EMAIL_VERIFY);
                        broadcaster.sendBroadcast(notificationIntent);

                    } else if (messageBody.get("type").equals("1")) {

                        utils.setString("email", messageBody.get("email"));
                        utils.setString(Constants.UNVERIFIED_EMAIL, "");

                        Intent notificationIntent = new Intent(Constants.EMAIL_VERIFY);
                        broadcaster.sendBroadcast(notificationIntent);

                    } else if (utils.getInt("inside_verify", 0) == 0) {
                        /// outside verify email activity
                        intent = new Intent(this, CreateProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ringNotification(intent, message, notificationId, messageBody.get("title"));
                    } else {
                        utils.setString("email", messageBody.get("email"));
                        utils.setString(Constants.UNVERIFIED_EMAIL, "");
                        /// inside verify email activity
                        Intent notificationIntent = new Intent(Constants.EMAIL_VERIFY);
                        broadcaster.sendBroadcast(notificationIntent);
                    }
                }
            } else if (messageBody.get("push_type").equalsIgnoreCase("16")) {
                /// broadcast push...
                if (utils.getInt("Background", 0) == 1) {
                    intent = new Intent(this, LandingActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("title", messageBody.get("b_title"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 16, messageBody.get("title"));
                } else {
                    intent = new Intent(this, BroadCastDialog.class);
                    intent.putExtra("message", message);
                    intent.putExtra("title", messageBody.get("b_title"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 16, messageBody.get("title"));
                }

            } else if (messageBody.get("push_type").equalsIgnoreCase("17")) {
                intent = new Intent(this, RateYourExperienceActivity.class);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));
                ringNotification(intent, message, 17, messageBody.get("title"));

            } else if (messageBody.get("push_type").equalsIgnoreCase("18")) {
                intent = new Intent(this, RateYourExperienceActivity.class);
                intent.putExtra("booking_id", messageBody.get("booking_id"));
                intent.putExtra("push_type", messageBody.get("push_type"));
                intent.putExtra("push_id", messageBody.get("id"));
                ringNotification(intent, message, 18, messageBody.get("title"));

            } else if (messageBody.get("push_type").equalsIgnoreCase("19")) {
                if (utils.getInt("Background", 0) == 1) {
                    intent = new Intent(this, AfterLoginSelectMode.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 19, messageBody.get("title"));
                } else {
                    intent = new Intent(this, LandingActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("title", messageBody.get("b_title"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 19, messageBody.get("title"));
                }
            } else if (messageBody.get("push_type").equalsIgnoreCase("20")) {
                if (utils.getInt("Background", 0) == 1) {
                    intent = new Intent(this, AfterLoginSelectMode.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 20, messageBody.get("title"));
                } else {
                    intent = new Intent(this, LandingActivity.class);
                    intent.putExtra("message", message);
                    intent.putExtra("title", messageBody.get("b_title"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ringNotification(intent, message, 20, messageBody.get("title"));
                }
            }
        }
    }

    void ringNotification(Intent intent, String mess, int notificationId, String title) {
        int mIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mIcon = R.mipmap.lolypop;
        else
            mIcon = R.mipmap.ic_launcher;

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(mIcon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(mess)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());

        if (utils.getInt("Background", 0) == 1) {
            int mBadgecount = utils.getInt("badge_count", 0);
            mBadgecount++;
            utils.setInt("badge_count", mBadgecount);
            ShortcutBadger.applyCount(getApplicationContext(), utils.getInt("badge_count", 0));
        }
    }


    void silentNotification(Intent intent, String mess, int notificationId, String title) {
        int mIcon;
        long pattern[] = {01};
        long pattern_vibrate[] = {0, 100, 200, 300, 400};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mIcon = R.mipmap.lolypop;

        else
            mIcon = R.mipmap.ic_launcher;

        long pattern_noti[] = null;
        if (utils.getInt("vibration", 0) == 1)
            pattern_noti = pattern_vibrate;
        else
            pattern_noti = pattern;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(mIcon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(mess)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setVibrate(pattern_noti)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());

        if (utils.getInt("Background", 0) == 1) {
            int mBadgecount = utils.getInt("badge_count", 0);
            mBadgecount++;
            utils.setInt("badge_count", mBadgecount);
            ShortcutBadger.applyCount(getApplicationContext(), utils.getInt("badge_count", 0));
        }
    }

}