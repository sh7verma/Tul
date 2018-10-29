package calenderViews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tul.BookingTulDetailActivity;
import com.app.tul.ListingTulDetailActivity;
import com.app.tul.R;

import java.util.ArrayList;
import java.util.List;

import model.DashboardDatesModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 24/11/17.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.TaskViewHolder> {
    List<DashboardDatesModel.ResponseBean> eventsList = new ArrayList<>();
    private Context context;
    private int mHeight;
    private int mWidth;
    private Utils util;

    public EventsListAdapter(Context context, List<DashboardDatesModel.ResponseBean> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
        util = new Utils(context);
        mHeight = util.getInt("height", 0);
        mWidth = util.getInt("width", 0);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_list, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {

        final DashboardDatesModel.ResponseBean obj = eventsList.get(position);

        holder.tvEventDesc.setText(obj.getTitle());

        if (obj.getTime().contains("(NEXT DAY)"))
            holder.tvEventTime.setText(obj.getTime().replace("(NEXT DAY)", ""));
        else if (obj.getTime().contains("(Next Day)"))
            holder.tvEventTime.setText(obj.getTime().replace("(Next Day)", ""));
        else
            holder.tvEventTime.setText(obj.getTime());

        if (obj.getStatus() == 1)
            holder.tvEventType.setText("BOOKING");
        else if (obj.getStatus() == 2)
            holder.tvEventType.setText("RETURN");

        if (obj.getStatus() == 1) {
            holder.viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.red_color));
        } else {
            holder.viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.return_color));
        }

        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingTulDetailActivity.class);
                intent.putExtra("calendar_tul_id", obj.getBooking_id());
                intent.putExtra("delivery_date", obj.getDelivery_date());
                intent.putExtra("return_date", obj.getReturn_date());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventDesc, tvEventType, tvEventTime;
        RelativeLayout rlClick;
        View viewLine;

        public TaskViewHolder(View itemView) {
            super(itemView);

            rlClick = (RelativeLayout) itemView.findViewById(R.id.rl_click);

            tvEventType = (TextView) itemView.findViewById(R.id.tv_eventType);
            tvEventType.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (util.getInt("width", 0) * 0.04));
            tvEventType.setPadding(0, 0, mWidth / 32, 0);

            tvEventDesc = (TextView) itemView.findViewById(R.id.tv_eventDesc);
            tvEventDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (util.getInt("width", 0) * 0.04));
            tvEventDesc.setPadding(0, mWidth / 32, 0, 0);

            tvEventTime = (TextView) itemView.findViewById(R.id.tv_event_time);
            tvEventTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (util.getInt("width", 0) * 0.04));
            tvEventTime.setPadding(0, mWidth / 84, 0, 0);

            viewLine = (View) itemView.findViewById(R.id.view_line);
        }
    }
}