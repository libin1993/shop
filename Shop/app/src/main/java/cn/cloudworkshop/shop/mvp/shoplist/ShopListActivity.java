package cn.cloudworkshop.shop.mvp.shoplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.ui.ShopActivity;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;
import cn.cloudworkshop.shop.utils.SPUtils;

public class ShopListActivity extends BaseMvpActivity<ShopListContract.Presenter> implements ShopListContract.View {

    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private List<ShopListBean.DataBean> dataList = new ArrayList<>();
    private CommonAdapter<ShopListBean.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        loadingView.setState(LoadingView.State.LOADING);

        mPresenter.initData(SPUtils.getStr(this, "token"));
        rvShopList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<ShopListBean.DataBean>(this, R.layout.rv_shop_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, ShopListBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_item, dataBean.getName());
            }
        };
        rvShopList.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(ShopListActivity.this, ShopActivity.class);
                intent.putExtra("shop_id", dataList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.initData(SPUtils.getStr(ShopListActivity.this, "token"));
            }
        });
    }

    @Override
    protected ShopListContract.Presenter initPresenter() {
        return new ShopListPresenter();
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }

    @Override
    public void loadSuccess(List<ShopListBean.DataBean> shopList) {

        dataList.addAll(shopList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg) {
        ToastUtils.showToast(ShopListActivity.this, msg);
    }

}
