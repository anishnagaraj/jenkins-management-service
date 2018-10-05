package com.eon.cps.jenkins.model;

import java.util.List;

import io.fabric8.kubernetes.api.model.Pod;

public class JenkinsInstance {
	
	private String version;
	
	private String serviceName;
	
	private Integer jobsCount;
	
	private String k8sNamespace;
	
	private List<JenkinsPod> pods;
	
	private Integer failedPodsCount;
	
	private Integer runningPodsCount;

	public JenkinsInstance(String version, String serviceName, Integer jobsCount, String k8sNamespace) {
		super();
		this.version = version;
		this.serviceName = serviceName;
		this.jobsCount = jobsCount;
		this.k8sNamespace = k8sNamespace;
	}

	public String getVersion() {
		return version;
	}

	public String getServiceName() {
		return serviceName;
	}

	public Integer getJobsCount() {
		return jobsCount;
	}
	
	public String getk8sNamespace() {
		return k8sNamespace;
	}

	public List<JenkinsPod> getPods() {
		return pods;
	}

	public void setPods(List<JenkinsPod> pods) {
		this.pods = pods;
	}

	public Integer getRunningPodsCount() {
		return runningPodsCount;
	}

	public void setRunningPodsCount(Integer runningPodsCount) {
		this.runningPodsCount = runningPodsCount;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getFailedPodsCount() {
		return failedPodsCount;
	}

	public void setFailedPodsCount(Integer failedPodsCount) {
		this.failedPodsCount = failedPodsCount;
	}


	
}
