package mobile.com.prototype_socialapp.Comment_ListView;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class CommentData {

    String idx;
    String user;
    String comment;
    String date;
    String imageurl;

    public CommentData(String idx, String user, String comment, String date, String imageurl){
        this.idx = idx;
        this.user = user;
        this.comment = comment;
        this.date = date;
        this.imageurl = imageurl;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
