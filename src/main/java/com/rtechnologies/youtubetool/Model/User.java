package com.rtechnologies.youtubetool.Model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullname;

    private String email;

    private String password;

    private String youtubeChannelLink;

    private int subscriptionDaysLeft;

    public User() {
    }

    public User(long id, String fullname, String email, String password, String youtubeChannelLink, int subscriptionDaysLeft) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.youtubeChannelLink = youtubeChannelLink;
        this.subscriptionDaysLeft = subscriptionDaysLeft;
    }

    public User(String fullname, String email, String password, String youtubeChannelLink, int subscriptionDaysLeft) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.youtubeChannelLink = youtubeChannelLink;
        this.subscriptionDaysLeft = subscriptionDaysLeft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getYoutubeChannelLink() {
        return youtubeChannelLink;
    }

    public void setYoutubeChannelLink(String youtubeChannelLink) {
        this.youtubeChannelLink = youtubeChannelLink;
    }

    public int getSubscriptionDaysLeft() {
        return subscriptionDaysLeft;
    }

    public void setSubscriptionDaysLeft(int subscriptionDaysLeft) {
        this.subscriptionDaysLeft = subscriptionDaysLeft;
    }
}

