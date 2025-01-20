package br.com.gustavoalmeidacarvalho.operariosapi.domain.event;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;

import java.time.LocalDateTime;
import java.util.Set;

public record Event(Long id, LocalDateTime dateTime, Sector sector, Set<User> workers) {

    public static Event create(LocalDateTime dateTime, Sector sector, Set<User> workers) {
        return new Event(null, dateTime, sector, workers);
    }

}
