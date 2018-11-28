package cn.cloudworkshop.shop.mvp.customerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.mvp.shoplist.ShopListActivity;
import cn.cloudworkshop.shop.ui.ShopActivity;
import cn.cloudworkshop.shop.utils.LoadingView;
import cn.cloudworkshop.shop.utils.SPUtils;

/**
 * Author：Libin on 2018/11/28 18:08
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomerListActivity extends BaseMvpActivity<CustomerListContract.Presenter> implements CustomerListContract.View {
    @BindView(R.id.rv_customer_list)
    RecyclerView rvCustomer;
    @BindView(R.id.sfr_customer)
    SmartRefreshLayout sfrCustomer;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    private int shopId;
    private int page = 1;
    //0:init, 1: refresh, 2:load
    private int type;

    private List<CustomerListBean.DataBean> dataList = new ArrayList<>();
    private CommonAdapter<CustomerListBean.DataBean> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        shopId = getIntent().getIntExtra("shop_id", 0);
    }

    private void initView() {
        mPresenter.initData(shopId, page, type);
        rvCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<CustomerListBean.DataBean>(this, R.layout.rv_shop_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, CustomerListBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_item, dataBean.getGuest_name());
            }

        };
        rvCustomer.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Intent intent = new Intent(ShopListActivity.this,ShopActivity.class);
//                intent.putExtra("shop_id",dataList.get(position).getGuest_id());
//                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        sfrCustomer.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                type = 2;
                mPresenter.initData(shopId, page, type);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                type = 1;
                mPresenter.initData(shopId, page, type);
            }
        });
    }

    @Override
    protected CustomerListContract.Presenter initPresenter() {
        return new CustomerListPresenter();
    }

    @Override
    public void hideLoading() {
        loadingView.setState(LoadingView.State.LOAD_DONE);
    }

    @Override
    public void finishRefresh() {
        sfrCustomer.finishRefresh();
    }

    @Override
    public void finishLoad() {
        sfrCustomer.finishLoadMore();
    }

    @Override
    public void loadError() {
        loadingView.setState(LoadingView.State.LOAD_ERROR);
    }

    @Override
    public void loadSuccess(List<CustomerListBean.DataBean> customerList) {
        if (type != 2) {
            dataList.clear();
        }
        dataList.addAll(customerList);
        adapter.notifyDataSetChanged();
    }
}
