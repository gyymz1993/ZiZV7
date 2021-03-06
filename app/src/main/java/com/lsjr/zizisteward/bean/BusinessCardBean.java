package com.lsjr.zizisteward.bean;

import java.util.List;

/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/6/7 16:14
 */

public class BusinessCardBean {
    private Counts counts;
    private String error;
    private String msg;
    private Shares shares;

    public Counts getCounts() {
        return counts;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Shares getShares() {
        return shares;
    }

    public void setShares(Shares shares) {
        this.shares = shares;
    }

    public class Counts {
        private String CommentCount;// 总评论数
        private String likeCount;// 总点赞次数
        private String sightCount;// 总发起数
        private String creditLevelId;
        private String identityType;
        private String isAttentions;
        private String photo;
        private String user_name;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreditLevelId() {
            return creditLevelId;
        }

        public void setCreditLevelId(String creditLevelId) {
            this.creditLevelId = creditLevelId;
        }

        public String getIdentityType() {
            return identityType;
        }

        public void setIdentityType(String identityType) {
            this.identityType = identityType;
        }

        public String getIsAttentions() {
            return isAttentions;
        }

        public void setIsAttentions(String isAttentions) {
            this.isAttentions = isAttentions;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(String commentCount) {
            CommentCount = commentCount;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getSightCount() {
            return sightCount;
        }

        public void setSightCount(String sightCount) {
            this.sightCount = sightCount;
        }
    }

    public class Shares {
        private String currPage;
        private List<SharesList> page;
        private int pageSize;
        private String pageTitle;
        private int totalCount;
        private String totalPageCount;

        public String getCurrPage() {
            return currPage;
        }

        public void setCurrPage(String currPage) {
            this.currPage = currPage;
        }

        public List<SharesList> getPage() {
            return page;
        }

        public void setPage(List<SharesList> page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getPageTitle() {
            return pageTitle;
        }

        public void setPageTitle(String pageTitle) {
            this.pageTitle = pageTitle;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public String getTotalPageCount() {
            return totalPageCount;
        }

        public void setTotalPageCount(String totalPageCount) {
            this.totalPageCount = totalPageCount;
        }
    }

    public class SharesList {
        private String collect_time_uid;
        private String content;
        private String credit_level_id;
        private String custom_tag;
        private String entityId;
        private String forbid_reason;
        private String gnum;
        private String id;
        private String img_wh;
        private String is_audit;
        private String is_collect;
        private String is_dispaly;
        private String is_give;
        private String name;
        private String persistent;
        private String photo;
        private String photo_number;
        private String reality_name;
        private String shareImg;
        private String share_comment;
        private String share_like;
        private String share_read;
        private ShareTime share_time;
        private String share_time_uid;
        private String share_type;
        private String sight_type;
        private String spicfirst;
        private String user_id;
        private String user_name;
        private String want_count;
        private String want_users;
        private boolean isZan;
        private boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public boolean isZan() {
            return isZan;
        }

        public void setZan(boolean isZan) {
            this.isZan = isZan;
        }

        public String getCollect_time_uid() {
            return collect_time_uid;
        }

        public void setCollect_time_uid(String collect_time_uid) {
            this.collect_time_uid = collect_time_uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCredit_level_id() {
            return credit_level_id;
        }

        public void setCredit_level_id(String credit_level_id) {
            this.credit_level_id = credit_level_id;
        }

        public String getCustom_tag() {
            return custom_tag;
        }

        public void setCustom_tag(String custom_tag) {
            this.custom_tag = custom_tag;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getForbid_reason() {
            return forbid_reason;
        }

        public void setForbid_reason(String forbid_reason) {
            this.forbid_reason = forbid_reason;
        }

        public String getGnum() {
            return gnum;
        }

        public void setGnum(String gnum) {
            this.gnum = gnum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_wh() {
            return img_wh;
        }

        public void setImg_wh(String img_wh) {
            this.img_wh = img_wh;
        }

        public String getIs_audit() {
            return is_audit;
        }

        public void setIs_audit(String is_audit) {
            this.is_audit = is_audit;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getIs_dispaly() {
            return is_dispaly;
        }

        public void setIs_dispaly(String is_dispaly) {
            this.is_dispaly = is_dispaly;
        }

        public String getIs_give() {
            return is_give;
        }

        public void setIs_give(String is_give) {
            this.is_give = is_give;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersistent() {
            return persistent;
        }

        public void setPersistent(String persistent) {
            this.persistent = persistent;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhoto_number() {
            return photo_number;
        }

        public void setPhoto_number(String photo_number) {
            this.photo_number = photo_number;
        }

        public String getReality_name() {
            return reality_name;
        }

        public void setReality_name(String reality_name) {
            this.reality_name = reality_name;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShare_comment() {
            return share_comment;
        }

        public void setShare_comment(String share_comment) {
            this.share_comment = share_comment;
        }

        public String getShare_like() {
            return share_like;
        }

        public void setShare_like(String share_like) {
            this.share_like = share_like;
        }

        public String getShare_read() {
            return share_read;
        }

        public void setShare_read(String share_read) {
            this.share_read = share_read;
        }

        public ShareTime getShare_time() {
            return share_time;
        }

        public void setShare_time(ShareTime share_time) {
            this.share_time = share_time;
        }

        public String getShare_time_uid() {
            return share_time_uid;
        }

        public void setShare_time_uid(String share_time_uid) {
            this.share_time_uid = share_time_uid;
        }

        public String getShare_type() {
            return share_type;
        }

        public void setShare_type(String share_type) {
            this.share_type = share_type;
        }

        public String getSight_type() {
            return sight_type;
        }

        public void setSight_type(String sight_type) {
            this.sight_type = sight_type;
        }

        public String getSpicfirst() {
            return spicfirst;
        }

        public void setSpicfirst(String spicfirst) {
            this.spicfirst = spicfirst;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getWant_count() {
            return want_count;
        }

        public void setWant_count(String want_count) {
            this.want_count = want_count;
        }

        public String getWant_users() {
            return want_users;
        }

        public void setWant_users(String want_users) {
            this.want_users = want_users;
        }
    }


}
