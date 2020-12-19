package com.juan.gamedevforums.web.dto;

import java.util.Date;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Categories;

public class TopicDto {

    public TopicDto(Long id, User user, Categories categories,
		    String title, String content,
		    int views, Date creationDate,
		    Date lastUpdateDate, boolean closed) {
	this.user = user;
	this.categories = categories;
	this.title = title;
	this.content = content;
	this.views = views;
	this.creationDate = creationDate;
	this.lastUpdateDate = lastUpdateDate;
	this.closed = closed;
    }

    private Long id;

    private User user;

    private Categories categories;
  
    private String title;

    private String content;
    
    private int views;

    private Date creationDate;

    private Date lastUpdateDate;

    private boolean closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
    
