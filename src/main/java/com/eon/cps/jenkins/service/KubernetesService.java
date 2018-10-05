package com.eon.cps.jenkins.service;

import java.util.List;
import java.util.Map;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;


public interface KubernetesService {
	
	public List<Service> getJenkinsServices();
	
	public List<Pod> getPods(Map<String, String> labels);

}
