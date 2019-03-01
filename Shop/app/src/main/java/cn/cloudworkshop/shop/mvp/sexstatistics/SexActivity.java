package cn.cloudworkshop.shop.mvp.sexstatistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

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
 * Author：Libin on 2018/12/24 15:06
 * Email：1993911441@qq.com
 * Describe：
 */
public class SexActivity extends BaseMvpActivity<SexStatisticsContact.Presenter> implements SexStatisticsContact.View {
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_sex_start)
    TextView tvStartTime;
    @BindView(R.id.tv_sex_end)
    TextView tvEndTime;
    @BindView(R.id.chart_sex)
    PieChart chartSex;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private int shopId;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_statistics);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(getString(R.string.sex_statistics));
        endTime = System.currentTimeMillis();
        startTime = endTime - 7L * 24L * 60L * 60L * 1000L;
        tvStartTime.setText(DateUtils.getDate("yyyy-MM-dd", startTime));
        tvEndTime.setText(DateUtils.getDate("yyyy-MM-dd", endTime));
        loadingView.setState(LoadingView.State.LOADING);

        chartSex.setUsePercentValues(true);//设置使用百分比（后续有详细介绍）
        chartSex.getDescription().setEnabled(true);//设置描述
//        chartSex.setExtraOffsets(25, 10, 25, 25); //设置边距
        chartSex.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        chartSex.setRotationEnabled(true);//是否可以旋转
        chartSex.setHighlightPerTapEnabled(true);//点击是否放大
        chartSex.setRotationAngle(120f);//设置旋转角度
        //这个方法为true就是环形图，为false就是饼图
        chartSex.setDrawHoleEnabled(false);
        //设置环形中间空白颜色是白色
        chartSex.setHoleColor(Color.WHITE);
        Description description = new Description();
        description.setEnabled(false);
        chartSex.setDescription(description);


        loadData();

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
        map.put("chart_types", "guest_gender_statistics");
        map.put("s_time", DateUtils.getDate("yyyy-MM-dd", startTime));
        map.put("e_time", DateUtils.getDate("yyyy-MM-dd", endTime));
        mPresenter.initData(map);
    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }


    @Override
    protected SexStatisticsContact.Presenter initPresenter() {
        return new SexStatisticsPresenter();
    }

    @Override
    public void loadSuccess(List<GuestCountBean.DataBean.GuestGenderStatisticsBean> genderList) {


        List<PieEntry> entries = new ArrayList<>();
        int[] colors = {Color.BLUE, Color.RED};
        for (int i = 0; i < genderList.size(); i++) {
            entries.add(new PieEntry(genderList.get(i).getCnt(),
                    genderList.get(i).getGender() + genderList.get(i).getCnt()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        dataSet.setColors(colors);//设置饼块的颜色

        //设置数据显示方式有见图
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueLineColor(Color.YELLOW);//设置连接线的颜色
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(R.color.gray_22);

        chartSex.setData(pieData);
        chartSex.highlightValues(null);
        chartSex.invalidate();

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

    @OnClick({R.id.iv_header_back, R.id.tv_sex_start, R.id.tv_sex_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.tv_sex_start:
                selectDate(1, endTime - 60L * 24L * 60L * 60L * 1000L, endTime);
                break;
            case R.id.tv_sex_end:
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
        picker.setTextColor(ContextCompat.getColor(this, R.color.gray_22),
                ContextCompat.getColor(this, R.color.gray_6a));

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




