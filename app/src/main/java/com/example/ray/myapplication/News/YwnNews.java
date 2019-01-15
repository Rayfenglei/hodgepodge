package com.example.ray.myapplication.News;

import java.io.Serializable;
import java.util.List;

public class YwnNews implements Serializable {
    public static final String DISPLAY_NOIMAGE = "0x00000001";
    public static final String DISPLAY_NORMAL = "0x00000002";
    public static final String DISPLAY_IMAGES = "0x00000004";
    public static final String DISPLAY_BIG = "0x00000008";

    private String allow_comment;
    private String body_size;
    private String comment_count;
    private String content_id;
    private String content_type;
    private String cpack;
    private String display;
    private Image headimage;
    private String is_liked;
    private String like_count;
    private String link_type;
    private List<Image> list_images;
    private String logourl;
    private String origin_url;
    private String publish_time;
    private String share_url;
    private String source;
    private String summary;
    private String title;
    private String url;
    private String flag;
    private String top;
    private String top_ttl;
    private List<String> pv_url;
    private List<String> click_url;

    public YwnNews() {
    }

    public List<String> getPv_url() {
        return pv_url;
    }

    public void setPv_url(List<String> pv_url) {
        this.pv_url = pv_url;
    }

    public List<String> getClick_url() {
        return click_url;
    }

    public void setClick_url(List<String> click_url) {
        this.click_url = click_url;
    }

    public String getAllow_comment() {
        return allow_comment;
    }

    public void setAllow_comment(String allow_comment) {
        this.allow_comment = allow_comment;
    }

    public String getBody_size() {
        return body_size;
    }

    public void setBody_size(String body_size) {
        this.body_size = body_size;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getCpack() {
        return cpack;
    }

    public void setCpack(String cpack) {
        this.cpack = cpack;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Image getHeadimage() {
        return headimage;
    }

    public void setHeadimage(Image headimage) {
        this.headimage = headimage;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public List<Image> getList_images() {
        return list_images;
    }

    public void setList_images(List<Image> list_images) {
        this.list_images = list_images;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getTop_ttl() {
        return top_ttl;
    }

    public void setTop_ttl(String top_ttl) {
        this.top_ttl = top_ttl;
    }

    public static class Image implements Serializable{
        private static final long serialVersionUID = 5947827332427115190L;
        private String format;
        private String height;
        private String img_url;
        private String surl;
        private String width;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getSurl() {
            return surl;
        }

        public void setSurl(String surl) {
            this.surl = surl;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }


}
