package br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.sector.Sector;

public record SectorSummary(Integer id, String name) {

    public static SectorSummary fromEntity(Sector sector) {
        return new SectorSummary(sector.getId(), sector.getName());
    }

}
