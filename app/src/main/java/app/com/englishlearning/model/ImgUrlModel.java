package app.com.englishlearning.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgUrlModel {
    public ImgUrlModel(int total, int totalHits, List<ImageUrl> hits) {
        this.total = total;
        this.totalHits = totalHits;
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<ImageUrl> getHits() {
        return hits;
    }

    public void setHits(List<ImageUrl> hits) {
        this.hits = hits;
    }

    @SerializedName("total")
    int total;
    @SerializedName("totalHits")
    int totalHits;
    @SerializedName("hits")
    List<ImageUrl> hits;
}
