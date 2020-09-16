package com.neodevloper.hdwallpaper2021.view.collections;

import java.util.List;

public class ResultsItems {
	private boolean featured;
	private boolean jsonMemberPrivate;
	private CoverPhoto coverPhoto;
	private int totalPhotos;
	private String shareKey;
	private Object description;
	private String title;
	private List<TagsItem> tags;
	private List<PreviewPhotosItem> previewPhotos;
	private String updatedAt;
	private boolean curated;
	private Links links;
	private int id;
	private String publishedAt;
	private User user;

	public boolean isFeatured(){
		return featured;
	}

	public boolean isJsonMemberPrivate(){
		return jsonMemberPrivate;
	}

	public CoverPhoto getCoverPhoto(){
		return coverPhoto;
	}

	public int getTotalPhotos(){
		return totalPhotos;
	}

	public String getShareKey(){
		return shareKey;
	}

	public Object getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public List<PreviewPhotosItem> getPreviewPhotos(){
		return previewPhotos;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isCurated(){
		return curated;
	}

	public Links getLinks(){
		return links;
	}

	public int getId(){
		return id;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public User getUser(){
		return user;
	}
}