package br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_notification_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserNotificationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
