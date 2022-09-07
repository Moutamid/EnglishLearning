package app.com.englishlearning.model;

public class WordsModel {
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WordsModel(String img, String title) {
        this.img = img;
        this.title = title;
    }

    String img;
    String title;
}
