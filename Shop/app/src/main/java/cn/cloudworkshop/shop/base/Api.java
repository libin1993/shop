package cn.cloudworkshop.shop.base;


import java.util.Map;

import cn.cloudworkshop.shop.base.BaseBean;
import cn.cloudworkshop.shop.bean.CustomerListBean;
import cn.cloudworkshop.shop.bean.GuestCountBean;
import cn.cloudworkshop.shop.bean.GuestRecordBean;
import cn.cloudworkshop.shop.bean.ShopListBean;
import cn.cloudworkshop.shop.bean.VersionBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author：Libin on 2018/10/11 09:34
 * Email：1993911441@qq.com
 * Describe：
 */
public interface Api {
    //    String HOST = "http://192.168.1.97/";
    String HOST = "http://api.cloudworkshop.cn/";

    @FormUrlEncoded
    @POST("index.php/mrjapp/login/login_on")
    Observable<BaseBean> login(@Field("account_name") String username, @Field("account_password") String pwd, @Field("andriod_cid") String cid);

    @FormUrlEncoded
    @POST("index.php/mrjapp/login/isvalid_token")
    Observable<BaseBean> isLogin(@Field("token") String token);

    @FormUrlEncoded
    @POST("index.php/mrjapp/shop/index")
    Observable<ShopListBean> shopList(@Field("token") String token);

    @FormUrlEncoded
    @POST("index.php/mrjapp/mrj/get_visit_users")
    Observable<CustomerListBean> customerList(@Field("token") String token, @Field("company_id") int shopId, @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/mrjapp/mrj/get_user_history")
    Observable<GuestRecordBean> guestRecord(@Field("token") String token, @Field("guest_id") String guestId, @Field("page") int page);

    @FormUrlEncoded
    @POST("index.php/mrjapp/mrj/edit_user")
    Observable<BaseBean> alterCustomer(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/index.php/mrjapp/mrj/chart_data")
    Observable<GuestCountBean> guestCount(@FieldMap Map<String, Object> map);

    @GET("/index.php/mrjapp/version/get_latest_version")
    Observable<VersionBean> checkUpdate();
}



