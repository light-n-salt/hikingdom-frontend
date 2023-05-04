package org.lightnsalt.hikingdom.batch.job;

import java.util.List;
import java.util.Random;

import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.repository.info.MountainInfoRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomMountainIdTasklet implements Tasklet {
	private final MountainInfoRepository mountainRepository;
	private MountainInfo randomMountain;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		List<MountainInfo> mountainIds = mountainRepository.findAll();
		int randomIndex = new Random().nextInt(mountainIds.size()) + 1;
		randomMountain = mountainIds.get(randomIndex);
		return RepeatStatus.FINISHED;
	}

	public MountainInfo getRandomMountainId() {
		return randomMountain;
	}

}
