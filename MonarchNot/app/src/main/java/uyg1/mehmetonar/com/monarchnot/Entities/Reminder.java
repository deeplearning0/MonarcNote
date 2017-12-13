package uyg1.mehmetonar.com.monarchnot.Entities;

/**
 * Created by mehme on 10.12.2017.
 */

public class Reminder {
    String title;
    String body;
    String reminingDate;
    String priority;
    String date;


    public Reminder(String title, String body, String reminingDate, String priority, String date) {
        this.title = title;
        this.body = body;
        this.reminingDate = reminingDate;
        this.priority = priority;
        this.date = date;
    }

    public Reminder() {


    }

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

    public String getreminingDate() {
        return reminingDate;
    }

    public void setreminingDate(String reminingDate) {
        this.reminingDate = reminingDate;
    }

    public String getpriority() {
        return priority;
    }

    public void setpriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", reminingDate='" + reminingDate + '\'' +
                ", priority='" + priority + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
