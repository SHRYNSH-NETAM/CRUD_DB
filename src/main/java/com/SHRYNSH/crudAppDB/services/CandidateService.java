package com.SHRYNSH.crudAppDB.services;

import com.SHRYNSH.crudAppDB.models.*;
import com.SHRYNSH.crudAppDB.repositories.jpa.PostgresCandidateRepo;
import com.SHRYNSH.crudAppDB.repositories.mongo.MongoCandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CandidateService {
    @Autowired
    private MongoCandidateRepo mongoCandidateRepo;
    @Autowired
    private PostgresCandidateRepo postgresCandidateRepo;
    private final IdGeneratorService idGeneratorService = new IdGeneratorService();

    public Candidate createCandidate(Candidate candidate) {
        UUID id = idGeneratorService.generateId();
        MongoCandidate mongoCandidate = new MongoCandidate();
        mongoCandidate.setId(idGeneratorService.uuidToString(id));
        if(candidate.getName() != null) mongoCandidate.setName(candidate.getName());
        if(candidate.getCurrentCompany() != null) mongoCandidate.setCurrentCompany(candidate.getCurrentCompany());
        if(candidate.getSkills() != null) mongoCandidate.setSkills(candidate.getSkills());
        if(candidate.getExperiences() != null) {
            for (Experience experience : candidate.getExperiences()) {
                MongoExperience mongoExperience = new MongoExperience();
                mongoExperience.setCompany(experience.getCompany());
                mongoExperience.setDuration(experience.getDuration());
                mongoCandidate.getExperiences().add(mongoExperience);
            }
        }
        mongoCandidateRepo.save(mongoCandidate);

        JpaCandidate jpaCandidate = new JpaCandidate();
        jpaCandidate.setId(idGeneratorService.uuidToString(id));
        if(candidate.getName() != null) jpaCandidate.setName(candidate.getName());
        if(candidate.getCurrentCompany() != null) jpaCandidate.setCurrentCompany(candidate.getCurrentCompany());
        if(candidate.getSkills() != null) jpaCandidate.setSkills(candidate.getSkills());

        if(candidate.getExperiences() != null){
            for (Experience experience : candidate.getExperiences()) {
                JpaExperience jpaExperience = new JpaExperience();
                jpaExperience.setCompany(experience.getCompany());
                jpaExperience.setDuration(experience.getDuration());
                jpaExperience.setCandidate(jpaCandidate); // Set the candidate to establish the foreign key relationship

                jpaCandidate.getExperiences().add(jpaExperience);
            }
        }
        postgresCandidateRepo.save(jpaCandidate);

        return candidate;
    }

    public List<Candidate> getAllCandidates() {
        List<MongoCandidate> mongoCandidates = mongoCandidateRepo.findAll();
        List<JpaCandidate> jpaCandidates = postgresCandidateRepo.findAll();

        List<Candidate> candidates = new ArrayList<>();
        for (MongoCandidate mongoCandidate : mongoCandidates) {
            Candidate candidate = new Candidate();
            candidate.setName(mongoCandidate.getName());
            candidate.setCurrentCompany(mongoCandidate.getCurrentCompany());
            candidate.setSkills(mongoCandidate.getSkills());

            List<Experience> experiences = new ArrayList<>();
            for (MongoExperience mongoExperience : mongoCandidate.getExperiences()) {
                Experience experience = new Experience();
                experience.setCompany(mongoExperience.getCompany());
                experience.setDuration(mongoExperience.getDuration());
                experiences.add(experience);
            }
            candidate.setExperiences(experiences);

            candidates.add(candidate);
        }

        for (JpaCandidate jpaCandidate : jpaCandidates) {
            Candidate candidate = new Candidate();
            candidate.setName(jpaCandidate.getName());
            candidate.setCurrentCompany(jpaCandidate.getCurrentCompany());
            candidate.setSkills(jpaCandidate.getSkills());

            List<Experience> experiences = new ArrayList<>();
            for (JpaExperience jpaExperience : jpaCandidate.getExperiences()) {
                Experience experience = new Experience();
                experience.setCompany(jpaExperience.getCompany());
                experience.setDuration(jpaExperience.getDuration());
                experiences.add(experience);
            }
            candidate.setExperiences(experiences);
            if(!candidates.contains(candidate)) candidates.add(candidate);
        }

        return candidates;
    }

    public Candidate updateCandidate(String id, Candidate candidate) {
        MongoCandidate mongoCandidate = mongoCandidateRepo.findById(id).orElse(null);
        JpaCandidate jpaCandidate = postgresCandidateRepo.findById(id).orElse(null);

        if (mongoCandidate != null) {
            if(candidate.getName() != null) mongoCandidate.setName(candidate.getName());
            if(candidate.getCurrentCompany() != null) mongoCandidate.setCurrentCompany(candidate.getCurrentCompany());
            if(candidate.getSkills() != null) mongoCandidate.setSkills(candidate.getSkills());
            if(candidate.getExperiences() != null) {
                List<MongoExperience> mongoExperiences = new ArrayList<>();
                for (Experience experience : candidate.getExperiences()) {
                    MongoExperience mongoExperience = new MongoExperience();
                    mongoExperience.setCompany(experience.getCompany());
                    mongoExperience.setDuration(experience.getDuration());
                    mongoExperiences.add(mongoExperience);
                }
                mongoCandidate.setExperiences(mongoExperiences);
            }
            mongoCandidateRepo.save(mongoCandidate);
        }
        if(jpaCandidate != null) {
            if(candidate.getName() != null) jpaCandidate.setName(candidate.getName());
            if(candidate.getCurrentCompany() != null) jpaCandidate.setCurrentCompany(candidate.getCurrentCompany());
            if(candidate.getSkills() != null) jpaCandidate.setSkills(candidate.getSkills());
            if(candidate.getExperiences() != null) {
                jpaCandidate.getExperiences().clear();
                for (Experience experience : candidate.getExperiences()) {
                    JpaExperience jpaExperience = new JpaExperience();
                    jpaExperience.setCompany(experience.getCompany());
                    jpaExperience.setDuration(experience.getDuration());
                    jpaExperience.setCandidate(jpaCandidate); // Set the candidate to establish the foreign key relationship
                    jpaCandidate.getExperiences().add(jpaExperience);
                }
            }
            postgresCandidateRepo.save(jpaCandidate);
        }

        Candidate responseCandidate = new Candidate();
        if(mongoCandidate.getName() != null) responseCandidate.setName(mongoCandidate.getName());
        if(mongoCandidate.getCurrentCompany() != null) responseCandidate.setCurrentCompany(mongoCandidate.getCurrentCompany());
        if(mongoCandidate.getSkills() != null) responseCandidate.setSkills(mongoCandidate.getSkills());
        if(mongoCandidate.getExperiences() != null){
            List<Experience> experiences = new ArrayList<>();
            for (MongoExperience mongoExperience : mongoCandidate.getExperiences()) {
                Experience experience = new Experience();
                experience.setCompany(mongoExperience.getCompany());
                experience.setDuration(mongoExperience.getDuration());
                experiences.add(experience);
            }
            responseCandidate.setExperiences(experiences);
        }
        return responseCandidate;
    }
}
