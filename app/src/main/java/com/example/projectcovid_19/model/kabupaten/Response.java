package com.example.projectcovid_19.model.kabupaten;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("results")
	private List<ResultsItem> results;

	public List<ResultsItem> getResults(){
		return results;
	}
}