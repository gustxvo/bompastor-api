package br.com.gustavoalmeidacarvalho.operariosapi.domain.repository;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    Optional<List<RefreshToken>> findAllByUser_Id(UUID userId);

    void deleteByToken(String token);
}
