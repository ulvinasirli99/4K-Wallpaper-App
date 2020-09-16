package com.neodevloper.hdwallpaper2021.view.listSearch;

import java.util.List;

public class CoverPhoto{
	private List<Object> currentUserCollections;
	private String color;
	private String createdAt;
	private Object description;
	private boolean likedByUser;
	private Urls urls;
	private String altDescription;
	private String updatedAt;
	private int width;
	private Links links;
	private String id;
	private List<Object> categories;
	private Object promotedAt;
	private User user;
	private int height;
	private int likes;

	public void setCurrentUserCollections(List<Object> currentUserCollections){
		this.currentUserCollections = currentUserCollections;
	}

	public List<Object> getCurrentUserCollections(){
		return currentUserCollections;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setLikedByUser(boolean likedByUser){
		this.likedByUser = likedByUser;
	}

	public boolean isLikedByUser(){
		return likedByUser;
	}

	public void setUrls(Urls urls){
		this.urls = urls;
	}

	public Urls getUrls(){
		return urls;
	}

	public void setAltDescription(String altDescription){
		this.altDescription = altDescription;
	}

	public String getAltDescription(){
		return altDescription;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategories(List<Object> categories){
		this.categories = categories;
	}

	public List<Object> getCategories(){
		return categories;
	}

	public void setPromotedAt(Object promotedAt){
		this.promotedAt = promotedAt;
	}

	public Object getPromotedAt(){
		return promotedAt;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	public void setLikes(int likes){
		this.likes = likes;
	}

	public int getLikes(){
		return likes;
	}


}