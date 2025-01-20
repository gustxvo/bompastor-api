package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_sector")
@Data
@NoArgsConstructor
public class SectorEntity {

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
            name = "tb_sector_worker",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<User> workers = new HashSet<>();

    public SectorEntity(Sector sector) {
        this.id = sector.id();
        this.name = sector.name();
        this.leader = sector.leader();
        this.workers = sector.workers();
    }

    public Sector toModel() {
        return new Sector(id, name, leader, workers);
    }

}
