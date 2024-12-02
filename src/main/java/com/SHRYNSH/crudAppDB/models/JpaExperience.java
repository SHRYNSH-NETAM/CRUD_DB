package com.SHRYNSH.crudAppDB.models;

import com.SHRYNSH.crudAppDB.models.JpaCandidate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "experience")
@Data
public class JpaExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
//    @ToString.Exclude
    private JpaCandidate candidate;

    private String company;

    private Double duration; // Duration in years
}
