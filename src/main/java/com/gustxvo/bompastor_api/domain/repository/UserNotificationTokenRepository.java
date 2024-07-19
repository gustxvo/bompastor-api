package com.gustxvo.bompastor_api.domain.repository;

import com.gustxvo.bompastor_api.domain.model.user.UserNotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface UserNotificationTokenRepository extends JpaRepository<UserNotificationToken, Long> {

    Set<UserNotificationToken> findAllByUser_IdIn(Set<UUID> userIds);

    boolean existsByToken(String token);
}
