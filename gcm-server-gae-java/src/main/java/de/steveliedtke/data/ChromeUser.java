package de.steveliedtke.data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class ChromeUser {

	@Id
	private Long id;
	
	private String userChannelId;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userChannelId
	 */
	public String getUserChannelId() {
		return userChannelId;
	}

	/**
	 * @param userChannelId the userChannelId to set
	 */
	public void setUserChannelId(String userChannelId) {
		this.userChannelId = userChannelId;
	}
}