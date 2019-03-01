package cn.cloudworkshop.shop.mvp.gueststatistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.GuestCountBean;
import cn.cloudworkshop.shop.utils.DateUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Author：Libin on 2018/12/5 10:28
 * Email：1993911441@qq.com
 * Describe：客流统计
 */
public class GuestStatisticsActivity extends BaseMvpActivity<GuestStatisticsContract.Presenter> implements GuestStatisticsContract.View {
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.chart_guest)
    LineChart chartGuest;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    private int shopId;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_statistics);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(getString(R.string.flow_statistics));
        endTime = System.currentTimeMillis();
        startTime = endTime - 7L * 24L * 60L * 60L * 1000L;
        tvStartTime.setText(DateUtils.getDate("yyyy-MM-dd", startTime));
        tvEndTime.setText(DateUtils.getDate("yyyy-MM-dd", endTime));
        loadingView.setState(LoadingView.State.LOADING);
        loadData();

        Description description = new Description();
        description.setEnabled(false);
        chartGuest.setDescription(description);

        YAxis rightYAxis = chartGuest.getAxisRight();
        rightYAxis.setEnabled(false);

        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                loadData();
            }
        });


    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", SPUtils.getStr(this, "token"));
        map.put("company_id", shopId);
        map.put("chart_types", "guest_flow_trend");
        map.put("s_time", DateUtils.getDate("yyyy-MM-dd", startTime));
        map.put("e_time", DateUtils.getDate("yyyy-MM-dd", endTime));
        mPresenter.initData(map);
    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }


    @Override
    protected GuestStatisticsContract.Presenter initPresenter() {
        return new GuestStatisticsPresenter();
    }

    @Override
    public void loadSuccess(List<GuestCountBean.DataBean.GuestFlowTrendBean> guestList) {
        //设置数据
        List<Entry> entries = new ArrayList<>();
        final List<String> list = new ArrayList<>();

        for (int i = 0; i < guestList.size(); i++) {
            entries.add(new Entry(i,  guestList.get(i).getCnt()));
            list.add(guestList.get(i).getDate());
        }

        XAxis xAxis = chartGuest.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(90);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index >= list.size()) {
                    return "";
                } else {
                    return list.get(index);
                }
            }
        });

        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "客流");
        //格式化显示数据
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return new DecimalFormat("###,###,##0").format(value);
            }
        });
        LineData data = new LineData(lineDataSet);
        chartGuest.setData(data);
        chartGuest.notifyDataSetChanged();
        chartGuest.invalidate();

    }

    @Override
    public void loadFail(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }

    @OnClick({R.id.iv_header_back, R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.tv_start_time:

                selectDate(1, endTime - 60L * 24L * 60L * 60L * 1000L, endTime);
                break;
            case R.id.tv_end_time:
                long end;
                if (startTime + 60L * 24L * 60L * 60L * 1000L >= System.currentTimeMillis()) {
                    end = System.currentTimeMillis();
                } else {
                    end = startTime + 60L * 24L * 60L * 60L * 1000L;
                }
                selectDate(2, startTime, end);
                break;
        }
    }

    /**
     * 选择日期
     */
    private void selectDate(final int type, final long start, long end) {
        DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setCycleDisable(true);
        picker.setTextSize(14);
        picker.setTopLineColor(ContextCompat.getColor(this, R.color.gray_15));
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setDividerColor(ContextCompat.getColor(this, R.color.gray_ed));
        picker.setTextColor(ContextCompat.getColor(this, R.color.gray_22), ContextCompat.getColor(this, R.color.gray_6a));

        picker.setLabelTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setContentPadding(20, 0);
        picker.setRangeStart(DateUtils.getYear(start), DateUtils.getMonth(start), DateUtils.getDay(start));
        picker.setRangeEnd(DateUtils.getYear(end), DateUtils.getMonth(end), DateUtils.getDay(end));

        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (type == 1) {
                    startTime = DateUtils.getTime("yyyyMMdd", year + month + day);
                    tvStartTime.setText(DateUtils.getDate("yyyy-MM-dd", startTime));
                } else {
                    endTime = DateUtils.getTime("yyyyMMdd", year + month + day);
                    tvEndTime.setText(DateUtils.getDate("yyyy-MM-dd", endTime));
                }
                loadingView.setState(LoadingView.State.LOADING);
                loadData();
            }
        });

        picker.show();
    }
}
