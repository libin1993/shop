package cn.cloudworkshop.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseActivity;
import cn.cloudworkshop.shop.mvp.customerlist.CustomerListActivity;
import cn.cloudworkshop.shop.mvp.shoplist.ShopListActivity;

/**
 * Author：Libin on 2018/11/28 17:45
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShopActivity extends BaseActivity {
    @BindView(R.id.tv_shop_data)
    TextView tvShopData;
    private int shopId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        getData();

    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }

    @OnClick(R.id.tv_shop_data)
    public void onViewClicked() {
        Intent intent = new Intent(ShopActivity.this, CustomerListActivity.class);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }
}
