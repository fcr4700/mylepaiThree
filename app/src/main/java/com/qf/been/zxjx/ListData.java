package com.qf.been.zxjx;

import java.util.List;

/**
 * Created by lenovo on 2016/9/3.
 */
public class ListData {
      private String title;
      private String pic_url;
      private String date;

    @Override
    public String toString() {
        return "ListData{" +
                "title='" + title + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", date='" + date + '\'' +
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
