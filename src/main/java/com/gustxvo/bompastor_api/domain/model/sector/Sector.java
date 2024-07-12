package com.gustxvo.bompastor_api.domain.model.sector;

import com.gustxvo.bompastor_api.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_sectors")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sector_id")
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User leader;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_sectors_workers",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> workers = new HashSet<>();

    public Sector(Integer sectorId) {
        this.id = sectorId;
    }

    public void addWorker(User worker) {
        workers.add(worker);
    }

    public void removeWorker(User worker) {
        workers.removeIf(user -> Objects.equals(user.getId(), worker.getId()));
    }
}
