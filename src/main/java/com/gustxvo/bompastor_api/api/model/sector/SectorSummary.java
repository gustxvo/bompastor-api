package com.gustxvo.bompastor_api.api.model.sector;

import com.gustxvo.bompastor_api.domain.model.sector.Sector;

public record SectorSummary(Integer id, String name) {

    public static SectorSummary fromEntity(Sector sector) {
        return new SectorSummary(sector.getId(), sector.getName());
    }

}
