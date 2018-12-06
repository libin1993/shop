package cn.cloudworkshop.shop.bean;

import java.util.List;

import cn.cloudworkshop.shop.base.BaseBean;

/**
 * Author：Libin on 2018/12/5 10:33
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuestCountBean extends BaseBean<GuestCountBean.DataBean> {


    public static class DataBean {
        private List<GuestFlowTrendBean> guest_flow_trend;
        private List<GuestAgeStatisticsBean> guest_age_statistics;
        private List<GuestGenderStatisticsBean> guest_gender_statistics;
        private List<GuestArrivalTimesStatisticsBean> guest_arrival_times_statistics;

        public List<GuestFlowTrendBean> getGuest_flow_trend() {
            return guest_flow_trend;
        }

        public void setGuest_flow_trend(List<GuestFlowTrendBean> guest_flow_trend) {
            this.guest_flow_trend = guest_flow_trend;
        }

        public List<GuestAgeStatisticsBean> getGuest_age_statistics() {
            return guest_age_statistics;
        }

        public void setGuest_age_statistics(List<GuestAgeStatisticsBean> guest_age_statistics) {
            this.guest_age_statistics = guest_age_statistics;
        }

        public List<GuestGenderStatisticsBean> getGuest_gender_statistics() {
            return guest_gender_statistics;
        }

        public void setGuest_gender_statistics(List<GuestGenderStatisticsBean> guest_gender_statistics) {
            this.guest_gender_statistics = guest_gender_statistics;
        }

        public List<GuestArrivalTimesStatisticsBean> getGuest_arrival_times_statistics() {
            return guest_arrival_times_statistics;
        }

        public void setGuest_arrival_times_statistics(List<GuestArrivalTimesStatisticsBean> guest_arrival_times_statistics) {
            this.guest_arrival_times_statistics = guest_arrival_times_statistics;
        }

        public static class GuestFlowTrendBean {
            /**
             * date : 2018-11-05
             * cnt : 0
             */

            private String date;
            private int cnt;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }
        }

        public static class GuestAgeStatisticsBean {
            /**
             * age_group : 17岁以下
             * cnt : 0
             */

            private String age_group;
            private int cnt;

            public String getAge_group() {
                return age_group;
            }

            public void setAge_group(String age_group) {
                this.age_group = age_group;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }
        }

        public static class GuestGenderStatisticsBean {
            /**
             * gender : 男性
             * cnt : 0
             */

            private String gender;
            private int cnt;

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }
        }

        public static class GuestArrivalTimesStatisticsBean {
            /**
             * type : 1次
             * cnt : 0
             */

            private String type;
            private int cnt;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }
        }
    }
}
