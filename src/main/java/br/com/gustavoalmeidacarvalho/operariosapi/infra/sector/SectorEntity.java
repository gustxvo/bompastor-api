package br.com.gustavoalmeidacarvalho.operariosapi.infra.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private UserEntity leader;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_sector_worker",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<UserEntity> workers = new HashSet<>();

    public SectorEntity(Sector sector) {
        this.id = sector.id();
        this.name = sector.name();
        this.leader = new UserEntity(sector.leader());
        this.workers = sector.workers().stream()
                .map(UserEntity::new)
                .collect(Collectors.toSet());;
    }

    public Sector toModel() {
        User leader = this.leader.toModel();
        Set<User> workers = this.workers.stream().map(UserEntity::toModel).collect(Collectors.toSet());
        return new Sector(id, name, leader, workers);
    }

}
