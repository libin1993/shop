package cn.cloudworkshop.shop.mvp.shoplist;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.shop.R;
import cn.cloudworkshop.shop.application.MyApp;
import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.base.BaseMvpActivity;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.bean.VersionBean;
import cn.cloudworkshop.shop.service.DownloadService;
import cn.cloudworkshop.shop.ui.ShopActivity;
import cn.cloudworkshop.shop.utils.AppManagerUtils;
import cn.cloudworkshop.shop.utils.SPUtils;
import cn.cloudworkshop.shop.utils.ToastUtils;
import cn.cloudworkshop.shop.view.LoadingView;
import cn.cloudworkshop.shop.view.PermissionDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ShopListActivity extends BaseMvpActivity<ShopListContract.Presenter> implements ShopListContract.View, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.iv_header_back)
    ImageView ivHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.view_header_line)
    View viewHeaderLine;

    private List<ShopListBean.DataBean> dataList = new ArrayList<>();
    private CommonAdapter<ShopListBean.DataBean> adapter;

    //读写权限
    String[] permissionStr = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //退出应用
    private long exitTime = 0;
    private DownloadService downloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        requestPermission();
        tvHeaderTitle.setText(R.string.shop_list);
        ivHeaderBack.setVisibility(View.GONE);
        viewHeaderLine.setVisibility(View.VISIBLE);
        loadingView.setState(LoadingView.State.LOADING);

        mPresenter.initData(SPUtils.getStr(this, "token"));
        rvShopList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<ShopListBean.DataBean>(this, R.layout.rv_shop_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, ShopListBean.DataBean dataBean, int position) {
                holder.setText(R.id.tv_name_shop, dataBean.getName());
                holder.setText(R.id.tv_info_shop, dataBean.getInfo());
                holder.setText(R.id.tv_address_shop, dataBean.getAddress());
            }
        };
        rvShopList.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(ShopListActivity.this, ShopActivity.class);
                intent.putExtra("shop_id", dataList.get(position).getId());
                intent.putExtra("shop_name", dataList.get(position).getName());
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
                loadingView.setState(LoadingView.State.LOADING);
                mPresenter.initData(SPUtils.getStr(ShopListActivity.this, "token"));
            }
        });

        mPresenter.checkUpdate();
        mPresenter.uploadCid(SPUtils.getStr(this, "token"), SPUtils.getStr(this, "client_id"));
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        if (!EasyPermissions.hasPermissions(this, permissionStr)) {
            EasyPermissions.requestPermissions(this, "", 1234, permissionStr);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 1234 || requestCode == 1235) {
            PermissionDialog.showPermissionDialog(this, getString(R.string.read_and_write));
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return false;
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

    @Override
    public void newVersion(final VersionBean.DataBean data) {
        if (data.getVersion_id() > getVersionCode()) {
            if (EasyPermissions.hasPermissions(ShopListActivity.this, permissionStr)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ShopListActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.check_new_version);
                dialog.setMessage(data.getDescription());
                //确定
                dialog.setPositiveButton(R.string.update_immediately, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadFile(data.getLink());
                    }
                });

                dialog.create();
                dialog.show();
            } else {
                EasyPermissions.requestPermissions(this, "", 1235, permissionStr);
            }
        }
    }


    /**
     * 下载文件
     */
    private void downloadFile(String url) {
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(getString(R.string.app_name));
        request.setDescription(getString(R.string.downloading));
        // 设置下载可见
        request.setVisibleInDownloadsUi(true);
        //下载完成后通知栏可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //当前网络状态
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (mConnectivityManager != null) {
            networkInfo = mConnectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("MOBILE")) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            }
        }

        // 设置下载位置
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "shop", "shop.apk");
        if (file.exists()) {
            file.delete();
        }
        request.setDestinationUri(Uri.fromFile(file));


        downloadService = new DownloadService(file);
        registerReceiver(downloadService, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (manager != null) {
            manager.enqueue(request);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                AppManagerUtils.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 获取版本号
     */

    private int getVersionCode() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi != null ? pi.versionCode : 0;
    }

    @Override
    protected void onDestroy() {
        if (downloadService != null) {
            unregisterReceiver(downloadService);
        }
        super.onDestroy();
    }

}
