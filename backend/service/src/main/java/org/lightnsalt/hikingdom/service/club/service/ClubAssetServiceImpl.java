package org.lightnsalt.hikingdom.service.club.service;

import java.util.List;
import java.util.stream.Collectors;

import org.lightnsalt.hikingdom.common.error.ErrorCode;
import org.lightnsalt.hikingdom.common.error.GlobalException;
import org.lightnsalt.hikingdom.domain.entity.club.Club;
import org.lightnsalt.hikingdom.domain.entity.club.ClubAsset;
import org.lightnsalt.hikingdom.service.club.repository.ClubAssetRepository;
import org.lightnsalt.hikingdom.service.club.repository.ClubRepository;
import org.lightnsalt.hikingdom.service.club.dto.response.ClubAssetRes;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubAssetServiceImpl implements ClubAssetService {
	private final ClubRepository clubRepository;
	private final ClubAssetRepository clubAssetRepository;

	@Override
	public List<ClubAssetRes> findClubMountainList(Long clubId) {
		final Club club = clubRepository.findById(clubId)
			.orElseThrow(() -> new GlobalException(ErrorCode.CLUB_NOT_FOUND));
		final List<ClubAsset> clubAssetList = clubAssetRepository.findAllByClubId(club.getId());

		return clubAssetList.stream()
			.map(ClubAssetRes::new).collect(Collectors.toList());
	}
}
