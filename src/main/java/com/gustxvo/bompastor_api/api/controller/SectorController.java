package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.sector.WorkerIdInput;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    @PostMapping("/{sectorId}/add-worker")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody WorkerIdInput workerId) {
        Sector sector = sectorRepository.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(workerId.uuid()).orElseThrow();
        sector.addWorker(worker);
        sectorRepository.save(sector);

        return ResponseEntity.noContent().build();
    }
}
