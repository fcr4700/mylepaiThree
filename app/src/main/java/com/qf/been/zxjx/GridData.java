package com.qf.been.zxjx;

/**
 * Created by lenovo on 2016/9/3.
 */
public class GridData {
        private String title;
        private String pic_url;
        private String doc_url;

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    @Override
    public String toString() {
        return "GridData{" +
                "title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
