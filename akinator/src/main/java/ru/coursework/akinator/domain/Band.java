package ru.coursework.akinator.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"parent", "members", "invsToPlayers", "usersReqs", "seanceInvs"})

@Entity
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bandname;
    private Integer number;
    private Long score;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "parent_id")
    private User parent;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinTable(name = "members",
            joinColumns = { @JoinColumn(name = "band_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> members = new HashSet<>();

    @ManyToMany(mappedBy = "bandsInvs", fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    private Set<User> invsToPlayers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinTable(name = "player_member_request_band",
            joinColumns = { @JoinColumn(name = "band_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> usersReqs = new HashSet<>();

    @ManyToMany(mappedBy = "seanceInvs", fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    private Set<User> seanceInvs = new HashSet<>();

    @Builder
    public Band(String bandname, Integer number, Long score, User parent) {
        this.bandname = bandname;
        this.number = number;
        this.score = score;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Band{" + "id=" + id + ", bandname=" + bandname + '}';
    }

}
