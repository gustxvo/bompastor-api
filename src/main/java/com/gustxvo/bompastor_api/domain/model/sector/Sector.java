package com.gustxvo.bompastor_api.domain.model.sector;

import com.gustxvo.bompastor_api.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tb_sectors")
@Getter
@Setter
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "tb_sectors_users",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

}
