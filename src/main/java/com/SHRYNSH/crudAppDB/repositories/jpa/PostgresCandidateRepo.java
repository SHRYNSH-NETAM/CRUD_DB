package com.SHRYNSH.crudAppDB.repositories.jpa;

import com.SHRYNSH.crudAppDB.models.JpaCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresCandidateRepo extends JpaRepository<JpaCandidate, String> {
}
