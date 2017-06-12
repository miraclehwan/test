package mobile.com.prototype_socialapp.Main3_ListView;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class VideoData {

    String user;
    String title;
    String date;
    String count;
    String videourl;
    String imageurl;

    public VideoData(String user, String title, String date, String count, String videourl, String imageurl){
        this.user = user;
        this.title = title;
        this.date = date;
        this.count = count;
        this.videourl = videourl;
        this.imageurl = imageurl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
