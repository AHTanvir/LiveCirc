package live.livecirc;

/**
 * Created by anwar on 1/22/2018.
 */

public class NewsModel {
    private String titel;
    private String description;
    private String url;
    private String imgUrl;

    public NewsModel(String titel, String description, String url, String imgUrl) {
        this.titel = titel;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
