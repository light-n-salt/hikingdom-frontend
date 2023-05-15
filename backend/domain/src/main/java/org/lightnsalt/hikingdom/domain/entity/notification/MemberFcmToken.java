package org.lightnsalt.hikingdom.domain.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.lightnsalt.hikingdom.domain.entity.info.MountainInfo;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import javax.persistence.*;

@Entity
@Table(name = "member_fcm_token")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "BIGINT UNSIGNED")
//    @JoinColumn(columnDefinition = "BIGINT UNSIGNED", name = "member_id", referencedColumnName = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @Column(length = 500)
    private String body;
}
