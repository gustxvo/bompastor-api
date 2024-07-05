package com.gustxvo.bompastor_api.api.controller;

import com.gustxvo.bompastor_api.api.model.sector.SectorDto;
import com.gustxvo.bompastor_api.domain.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorRepository sectorRepository;

    @GetMapping
    public Set<SectorDto> sectors(@Param("leader_id") String leaderId) {
        return sectorRepository.findByLeaderId(UUID.fromString(leaderId)).stream()
                .map(SectorDto::fromEntity)
                .collect(Collectors.toSet());
    }

}
