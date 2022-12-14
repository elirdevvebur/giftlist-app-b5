package kg.giftlist.giftlist.db.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.giftlist.giftlist.enums.NotificationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_gen")
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    private LocalDate createdAt;

    private boolean isRead;

    private Long recipientId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST} )
    @JsonIgnore
    private User user;

    @OneToOne
    private Wish wish;

    @OneToOne
    private Gift gift;

    @OneToOne
    private Holiday holiday;

    @OneToOne
    private Booking giftBooking;

    @OneToOne
    private Booking wishBooking;

    @OneToOne
    private Complaint complaintGift;

    @OneToOne
    private Complaint complaintWish;
}
