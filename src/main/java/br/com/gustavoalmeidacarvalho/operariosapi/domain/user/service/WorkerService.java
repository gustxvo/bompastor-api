package br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;

import java.util.List;
import java.util.UUID;

public interface WorkerService {

    List<User> getWorkers();

    List<Event> getNextWorkerEvents(UUID workerId);

}
