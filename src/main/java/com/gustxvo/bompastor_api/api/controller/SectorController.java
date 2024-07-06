package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.sector.SectorDto;
import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import com.gustxvo.bompastor_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;

    @GetMapping
    public Set<SectorDto> sectors(@Param("leader_id") String leaderId) {
        return sectorRepository.findByLeaderId(UUID.fromString(leaderId)).stream()
                .map(SectorDto::fromEntity)
                .collect(Collectors.toSet());
    }

    @PostMapping("/add-worker/{sectorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addWorker(
            @PathVariable("sectorId") Integer sectorId, @RequestBody String workerId) {
        Sector sector = sectorRepository.findById(sectorId).orElseThrow();
        User worker = userRepository.findById(UUID.fromString(workerId)).orElseThrow();
        sector.addWorker(worker);
        sectorRepository.save(sector);

        return ResponseEntity.noContent().build();
    }
}
