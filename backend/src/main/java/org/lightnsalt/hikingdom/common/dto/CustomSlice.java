package org.lightnsalt.hikingdom.common.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomSlice<T> {
	private final List<T> content;
	private final long numberOfElements;
	private final int pageSize;
	@JsonProperty("hasPrevious")
	private final boolean hasPrevious;
	@JsonProperty("hasNext")
	private final boolean hasNext;

	public CustomSlice(Slice<T> page) {
		this.content = page.getContent();
		this.numberOfElements = page.getNumberOfElements();
		this.pageSize = page.getSize();
		this.hasPrevious = page.hasPrevious();
		this.hasNext = page.hasNext();
	}

}
