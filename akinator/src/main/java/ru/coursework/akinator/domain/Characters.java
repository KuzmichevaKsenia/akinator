package ru.coursework.akinator.domain;

import javax.persistence.*;

@Entity
public class Characters {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String charname;
    private Long repeats;

    public Characters(String charname) {
        this.charname = charname;
        this.repeats = 1L;
    }

    public Characters() {
    }

    public Long getId() {
        return id;
    }

    public String getCharname() {
        return charname;
    }

    public Long getRepeats() {
        return repeats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCharname(String charname) {
        this.charname = charname;
    }

    public void setRepeats(Long repeats) {
        this.repeats = repeats;
    }
}
