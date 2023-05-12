package org.lightnsalt.hikingdom.chat.repository.mongo;

import java.time.LocalDateTime;

import org.lightnsalt.hikingdom.chat.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
	Page<Chat> findByClubIdOrderBySendAtDesc(Long clubId, Pageable pageable);

	Page<Chat> findByClubIdAndSendAtBeforeOrderBySendAtDesc(Long clubId, LocalDateTime sendAt, Pageable pageable);
}
