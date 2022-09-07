package app.com.englishlearning.model;

import com.google.gson.annotations.SerializedName;

public class ImageUrl {
    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public ImageUrl(String previewURL) {
        this.previewURL = previewURL;
    }

    @SerializedName("webformatURL")
    String previewURL;
}
