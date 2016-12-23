package com.niit.backend.dao;

import java.util.List;

import com.niit.backend.model.Job;


public interface JobDao {
	void postJob(Job job);
	Job getJobById(int jobid);
	List<Job> getAllJobs();
	Job updateJob(int jobid, Job job);
	void deleteJob(int id);
	
	List<Job> getJobByStatus(String status);	//status=approved or rejected or pending
	List<Job> getJobByExpiry(String hasexpired);	// hasexpired=yes or no
	
}
