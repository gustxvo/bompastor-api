package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.LeaderIdInput;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.SectorDto;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.WorkerIdInput;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserSummary;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.UserRole;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.repository.UserRepository;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final UserRepository userRepository;
    private final SectorService sectorService;

    @GetMapping
    public List<SectorDto> listSectors() {
        return sectorService.findAll().stream()
                .map(SectorDto::fromDomain)
                .toList();
    }

    @GetMapping("/{sectorId}")
    public ResponseEntity<SectorDto> getSector(@PathVariable("sectorId") Integer sectorId) {
        Sector sector = sectorService.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"));

        return ResponseEntity.ok(SectorDto.fromDomain(sector));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/{sectorId}/change-leader")
    public ResponseEntity<Void> changeLeader(@PathVariable("sectorId") Integer sectorId, @RequestBody LeaderIdInput leaderId) {
        Sector sector = sectorService.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"));

        User leader = userRepository.findById(leaderId.uuid())
                .filter(user -> user.getRole() == UserRole.LEADER)
                .orElseThrow(() -> new IllegalStateException("Leader not found"));

        sectorService.changeLeader(sector, leader);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sectorId}/add-worker")
    public ResponseEntity<Void> addWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody WorkerIdInput workerId) {
        Sector sector = sectorService.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(workerId.uuid()).orElseThrow();

        sectorService.addWorker(sector, worker);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{sectorId}/remove-worker")
    public ResponseEntity<Void> removeWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody WorkerIdInput workerId) {
        Sector sector = sectorService.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(workerId.uuid()).orElseThrow();

        sectorService.removeWorker(sector, worker);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sectorId}/available-workers")
    public ResponseEntity<Set<UserSummary>> listAvailableWorkers(@PathVariable("sectorId") Integer sectorId) {
        Set<UUID> workerIds = sectorService.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"))
                .workers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        List<UUID> adminIds = userRepository.findByRole(UserRole.ADMIN).stream().map(User::getId).toList();
        workerIds.addAll(adminIds);

        Set<UserSummary> availableWorkers = userRepository.findAllByIdNotIn(workerIds).stream()
                .map(UserSummary::fromEntity)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(availableWorkers);
    }
}
