package org.lightnsalt.hikingdom.batch.job.info;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RandomMountainTasklet implements Tasklet {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		List<Long> ids = entityManager.createQuery("SELECT m.id FROM MountainInfo m", Long.class)
			.getResultList();
		log.info("number of id is : {}", ids.size());
		Long randomId = ids.get(new Random().nextInt(ids.size()));
		// 추출한 랜덤 id를 다른 작업에 활용하도록 chunkContext에 저장
		chunkContext.getStepContext().getStepExecution().getExecutionContext().put("randomMountainId", randomId);
		return RepeatStatus.FINISHED;
	}
}