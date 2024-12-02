package com.SHRYNSH.crudAppDB.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidate")
@Data
@NoArgsConstructor
public class JpaCandidate {
    @Id
    private String id;
    @Nonnull
    private String name;
    @Column(name = "skills")
    private List<String> skills;

    @Column(name = "current_company")
    private String currentCompany;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaExperience> experiences = new ArrayList<>();
}

// schema =
