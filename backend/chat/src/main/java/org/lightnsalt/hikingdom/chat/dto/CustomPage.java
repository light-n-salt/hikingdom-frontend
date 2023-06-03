package org.lightnsalt.hikingdom.chat.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 프로젝트 내 사용되는 페이지 형식을 나타내는 클래스
 * 클라이언트와 일관된 형식으로 데이터를 주고받기 위해 Spring Data JPA의 기본 Page 형식을 따르지 않고 따로 정의해 사용한다.
 *
 * @param <T> Page 내 요소의 타입
 */
@Data
public class CustomPage<T> {
	/**
	 * 페이지 내 요소 목록
	 */
	private List<T> content;

	/**
	 * 페이지 번호
	 */
	private int pageNumber;

	/**
	 * 페이지 크기 (요소 개수)
	 */
	private int pageSize;

	/**
	 * 전체 요소 개수
	 */
	private long totalElements;

	/**
	 * 다음 페이지 존재 여부
	 */
	@JsonProperty("hasNext")
	private boolean hasNext;

	/**
	 * 주어진 요소 목록, 페이지 번호, 페이지 크기, 전체 요소 개수, 다음 페이지 존재 여부로 CustomPage 객체를 생성한다.
	 *
	 * @param content 페이지 내 요소 목록
	 * @param pageNumber 페이지 번호
	 * @param pageSize 페이지 크기
	 * @param totalElements 전체 요소 개수
	 * @param hasNext 다음 페이지 존재 여부
	 */
	public CustomPage(List<T> content, int pageNumber, int pageSize, long totalElements, boolean hasNext) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.hasNext = hasNext;
	}
}
