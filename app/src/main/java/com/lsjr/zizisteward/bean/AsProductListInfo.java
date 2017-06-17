package com.lsjr.zizisteward.bean;

import java.io.Serializable;
import java.util.List;

public class AsProductListInfo {

    private List<JiangPinListInfo> products;
    private List<BannerInfo> banner;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<JiangPinListInfo> getProducts() {
        return products;
    }

    public void setProducts(List<JiangPinListInfo> products) {
        this.products = products;
    }

    @SuppressWarnings("serial")
    public class BannerInfo implements Serializable {

        private String id;
        private String image_filename;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage_filename() {
            return image_filename;
        }

        public void setImage_filename(String image_filename) {
            this.image_filename = image_filename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class JiangPinListInfo {

        private String audit;
        private String bname;
        private String brand_type;
        private String entityId;
        private String id;
        private String persistent;
        private String putaway;
        private String sbrand;
        private String sell_points;
        private String sisrec;
        private String skeyword;
        private String sname;
        private String spic;
        private ShareTime stime;

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getBrand_type() {
            return brand_type;
        }

        public void setBrand_type(String brand_type) {
            this.brand_type = brand_type;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPersistent() {
            return persistent;
        }

        public void setPersistent(String persistent) {
            this.persistent = persistent;
        }

        public String getPutaway() {
            return putaway;
        }

        public void setPutaway(String putaway) {
            this.putaway = putaway;
        }

        public String getSbrand() {
            return sbrand;
        }

        public void setSbrand(String sbrand) {
            this.sbrand = sbrand;
        }

        public String getSell_points() {
            return sell_points;
        }

        public void setSell_points(String sell_points) {
            this.sell_points = sell_points;
        }

        public String getSisrec() {
            return sisrec;
        }

        public void setSisrec(String sisrec) {
            this.sisrec = sisrec;
        }

        public String getSkeyword() {
            return skeyword;
        }

        public void setSkeyword(String skeyword) {
            this.skeyword = skeyword;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSpic() {
            return spic;
        }

        public void setSpic(String spic) {
            this.spic = spic;
        }

        public ShareTime getStime() {
            return stime;
        }

        public void setStime(ShareTime stime) {
            this.stime = stime;
        }

    }

}