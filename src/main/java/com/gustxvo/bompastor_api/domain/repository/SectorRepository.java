package com.gustxvo.bompastor_api.domain.repository;

import com.gustxvo.bompastor_api.domain.model.sector.Sector;
import com.gustxvo.bompastor_api.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {
}
