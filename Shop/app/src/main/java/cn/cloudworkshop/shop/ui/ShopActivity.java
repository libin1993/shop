package cn.cloudworkshop.shop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseActivity;
import cn.cloudworkshop.shop.mvp.agestatistics.AgeStatisticsActivity;
import cn.cloudworkshop.shop.mvp.customerlist.CustomerListActivity;
import cn.cloudworkshop.shop.mvp.gueststatistics.GuestStatisticsActivity;
import cn.cloudworkshop.shop.mvp.login.LoginActivity;
import cn.cloudworkshop.shop.mvp.sexstatistics.SexActivity;
import cn.cloudworkshop.shop.mvp.sexstatistics.SexStatisticsActivity;
import cn.cloudworkshop.shop.mvp.timesstatistics.TimesStatisticsActivity;
import cn.cloudworkshop.shop.utils.SPUtils;

/**
 * Author：Libin on 2018/11/28 17:45
 * Email：1993911441@qq.com
 * Describe：门店
 */
public class ShopActivity extends BaseActivity {
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;
    @BindView(R.id.cv_shop_customer)
    CardView cvShopCustomer;
    @BindView(R.id.cv_shop_data)
    CardView cvShopData;
    @BindView(R.id.cv_age)
    CardView cvAge;
    @BindView(R.id.cv_sex)
    CardView cvSex;
    @BindView(R.id.cv_times)
    CardView cvTimes;
    @BindView(R.id.tv_log_out)
    TextView tvLogOut;
    private int shopId;
    private String shopName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void initView() {
        tvHeaderTitle.setText(shopName);
        viewHeaderLine.setVisibility(View.VISIBLE);
    }

    private void getData() {
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", 0);
        shopName = intent.getStringExtra("shop_name");
    }

    @OnClick({R.id.iv_header_back, R.id.cv_shop_customer, R.id.cv_shop_data, R.id.cv_age, R.id.cv_sex,
            R.id.cv_times, R.id.tv_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.cv_shop_customer:
                toActivity(CustomerListActivity.class);
                break;
            case R.id.cv_shop_data:
                toActivity(GuestStatisticsActivity.class);
                break;
            case R.id.cv_age:
                toActivity(AgeStatisticsActivity.class);
                break;
            case R.id.cv_sex:
                toActivity(SexActivity.class);
                break;
            case R.id.cv_times:
                toActivity(TimesStatisticsActivity.class);
                break;
            case R.id.tv_log_out:
                SPUtils.deleteStr(this, "token");
                Intent logout = new Intent(ShopActivity.this, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logout);
                break;
        }
    }

    private void toActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }
}


