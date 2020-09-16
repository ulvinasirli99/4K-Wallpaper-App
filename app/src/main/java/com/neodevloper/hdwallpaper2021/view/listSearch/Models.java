package com.neodevloper.hdwallpaper2021.view.listSearch;

import java.util.List;

public class Models{
	private int total;
	private int totalPages;
	private List<ResultsItem> results;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

}