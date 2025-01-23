package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.WorkerService;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<User> getWorkers() {
        return userRepository.findByRoleNot(UserRole.ADMIN)
                .stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public List<Event> getNextWorkerEvents(UUID workerId) {
        return eventRepository
                .findAllByWorkerId(workerId)
                .stream()
                .toList();
    }

}
