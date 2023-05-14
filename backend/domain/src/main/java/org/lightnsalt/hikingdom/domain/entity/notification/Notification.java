package org.lightnsalt.hikingdom.domain.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.lightnsalt.hikingdom.domain.common.BaseTimeEntity;
import org.lightnsalt.hikingdom.domain.entity.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 100)
    private String body;

    @Column(name = "send_at")
    private LocalDateTime sendAt;

    @Column(name = "is_read", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isRead;

    @Builder
    public Notification(Member member, String title, String body, LocalDateTime sendAt, boolean isRead) {
        this.member = member;
        this.title = title;
        this.body = body;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }
}
