package br.com.gustavoalmeidacarvalho.operariosapi.infra.event;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.event.Event;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Sector sector;

    @ManyToMany
    @JoinTable(
            name = "tb_event_worker",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<User> workers;

    public EventEntity(Event event) {
        this.id = event.id();
        this.dateTime = event.dateTime();
        this.sector = event.sector();
        this.workers = event.workers();
    }

    public Event toModel() {
        return new Event(id, dateTime, sector,workers);
    }

}
