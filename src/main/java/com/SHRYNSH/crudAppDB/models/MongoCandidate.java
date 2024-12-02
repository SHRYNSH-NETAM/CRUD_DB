package com.SHRYNSH.crudAppDB.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "candidate")
@Data
@NoArgsConstructor
public class MongoCandidate {
    @Id
    private String id;
    @NonNull
    private String name;

    private List<String> skills = new ArrayList<>();

    private String currentCompany;

    private List<MongoExperience> experiences = new ArrayList<>();
}