package com.eon.cps.jenkins.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.eon.cps.jenkins.model.JenkinsInstance;

public interface JenkinsManagementService {
	
	public List<JenkinsInstance> getAllJenkinsInstances() throws URISyntaxException, IOException ;


}
