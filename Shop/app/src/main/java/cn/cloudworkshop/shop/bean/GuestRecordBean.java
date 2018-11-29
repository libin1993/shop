package cn.cloudworkshop.shop.bean;

import java.util.List;

import cn.cloudworkshop.shop.base.BaseBean;

/**
 * Author：Libin on 2018/11/29 15:58
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestRecordBean extends BaseBean<List<GuestRecordBean.DataBean>> {

    /**
     * code : 10000
     * pages : {"totalnum":21,"everypage":10,"totalpage":3,"page":1}
     * data : [{"recent_visit_at":"2018-11-21 13:14:07","recent_visit_imgurl":"http://qiniuimg1.meirenji.cn/217808936349204480.jpg"},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":"http://qiniuimg1.meirenji.cn/217808936349204480.jpg"},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":"http://qiniuimg1.meirenji.cn/217808936349204480.jpg"},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null},{"recent_visit_at":"2018-11-20 10:47:07","recent_visit_imgurl":null}]
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
         * totalnum : 21
         * everypage : 10
         * totalpage : 3
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
         * recent_visit_at : 2018-11-21 13:14:07
         * recent_visit_imgurl : http://qiniuimg1.meirenji.cn/217808936349204480.jpg
         */

        private String recent_visit_at;
        private String recent_visit_imgurl;

        public String getRecent_visit_at() {
            return recent_visit_at;
        }

        public void setRecent_visit_at(String recent_visit_at) {
            this.recent_visit_at = recent_visit_at;
        }

        public String getRecent_visit_imgurl() {
            return recent_visit_imgurl;
        }

        public void setRecent_visit_imgurl(String recent_visit_imgurl) {
            this.recent_visit_imgurl = recent_visit_imgurl;
        }
    }
}
