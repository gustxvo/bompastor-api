package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.sector.SectorDto;
import com.gustxvo.bompastor_api.api.model.sector.WorkerIdInput;
import com.gustxvo.bompastor_api.api.model.user.UserSummary;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @GetMapping("/{sectorId}")
    public ResponseEntity<SectorDto> getSector(@PathVariable("sectorId") Integer sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new IllegalStateException("Sector not found"));

        return ResponseEntity.ok(SectorDto.fromEntity(sector));
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
