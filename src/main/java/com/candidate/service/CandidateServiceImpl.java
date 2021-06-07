package com.candidate.service;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;


@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Boolean addCadidate(Candidate request) {
		Boolean response = false;
		try {
			CandidateCollection collection = null;
			if (request.getId() != null) {
				collection = candidateRepository.findById(new ObjectId(request.getId())).orElse(null);

				if (collection == null)
					throw new RuntimeException("No Such Record");
				else {
					if (request.getName() != null)
						collection.setName(request.getName());
					if (request.getPhoneNumber() != null)
						collection.setPhoneNumber(request.getPhoneNumber());
					if (request.getEmailAddress() != null)
						collection.setEmailAddress(request.getEmailAddress());
					if (request.getAddress() != null)
						collection.setAddress(request.getAddress());
				}

			} else {
				collection = new CandidateCollection();
				collection.setName(request.getName());
				collection.setPhoneNumber(request.getPhoneNumber());
				collection.setEmailAddress(request.getEmailAddress());
				collection.setAddress(request.getAddress());

			}
			candidateRepository.save(collection);
			response = true;
		} catch (RuntimeException e) {
			throw new RuntimeException("Error while adding candidate" + e.getMessage());

		}

		return response;
	}

	@Override
	public List<Candidate> getCandidate(int page, int size, String searchTerm) {
		List<Candidate> response=null;
		try {
		Criteria criteria = new Criteria();
		
		if (searchTerm!=null)
			criteria = criteria.orOperator(new Criteria("name").regex("^" + searchTerm, "i"),
					new Criteria("name").regex("^" + searchTerm));
		
	


		Aggregation aggregation = null;
		if (size > 0) {
			aggregation = Aggregation.newAggregation(
					
					
					Aggregation.match(criteria),
					Aggregation.sort(Sort.by(Sort.Direction.ASC,"name")), Aggregation.skip((page) * size),
					Aggregation.limit(size));

		} else {
			aggregation = Aggregation.newAggregation(
					Aggregation.match(criteria),
					Aggregation.sort(Sort.by(Sort.Direction.ASC,"name")));

		}
		response = mongoTemplate.aggregate(aggregation, CandidateCollection.class, Candidate.class)
				.getMappedResults();
		
		}
		catch (RuntimeException e) {
			throw new RuntimeException("Error while getting the candidate list");
		}

		return response;
	}

}
