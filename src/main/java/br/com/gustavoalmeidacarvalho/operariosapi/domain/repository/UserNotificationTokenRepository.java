package br.com.gustavoalmeidacarvalho.operariosapi.domain.repository;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserNotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserNotificationTokenRepository extends JpaRepository<UserNotificationToken, Long> {

    Set<UserNotificationToken> findAllByUser_IdIn(Set<UUID> userIds);

    boolean existsByToken(String token);
}
