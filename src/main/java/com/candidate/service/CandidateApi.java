package com.candidate.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CandidateApi {
	
	private static Logger logger = LogManager.getLogger(CandidateApi.class.getName());

	@Autowired
	private CandidateService candidateService;
	
	
	@PostMapping(value = "/addCandidate")
	public Boolean addCandidate(@RequestBody Candidate request) {
		if(request==null)
			logger.error("request is null");
		Boolean response =candidateService.addCadidate(request);
		return response;
	}
	
	@GetMapping(value = "/getCandidateList")
	public List<Candidate> getCandidateList(@RequestParam(required = false,name = "searchTerm")String searchTerm,
			@RequestParam(required = false,name = "size",defaultValue = "0")int size,
			@RequestParam(required = false,name = "page",defaultValue = "0")int page) {
		
		List<Candidate> response =candidateService.getCandidate(page, size, searchTerm);
		if(response==null)
			logger.error("no candidates are present");
		return response;
	}
	
}
