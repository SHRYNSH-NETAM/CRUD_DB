package com.SHRYNSH.crudAppDB.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Candidate {
    private String id;
    private String name;
    private List<String> skills;
    private String currentCompany;
    private List<Experience> experiences;
}
