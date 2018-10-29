package calenderViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.tul.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.DashboardDatesModel;
import utils.Constants;
import utils.Utils;

/**
 * Created by dev on 24/11/17.
 */

public class CalendarCustomView extends LinearLayout implements GridAdapter.EventsListInterface {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private static final int MAX_CALENDAR_COLUMN = 42;
    private Date date = null;
    private LinearLayout llMonth;
    private ImageView previousButton, nextButton;
    private TextView currentDate, tvEventdate;
    private RecyclerView calendarGridView;
    private RecyclerView rvEventsList;
    private LinearLayout llData;
    private ProgressBar pbLoad;
    private TextView txtNoBookings, txtAll, txtBooked, txtReturn;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private List<DashboardDatesModel.ResponseBean> listEvents = new ArrayList<>();
    private List<DashboardDatesModel.ResponseBean> partiuclarTypeEvents = new ArrayList<>();
    private EventsListAdapter adapter;
    private LinearLayout llAll, llBooking, llReturn;
    private Utils utils;
    private int mWidth, mHeight;
    List<Date> dayValueInCells = new ArrayList<Date>();
    ArrayList<DashboardDatesModel.ResponseBean> eventsDataArray = new ArrayList<>();
    Date mTodayDate=Calendar.getInstance().getTime();

    public CalendarCustomView(Context context) {
        super(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        utils = new Utils(context);
        mWidth = utils.getInt("width", 0);
        mHeight = utils.getInt("height", 0);
        initializeUILayout();
        setUpCalendarAdapter(Constants.TYPE, false);
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        setLlBookingClickEvent();
        setLlAllClickEvent();
        setLlReturnClickEvent();
        Log.d(TAG, "I need to call this method");
    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);

        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        previousButton.setPadding(mWidth / 28, mWidth / 28, mWidth / 28, mWidth / 28);

        nextButton = (ImageView) view.findViewById(R.id.next_month);
        nextButton.setPadding(mWidth / 28, mWidth / 28, mWidth / 28, mWidth / 28);

        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        currentDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        tvEventdate = (TextView) view.findViewById(R.id.tv_event_date);
        calendarGridView = (RecyclerView) view.findViewById(R.id.calendar_grid);
        rvEventsList = (RecyclerView) view.findViewById(R.id.rv_eventsList);

        llAll = (LinearLayout) view.findViewById(R.id.ll_all);
        llAll.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        llBooking = (LinearLayout) view.findViewById(R.id.ll_booking);
        llBooking.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        llReturn = (LinearLayout) view.findViewById(R.id.ll_return);
        llReturn.setPadding(mWidth / 32, mWidth / 64, mWidth / 32, mWidth / 64);

        llMonth = (LinearLayout) view.findViewById(R.id.ll_month);
        llMonth.setPadding(0, mHeight / 64, 0, mHeight / 64);

        pbLoad = (ProgressBar) view.findViewById(R.id.pb_load);

        llData = (LinearLayout) view.findViewById(R.id.ll_data);

        txtNoBookings = (TextView) view.findViewById(R.id.txt_no_bookings);
        txtNoBookings.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.05));
        txtNoBookings.setPadding(0, mHeight / 8, 0, mHeight / 16);

        txtAll = (TextView) view.findViewById(R.id.txt_all);
        txtAll.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        txtBooked = (TextView) view.findViewById(R.id.txt_booked);
        txtBooked.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        txtReturn = (TextView) view.findViewById(R.id.txt_return);
        txtReturn.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (mWidth * 0.04));

        rvEventsList.setLayoutManager(new LinearLayoutManager(context));
        rvEventsList.setNestedScrollingEnabled(true);
//        addEvents();
    }

    public void addEvents(ArrayList<DashboardDatesModel.ResponseBean> mDatesModels) {
        listEvents.clear();
        listEvents.addAll(mDatesModels);
        setUpCalendarAdapter(Constants.TYPE, true);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTodayDate.before(cal.getTime())) {
                    cal.add(Calendar.MONTH, -1);
                    setUpCalendarAdapter(Constants.TYPE, false);
                }
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter(Constants.TYPE, false);
            }
        });
    }

    private void setLlAllClickEvent() {
        llAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAll.setTypeface(Typeface.DEFAULT_BOLD);
                txtBooked.setTypeface(Typeface.DEFAULT);
                txtReturn.setTypeface(Typeface.DEFAULT);
                Constants.TYPE = 0;
                setUpCalendarAdapter(Constants.TYPE, false);
                setEventsData();
            }
        });
    }

    private void setLlBookingClickEvent() {
        llBooking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAll.setTypeface(Typeface.DEFAULT);
                txtBooked.setTypeface(Typeface.DEFAULT_BOLD);
                txtReturn.setTypeface(Typeface.DEFAULT);
                Constants.TYPE = 1;
                setUpCalendarAdapter(Constants.TYPE, false);
                setEventsData();
            }
        });
    }

    private void setLlReturnClickEvent() {
        llReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAll.setTypeface(Typeface.DEFAULT);
                txtBooked.setTypeface(Typeface.DEFAULT);
                txtReturn.setTypeface(Typeface.DEFAULT_BOLD);
                Constants.TYPE = 2;
                setUpCalendarAdapter(Constants.TYPE, false);
                setEventsData();
            }
        });
    }

    private void setGridCellClickEvents() {
       /* calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });*/
    }

    private void setUpCalendarAdapter(int type, boolean isApiHit) {

        dayValueInCells.clear();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 7);
        calendarGridView.setLayoutManager(layoutManager);
        calendarGridView.setHasFixedSize(true);
        calendarGridView.setItemAnimator(new DefaultItemAnimator());
        calendarGridView.setNestedScrollingEnabled(false);
        RecyclerView.ItemAnimator animator = calendarGridView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        if (type == 0) {
            mAdapter = new GridAdapter(context, dayValueInCells, cal, listEvents, this, isApiHit);
        } else if (type == 1) {
            partiuclarTypeEvents.clear();
            for (int i = 0; i < listEvents.size(); i++) {
                if (listEvents.get(i).getStatus() == 1) {
                    /// booking
                    partiuclarTypeEvents.add(listEvents.get(i));
                }
            }
            mAdapter = new GridAdapter(context, dayValueInCells, cal, partiuclarTypeEvents, this, isApiHit);
        } else if (type == 2) {
            partiuclarTypeEvents.clear();
            for (int i = 0; i < listEvents.size(); i++) {
                if (listEvents.get(i).getStatus() == 2) {
                    /// return
                    partiuclarTypeEvents.add(listEvents.get(i));
                }
            }
            mAdapter = new GridAdapter(context, dayValueInCells, cal, partiuclarTypeEvents, this, isApiHit);
        }
        calendarGridView.setAdapter(mAdapter);
    }

    @Override
    public void getEventsList(List<DashboardDatesModel.ResponseBean> listEvents, String inputDate) {
        pbLoad.setVisibility(GONE);
        llData.setVisibility(VISIBLE);
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM", Locale.US);
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            Date date = parseFormat.parse(inputDate);
            tvEventdate.setText(displayFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventsDataArray.clear();
        eventsDataArray.addAll(listEvents);
        setEventsData();
    }

    private void setEventsData() {
        if (eventsDataArray.size() > 0) {
            ArrayList<DashboardDatesModel.ResponseBean> tempArray = new ArrayList<>();
            if (Constants.TYPE == 1) {
                for (DashboardDatesModel.ResponseBean responseBean : eventsDataArray) {
                    if (responseBean.getStatus() == 1)
                        tempArray.add(responseBean);
                }
            } else if (Constants.TYPE == 2) {
                for (DashboardDatesModel.ResponseBean responseBean : eventsDataArray) {
                    if (responseBean.getStatus() == 2)
                        tempArray.add(responseBean);
                }
            } else
                tempArray.addAll(eventsDataArray);
            if (tempArray.size() > 0) {
                txtNoBookings.setVisibility(GONE);
                adapter = new EventsListAdapter(context, tempArray);
                rvEventsList.setAdapter(adapter);
            } else {
                rvEventsList.setAdapter(null);
                txtNoBookings.setVisibility(VISIBLE);
            }
        } else {
            rvEventsList.setAdapter(null);
            txtNoBookings.setVisibility(VISIBLE);
        }
    }

    @Override
    public void notifyAdapter() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        txtNoBookings.setVisibility(GONE);
        pbLoad.setVisibility(VISIBLE);
        llData.setVisibility(INVISIBLE);
    }

}