package br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;

import java.util.List;
import java.util.UUID;

public interface LeaderService {

    Sector findSectorByLeaderId(UUID leaderId);

    List<Event> findEventsByLeaderId(UUID leaderId);

}
