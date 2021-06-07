package com.candidate.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<CandidateCollection, ObjectId>{

}
