package com.exwebtech.shravantest.modules.Post.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HitsResponseModel {

//    {
//        "hits": [{
//        "created_at": "2019-12-26T15:33:24.000Z",
//            "title": "SimpleParallax – a JavaScript library for parallax effects",
//    }],
//        "nbPages": 50
//    }

    @Expose
    @SerializedName("nbPages")
    private int nbPages = 0;

    @Expose
    @SerializedName("hits")
    private List<HitsData> hits = new ArrayList<>();

    public int getNbPages() {
        return nbPages;
    }

    public void setHits(List<HitsData> hits) {
        this.hits = hits;
    }

    public List<HitsData> getHits() {
        return hits;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public static class HitsData {

        @Expose
        @SerializedName("created_at")
        private String created_at = "";

        @Expose
        @SerializedName("title")
        private String title = "";

        @Expose
        @SerializedName("selected")
        private boolean selected = false;


        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getTitle() {
            return title;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
