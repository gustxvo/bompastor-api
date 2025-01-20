package br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
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
    private UserEntity user;

    public UserNotificationToken(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

}
