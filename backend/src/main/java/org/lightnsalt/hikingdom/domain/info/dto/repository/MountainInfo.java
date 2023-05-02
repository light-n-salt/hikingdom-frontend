package org.lightnsalt.hikingdom.domain.info.dto.repository;

public interface MountainInfo {
	Long getId();

	String getName();

	String description();

	String getAddress();

	double getMaxAlt();

	double getTopLat();

	double getTopLng();

	int getTotalDuration();

	String getImgUrl();

	String getPeaks();

	String getTransport();

	String getFacility();

	double getDiffDistance();

}
