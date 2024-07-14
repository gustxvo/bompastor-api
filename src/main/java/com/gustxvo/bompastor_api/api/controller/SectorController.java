package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.sector.LeaderIdInput;
import com.gustxvo.bompastor_api.api.model.sector.SectorDto;
import com.gustxvo.bompastor_api.api.model.sector.WorkerIdInput;
import com.gustxvo.bompastor_api.api.model.user.UserSummary;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.model.user.UserRole;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @GetMapping
    public List<SectorDto> listSectors() {
        return sectorRepository.findAll().stream()
                .map(SectorDto::fromEntity)
                .toList();
    }

    @GetMapping("/{sectorId}")
    public ResponseEntity<SectorDto> getSector(@PathVariable("sectorId") Integer sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"));

        return ResponseEntity.ok(SectorDto.fromEntity(sector));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/{sectorId}/change-leader")
    public ResponseEntity<Void> changeLeader(@PathVariable("sectorId") Integer sectorId, @RequestBody LeaderIdInput leaderId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"));
        User leader = userRepository.findById(leaderId.uuid())
                .filter(user -> user.getRole() == UserRole.LEADER)
                .orElseThrow(() -> new IllegalStateException("Leader not found"));

        sector.setLeader(leader);
        sectorRepository.save(sector);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sectorId}/add-worker")
    public ResponseEntity<Void> addWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody WorkerIdInput workerId) {
        Sector sector = sectorRepository.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(workerId.uuid()).orElseThrow();
        sector.addWorker(worker);
        sectorRepository.save(sector);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{sectorId}/remove-worker")
    public ResponseEntity<Void> removeWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody WorkerIdInput workerId) {
        Sector sector = sectorRepository.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(workerId.uuid()).orElseThrow();
        sector.removeWorker(worker);
        sectorRepository.save(sector);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sectorId}/available-workers")
    public ResponseEntity<Set<UserSummary>> listAvailableWorkers(@PathVariable("sectorId") Integer sectorId) {
        Set<UUID> workerIds = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"))
                .getWorkers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        Set<UserSummary> availableWorkers = userRepository.findAllByIdNotIn(workerIds).stream()
                .map(UserSummary::fromEntity)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(availableWorkers);
    }
}
