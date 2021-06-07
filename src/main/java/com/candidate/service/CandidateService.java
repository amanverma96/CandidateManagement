package com.candidate.service;

import java.util.List;

public interface CandidateService {

	Boolean addCadidate(Candidate request);
	
	List<Candidate> getCandidate(int page,int size,String searchTerm);
}
