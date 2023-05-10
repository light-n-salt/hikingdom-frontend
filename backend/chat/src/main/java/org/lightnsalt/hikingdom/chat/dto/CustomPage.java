package org.lightnsalt.hikingdom.chat.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomPage<T> {
	private List<T> content;
	private boolean isLast;
	private int pageNumber;
	private int pageSize;
	private long totalElements;

	public CustomPage(List<T> content, int pageNumber, int pageSize, long totalElements, boolean isLast) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.isLast = isLast;
	}
}
