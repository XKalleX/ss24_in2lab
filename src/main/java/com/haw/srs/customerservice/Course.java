package com.haw.srs.customerservice;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer anzahlTeilnehmer = 0; //Attribut für die Anzahl der Teilnehmer

    public Course(String name) {
        this.name = name;
    }
    
    // Getter und Setter für das Attribut "anzahlTeilnehmer"
    public Integer getAnzahlTeilnehmer() {
        return anzahlTeilnehmer;
    }

    public void setAnzahlTeilnehmer(Integer anzahlTeilnehmer) {
        this.anzahlTeilnehmer = anzahlTeilnehmer;
    }
}
