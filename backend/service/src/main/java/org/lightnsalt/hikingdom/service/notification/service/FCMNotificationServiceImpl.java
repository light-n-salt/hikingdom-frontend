package org.lightnsalt.hikingdom.service.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.domain.entity.notification.MemberFcmToken;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.lightnsalt.hikingdom.service.notification.dto.request.FCMNotificationReq;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class FCMNotificationServiceImpl implements FCMNotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final MemberRepository memberRepository;

	public void sendNotificationByToken(FCMNotificationReq requestDto) {

		Optional<Member> member = memberRepository.findById(requestDto.getTargetUserId());

		if (member.isPresent()) {
			if (member.get().getMemberFcmTokens() != null && member.get().getMemberFcmTokens().size() != 0) {
				Notification notification = Notification.builder()
					.setTitle(requestDto.getTitle())
					.setBody(requestDto.getBody())
					// .setImage(requestDto.getImage())
					.build();

				for (MemberFcmToken memberFcmToken : member.get().getMemberFcmTokens()) {
					if (memberFcmToken.getBody() == null) {
						log.info(member.get().getEmail() + " memberFcmToken is null");
					} else {
						Message message = Message.builder()
							.setToken(memberFcmToken.getBody())
							.setNotification(notification)
							// .putAllData(requestDto.getData())
							.build();
						try {
							firebaseMessaging.send(message);
							log.info("해당 fcmToken으로 알림을 성공적으로 전송했습니다. fcmToken: " + memberFcmToken.getBody());
						} catch (FirebaseMessagingException e) {
							e.printStackTrace();
							log.info("해당 fcmToken으로 알림 보내기를 일부 실패하였습니다. fcmToken: " + memberFcmToken.getBody());
						}
					}
				}

			} else {
				log.info("해당 사용자에 대한 fcm token 정보가 없습니다. targetUserId=" + requestDto.getTargetUserId());
			}
		}
		//        log.info("해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId());
	}
}
