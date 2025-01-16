package br.com.gustavoalmeidacarvalho.operariosapi.domain.model.sector;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_sector")
@Data
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
            name = "tb_sector_worker",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
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
