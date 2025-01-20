package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.UserNotificationToken;
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
public class UserNotificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    @Column(unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserNotificationTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public UserNotificationTokenEntity(UserNotificationToken userToken) {
        this.deviceId = userToken.deviceId();
        this.token = userToken.token();
        this.user = new UserEntity(userToken.userId());
    }

    public UserNotificationToken toModel() {
        return new UserNotificationToken(deviceId, token, user.getId());
    }

}
