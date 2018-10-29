package calenderViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tul.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import api.RetrofitClient;
import model.DashboardDatesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 24/11/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.TaskViewHolder> {
    private static final String TAG = GridAdapter.class.getSimpleName();
    EventsListInterface eventsListInterface;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private Context context;
    private List<DashboardDatesModel.ResponseBean> allEvents;
    private int lastCheckedPosition = -1;
    boolean isApiHit;
    Utils utils;
    DateFormat dateFormat;
    boolean hitOnlyOnce = true;
    private Date mTodayDate;
    Calendar calendar;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<DashboardDatesModel.ResponseBean> allEvents, EventsListInterface eventsListInterface
            , boolean isApiHit) {
        this.context = context;
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.eventsListInterface = eventsListInterface;
        this.isApiHit = isApiHit;
        utils = new Utils(context);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mTodayDate = calendar.getTime();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_cell_layout, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        Date mDate = monthlyDates.get(position);
        initializeViews(mDate, holder, position);
    }

    public void initializeViews(final Date mDate, final TaskViewHolder holder, final int position) {
        final Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        final int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        final int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        final int displayYear = dateCal.get(Calendar.YEAR);
        final int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        final int currentYear = currentDate.get(Calendar.YEAR);

        if (isApiHit) {
            if (Constants.CURRENT_DATE == null || Constants.CURRENT_DATE.equals("")) {
                Constants.CURRENT_DATE = dateFormat.format(new Date());
            }
            if (hitOnlyOnce) {
                eventsListInterface.showProgress();
                hitParticularDateAPI(Constants.CURRENT_DATE);
                hitOnlyOnce = false;
            }
        }

        if (dateFormat.format(mDate).equals(Constants.CURRENT_DATE)) {
            holder.cellNumber.setTextColor(Color.WHITE);
        } else {
            holder.cellNumber.setTextColor(Color.GRAY);
        }

        /** to make last and next month dates invisible **/
        if (displayMonth == currentMonth && displayYear == currentYear) {
            holder.llDateView.setVisibility(View.VISIBLE);
            holder.cellNumber.setText(String.valueOf(dayValue));
        } else {
            holder.llDateView.setVisibility(View.GONE);
        }


        holder.llDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTodayDate.equals(monthlyDates.get(position)) || monthlyDates.get(position).after(mTodayDate)) {
                    eventsListInterface.notifyAdapter();
                    Constants.CURRENT_DATE = dateFormat.format(mDate);
                    hitOnlyOnce = true;
                    isApiHit = true;
                    notifyDataSetChanged();
                }
            }
        });

        Calendar eventCalendar = Calendar.getInstance();
        for (int i = 0; i < allEvents.size(); i++) {
            try {

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = format.parse(allEvents.get(i).getDate());
                    eventCalendar.setTime(date);
                } catch (ParseException e) {
                    Log.e("Adapter Date Exc = ", e.getLocalizedMessage());
                }

                if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR) && (displayMonth == currentMonth && displayYear == currentYear)) {
                    View addView = LayoutInflater.from(context).inflate(R.layout.event_add_symbol, null);
                    View view1 = (View) addView.findViewById(R.id.view_one);

                    if (holder.llViewContainer.getChildCount() < 3) {
                        if (allEvents.get(i).getStatus() == 1) {
                            /// booking
                            view1.setBackgroundColor(ContextCompat.getColor(context, R.color.red_color));
                        } else if (allEvents.get(i).getStatus() == 2) {
                            /// return
                            view1.setBackgroundColor(ContextCompat.getColor(context, R.color.return_color));
                        }
                        holder.llViewContainer.addView(addView);
                    }
                } else {

                }
            } catch (Exception e) {
                System.out.println("Exception isss; " + e.toString());
            }
        }
    }

    private void hitParticularDateAPI(final String selectedDate) {
        Call<DashboardDatesModel> call = RetrofitClient.getInstance()
                .getParticularDateData(utils.getString("access_token", ""), selectedDate);
        call.enqueue(new Callback<DashboardDatesModel>() {
            @Override
            public void onResponse(Call<DashboardDatesModel> call, Response<DashboardDatesModel> response) {
                if (response.body().getResponse() != null)
                    eventsListInterface.getEventsList(response.body().getResponse(), selectedDate);
                else {
                    if (response.body().error.getCode().equals("420")) {
                        Constants.moveToSplash(context, utils);
                    } else {
                        Toast.makeText(context, response.body().error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardDatesModel> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return monthlyDates.size();
    }

    public interface EventsListInterface {

        void getEventsList(List<DashboardDatesModel.ResponseBean> listEvents, String eventDate);

        void notifyAdapter();

        void showProgress();

    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView cellNumber;
        LinearLayout llViewContainer, llDateView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            cellNumber = (TextView) itemView.findViewById(R.id.calendar_date_id);
            llViewContainer = (LinearLayout) itemView.findViewById(R.id.ll_container);
            llDateView = (LinearLayout) itemView.findViewById(R.id.ll_dateView);
        }
    }
}