package com.juan.gamedevforums.web.dto;

import java.util.Date;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.Topic;

public class PostDto {

    public PostDto(Long id, Topic topic,
		   UserDto userDto, String content,
		   Date creationDate, Date lastUpdateDate) {
	this.id = id;
	this.topic = topic;
	this.userDto = userDto;
	this.content = content;
	this.creationDate = creationDate;
	this.lastUpdateDate = lastUpdateDate;
    }

    private Long id;
    
    private Topic topic;

    private User user;

    private String content;

    private Date creationDate;

    private Date lastUpdateDate;

    private UserDto userDto;

   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
