package br.com.gustavoalmeidacarvalho.operariosapi.infra.user;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    public UserEntity(UUID userId) {
        this.id = userId;
    }

    public UserEntity(User user) {
        this.id = user.id();
        this.name = user.name();
        this.email = user.email();
        this.password = user.password();
        this.role = user.role();
    }

    public User toModel() {
        return new User(id, name, email, password, role);
    }

}
