package br.com.gustavoalmeidacarvalho.operariosapi.infra.event;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.sector.SectorEntity;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.infra.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_event")
@Data
@NoArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorEntity sector;

    @ManyToMany
    @JoinTable(
            name = "tb_event_worker",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<UserEntity> workers;

    public EventEntity(Event event) {
        this.id = event.id();
        this.dateTime = event.dateTime();
        this.sector = new SectorEntity(event.sector());
        this.workers = event.workers().stream()
                .map(UserEntity::new)
                .collect(Collectors.toSet());
    }

    public Event toModel() {
        Set<User> workers = this.workers.stream().map(UserEntity::toModel).collect(Collectors.toSet());
        return new Event(id, dateTime, sector.toModel(), workers);
    }

}
