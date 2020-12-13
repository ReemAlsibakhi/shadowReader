package com.reemsib.shadowreader.model;

public class PushNotification {

    private int video_id;
   private String negative_review;
    private String positive_review;
    private String lesson_name;
    private String video_name;

    public PushNotification() {
    }

    public PushNotification(int video_id, String negative_review, String positive_review, String lesson_name, String video_name) {
        this.video_id = video_id;
        this.negative_review = negative_review;
        this.positive_review = positive_review;
        this.lesson_name = lesson_name;
        this.video_name = video_name;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getNegative_review() {
        return negative_review;
    }

    public void setNegative_review(String negative_review) {
        this.negative_review = negative_review;
    }

    public String getPositive_review() {
        return positive_review;
    }

    public void setPositive_review(String positive_review) {
        this.positive_review = positive_review;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }
}
