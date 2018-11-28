package cn.cloudworkshop.shop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import cn.cloudworkshop.shop.base.BaseBean;

/**
 * Author：Libin on 2018/11/28 16:33
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShopListBean extends BaseBean<List<ShopListBean.DataBean>> {

    public static class DataBean {
        /**
         * id : 1
         * parent_id : 0
         * name : 云工场
         * info : 杭州云工场科技有限公司
         * address : 星城发展大厦
         * phone : 1234
         * status : 0
         * create_time : 2018-11-14 10:02:25
         * update_time : 2018-11-14 10:02:25
         */

        private int id;
        private int parent_id;
        private String name;
        private String info;
        private String address;
        private String phone;
        private int status;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
