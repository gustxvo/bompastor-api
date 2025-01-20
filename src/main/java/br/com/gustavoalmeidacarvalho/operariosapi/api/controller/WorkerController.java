package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.WorkerNames;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.EventService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.UserRole;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final EventService eventService;
    private final UserRepository userRepository;

    @GetMapping
    public WorkerNames workers() {
        List<User> workers = userRepository.findByRoleNot(UserRole.ADMIN)
                .stream()
                .map(UserEntity::toModel)
                .toList();
        return WorkerNames.fromUserList(workers);
    }

    @GetMapping("/events")
    public List<EventSummary> list(JwtAuthenticationToken token) {
        UUID workerId = UUID.fromString(token.getName());

        return eventService
                .findAllByWorkerId(workerId)
                .stream()
                .map(EventSummary::fromDomain)
                .toList();
    }
}
