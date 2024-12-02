package com.SHRYNSH.crudAppDB.repositories.mongo;

import com.SHRYNSH.crudAppDB.models.MongoCandidate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCandidateRepo extends MongoRepository<MongoCandidate, String> {
}
