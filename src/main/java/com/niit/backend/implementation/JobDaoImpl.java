package com.niit.backend.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.backend.dao.JobDao;
import com.niit.backend.model.Job;

@Repository
public class JobDaoImpl implements JobDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void postJob(Job job) {
		Session session = sessionFactory.openSession();
		session.save(job);
		session.flush();
		session.close();
	}

	public Job getJobById(int jobid) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		Job job = (Job) session.get(Job.class, jobid);
		session.close();
		return job;
	}

	@Override
	public List<Job> getAllJobs() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where status ='approved' and hasexpired='no'");
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

	@Transactional
	public Job updateJob(int jobid, Job job) {
		// person -> modified value -> 226
		Session session = sessionFactory.openSession();
		// current person -> 226
		// currentPerson, person -> with same id
		// updating only variable person
		// notunique
		// select [before modification]ge
		System.out.println("Id of job is to update is: " + job.getJobId());
		if (session.get(Job.class, jobid) == null) // id doesnt exist in the
													// database
			return null;
		session.merge(job); // update query where personid=?
		// select [after modification]
		Job updatedJob = (Job) session.get(Job.class, jobid); // select query
		session.flush();
		session.close();
		return updatedJob;

	}

	public void deleteJob(int id) {
		Session session = sessionFactory.openSession();
		// make the object persistent - person
		Job job = (Job) session.get(Job.class, id);
		session.delete(job);
		// Transient - person
		session.flush();
		session.close();
	}

	@Override
	public List<Job> getJobByStatus(String status) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where status = '" + status + "'");
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

	@Override
	public List<Job> getJobByExpiry(String hasexpired) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where hasexpired = '" + hasexpired + "'");
		List<Job> jobs = query.list();
		session.close();
		return jobs;
	}

}
