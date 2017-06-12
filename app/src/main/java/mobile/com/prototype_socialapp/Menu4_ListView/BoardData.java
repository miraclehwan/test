package mobile.com.prototype_socialapp.Menu4_ListView;

/**
 * Created by Daehwan Kim on 2017-06-10.
 */

public class BoardData {

    String user;
    String day;
    String title;
    String content;
    String reply;
    String count;
    String imageurl;
    public BoardData(String user, String day, String title, String content, String reply, String count, String imageurl){
        this.user = user;
        this.day = day;
        this.title = title;
        this.content = content;
        this.reply = reply;
        this.count = count;
        this.imageurl = imageurl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
