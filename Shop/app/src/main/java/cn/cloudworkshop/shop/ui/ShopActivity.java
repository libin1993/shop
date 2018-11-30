package cn.cloudworkshop.shop.ui;

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
import cn.cloudworkshop.shop.mvp.customerlist.CustomerListActivity;

/**
 * Author：Libin on 2018/11/28 17:45
 * Email：1993911441@qq.com
 * Describe：
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

    @OnClick({R.id.iv_header_back, R.id.cv_shop_customer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.cv_shop_customer:
                Intent intent = new Intent(ShopActivity.this, CustomerListActivity.class);
                intent.putExtra("shop_id", shopId);
                startActivity(intent);
                break;
        }
    }
}
