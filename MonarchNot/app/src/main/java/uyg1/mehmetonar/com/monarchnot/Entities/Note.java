package uyg1.mehmetonar.com.monarchnot.Entities;

/**
 * Created by mehme on 10.12.2017.
 */

public class Note {
    String title;
    String body;
    String date;

/*
    public Note(String body, String date, String title) {
        this.title = title;
        this.body = body;
        this.date = date;
    }
*/


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

