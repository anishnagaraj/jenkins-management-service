package com.eon.cps.jenkins.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eon.cps.jenkins.model.JenkinsInstance;
import com.eon.cps.jenkins.service.JenkinsManagementService;


@RestController
@CrossOrigin
public class JenkinsController {
	

	@Autowired
	@Qualifier("jenkinsManagementServiceImpl")
	private JenkinsManagementService jenkinsMgmtService;

	private static final String JENKINS_SERVICE_BASE_URL = "/jenkins-controller";

	private static final String JENKINS_GET_JENKINS_SERVICES_ON_K8S_CLUSTER = JENKINS_SERVICE_BASE_URL + "/services";
		

	@RequestMapping(value = JENKINS_GET_JENKINS_SERVICES_ON_K8S_CLUSTER, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<JenkinsInstance> getJenkinsDetails() throws URISyntaxException, IOException {
		return jenkinsMgmtService.getAllJenkinsInstances();
	}

}
