package cn.cloudworkshop.shop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.cloudworkshop.shop.base.BaseBean;

/**
 * Author：Libin on 2018/11/28 18:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomerListBean extends BaseBean<List<CustomerListBean.DataBean>> {

    /**
     * pages : {"totalnum":2,"everypage":10,"totalpage":1,"page":1}
     * data : [{"guest_id":"117808941063602176","guest_gender":1,"guest_age":26,"recent_visit_imgurl":"http://qiniuimg1.meirenji.cn/217808936349204480.jpg","guest_mobile":"13357687895","guest_type":5,"guest_name":"Bill","recent_visit_at":"2018-11-21 13:14:07","company_id":1},{"guest_id":"217808941063602176","guest_gender":2,"guest_age":12,"recent_visit_imgurl":"http://qiniuimg1.meirenji.cn/217808936349204480.jpg","guest_mobile":"13357687073","guest_type":5,"guest_name":"123","recent_visit_at":"2018-11-21 13:14:07","company_id":1}]
     */
    private PagesBean pages;

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }


    public static class PagesBean {
        /**
         * totalnum : 2
         * everypage : 10
         * totalpage : 1
         * page : 1
         */

        private int totalnum;
        private int everypage;
        private int totalpage;
        private int page;

        public int getTotalnum() {
            return totalnum;
        }

        public void setTotalnum(int totalnum) {
            this.totalnum = totalnum;
        }

        public int getEverypage() {
            return everypage;
        }

        public void setEverypage(int everypage) {
            this.everypage = everypage;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public static class DataBean {
        /**
         * guest_id : 117808941063602176
         * guest_gender : 1
         * guest_age : 26
         * recent_visit_imgurl : http://qiniuimg1.meirenji.cn/217808936349204480.jpg
         * guest_mobile : 13357687895
         * guest_type : 5
         * guest_name : Bill
         * recent_visit_at : 2018-11-21 13:14:07
         * company_id : 1
         */

        private String guest_id;
        private int guest_gender;
        private int guest_age;
        private String recent_visit_imgurl;
        private String guest_mobile;
        private int guest_type;
        private String guest_name;
        private String recent_visit_at;
        private int company_id;

        public String getGuest_id() {
            return guest_id;
        }

        public void setGuest_id(String guest_id) {
            this.guest_id = guest_id;
        }

        public int getGuest_gender() {
            return guest_gender;
        }

        public void setGuest_gender(int guest_gender) {
            this.guest_gender = guest_gender;
        }

        public int getGuest_age() {
            return guest_age;
        }

        public void setGuest_age(int guest_age) {
            this.guest_age = guest_age;
        }

        public String getRecent_visit_imgurl() {
            return recent_visit_imgurl;
        }

        public void setRecent_visit_imgurl(String recent_visit_imgurl) {
            this.recent_visit_imgurl = recent_visit_imgurl;
        }

        public String getGuest_mobile() {
            return guest_mobile;
        }

        public void setGuest_mobile(String guest_mobile) {
            this.guest_mobile = guest_mobile;
        }

        public int getGuest_type() {
            return guest_type;
        }

        public void setGuest_type(int guest_type) {
            this.guest_type = guest_type;
        }

        public String getGuest_name() {
            return guest_name;
        }

        public void setGuest_name(String guest_name) {
            this.guest_name = guest_name;
        }

        public String getRecent_visit_at() {
            return recent_visit_at;
        }

        public void setRecent_visit_at(String recent_visit_at) {
            this.recent_visit_at = recent_visit_at;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }
    }
}
