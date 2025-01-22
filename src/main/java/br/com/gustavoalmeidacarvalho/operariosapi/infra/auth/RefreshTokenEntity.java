package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.RefreshToken;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Integer id;

    private String token;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public RefreshToken toModel() {
        return new RefreshToken(id, UUID.fromString(token), expirationDate, user.toModel());
    }
}
