package com.bd.gitlab.model;

import java.util.Date;

public class User {
	
	private long id;
	private String username;
    private String email;
	private String avatar_url;
	private String name;
	private boolean blocked;
	private Date created_at;
	private int access_level;
	private String private_token;
	private String state;
	private String bio;
	private String skype;
	private String linkedin;
	private String twitter;
	private String website_url;
	private int theme_id;
	private int color_scheme_id;
	private boolean is_admin;
	private boolean can_create_group;
	private boolean can_create_project;
	private int projects_limit;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

	public String getAvatarUrl() {
		return avatar_url;
	}
	public void setAvatarUrl(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Date getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(Date created_at) {
		this.created_at = created_at;
	}

	public String getAccessLevel(String[] names) {
		int temp = access_level / 10 - 1;
		
		if(temp >= 0 && temp < names.length)
			return names[temp];
		
		return "";
	}
	public void setAccessLevel(int access_level) {
		this.access_level = access_level;
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;

		User rhs = (User) obj;

        return rhs.id == id;
	}


	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public int getAccess_level() {
		return access_level;
	}

	public void setAccess_level(int access_level) {
		this.access_level = access_level;
	}

	public String getPrivate_token() {
		return private_token;
	}

	public void setPrivate_token(String private_token) {
		this.private_token = private_token;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getWebsite_url() {
		return website_url;
	}

	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}

	public int getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(int theme_id) {
		this.theme_id = theme_id;
	}

	public int getColor_scheme_id() {
		return color_scheme_id;
	}

	public void setColor_scheme_id(int color_scheme_id) {
		this.color_scheme_id = color_scheme_id;
	}

	public boolean is_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public boolean isCan_create_group() {
		return can_create_group;
	}

	public void setCan_create_group(boolean can_create_group) {
		this.can_create_group = can_create_group;
	}

	public boolean isCan_create_project() {
		return can_create_project;
	}

	public void setCan_create_project(boolean can_create_project) {
		this.can_create_project = can_create_project;
	}

	public int getProjects_limit() {
		return projects_limit;
	}

	public void setProjects_limit(int projects_limit) {
		this.projects_limit = projects_limit;
	}
}
