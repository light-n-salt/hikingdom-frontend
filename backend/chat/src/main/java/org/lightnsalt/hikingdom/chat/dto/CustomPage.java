package org.lightnsalt.hikingdom.chat.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomPage<T> {
	private List<T> content;
	@JsonProperty("hasNext")
	private boolean hasNext;
	private int pageNumber;
	private int pageSize;
	private long totalElements;

	public CustomPage(List<T> content, int pageNumber, int pageSize, long totalElements, boolean hasNext) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.hasNext = hasNext;
	}
}
