package com.gustxvo.bompastor_api.domain.repository;

import com.gustxvo.bompastor_api.domain.model.user.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    Optional<List<RefreshToken>> findAllByUser_Id(UUID userId);

}
