package org.lightnsalt.hikingdom.service.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.lightnsalt.hikingdom.domain.entity.member.Member;
import org.lightnsalt.hikingdom.service.member.repository.MemberRepository;
import org.lightnsalt.hikingdom.service.notification.dto.FCMNotificationReq;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

//    public String sendNotificationByToken(FCMNotificationReq requestDto) {
//
//        Optional<Member> member = memberRepository.findById(requestDto.getTargetUserId());
//
//        if (member.isPresent()) {
//            if (member.get().getFirebaseToken() != null) {
//                Notification notification = Notification.builder()
//                        .setTitle(requestDto.getTitle())
//                        .setBody(requestDto.getBody())
//                        // .setImage(requestDto.getImage())
//                        .build();
//
//                Message message = Message.builder()
//                        .setToken(member.get().getFirebaseToken())
//                        .setNotification(notification)
//                        // .putAllData(requestDto.getData())
//                        .build();
//
//                try {
//                    firebaseMessaging.send(message);
//                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetUserId();
//                } catch (FirebaseMessagingException e) {
//                    e.printStackTrace();
//                    return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getTargetUserId();
//                }
//            } else {
//                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
//            }
//
//        } else {
//            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
//        }
//
//
//    }
}
