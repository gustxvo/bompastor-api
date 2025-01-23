package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.event.EventSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.WorkerNames;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.WorkerService;
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

    private final WorkerService workerService;

    @GetMapping
    public WorkerNames workers() {
        List<User> workers = workerService.getWorkers();
        return WorkerNames.fromUserList(workers);
    }

    @GetMapping("/events")
    public List<EventSummary> nextWorkerEvents(JwtAuthenticationToken token) {
        UUID workerId = UUID.fromString(token.getName());

        return workerService.getNextWorkerEvents(workerId).stream()
                .map(EventSummary::fromDomain)
                .toList();
    }
}
