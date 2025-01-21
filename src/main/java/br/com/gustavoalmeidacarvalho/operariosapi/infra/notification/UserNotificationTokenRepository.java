package br.com.gustavoalmeidacarvalho.operariosapi.infra.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserNotificationTokenRepository extends JpaRepository<UserNotificationTokenEntity, Long> {

    Set<UserNotificationTokenEntity> findAllByUser_IdIn(Set<UUID> userIds);

    boolean existsByToken(String token);

}
