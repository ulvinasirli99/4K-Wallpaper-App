package com.neodevloper.hdwallpaper2021.view.collections;

import java.util.List;

public class CoverPhoto{
	private List<Object> currentUserCollections;
	private String color;
	private String createdAt;
	private String description;
	private boolean likedByUser;
	private Urls urls;
	private String altDescription;
	private String updatedAt;
	private int width;
	private Links links;
	private String id;
	private List<Object> categories;
	private String promotedAt;
	private User user;
	private int height;
	private int likes;

	public List<Object> getCurrentUserCollections(){
		return currentUserCollections;
	}

	public String getColor(){
		return color;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getDescription(){
		return description;
	}

	public boolean isLikedByUser(){
		return likedByUser;
	}

	public Urls getUrls(){
		return urls;
	}

	public String getAltDescription(){
		return altDescription;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getWidth(){
		return width;
	}

	public Links getLinks(){
		return links;
	}

	public String getId(){
		return id;
	}

	public List<Object> getCategories(){
		return categories;
	}

	public String getPromotedAt(){
		return promotedAt;
	}

	public User getUser(){
		return user;
	}

	public int getHeight(){
		return height;
	}

	public int getLikes(){
		return likes;
	}
}