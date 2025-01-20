package br.com.gustavoalmeidacarvalho.operariosapi.infra.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<List<RefreshTokenEntity>> findAllByUser_Id(UUID userId);

    void deleteByToken(String token);

}
