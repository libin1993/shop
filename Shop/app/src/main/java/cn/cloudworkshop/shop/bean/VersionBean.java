package cn.cloudworkshop.shop.bean;

import cn.cloudworkshop.shop.base.BaseBean;

/**
 * Author：Libin on 2018/12/6 10:30
 * Email：1993911441@qq.com
 * Describe：
 */
public class VersionBean extends BaseBean<VersionBean.DataBean> {

    public static class DataBean {
        /**
         * version_id : 1
         * version_name : version 1.0.0
         * description : 第一版
         */

        private int version_id;
        private String version_name;
        private String description;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getVersion_id() {
            return version_id;
        }

        public void setVersion_id(int version_id) {
            this.version_id = version_id;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
