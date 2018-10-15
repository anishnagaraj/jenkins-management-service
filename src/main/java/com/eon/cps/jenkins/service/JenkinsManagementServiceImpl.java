
package com.eon.cps.jenkins.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.eon.cps.jenkins.model.JenkinsInstance;
import com.eon.cps.jenkins.model.JenkinsPod;
import com.offbytwo.jenkins.JenkinsServer;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;

@org.springframework.stereotype.Service("jenkinsManagementServiceImpl")
public class JenkinsManagementServiceImpl implements JenkinsManagementService {

	@Autowired
	@Qualifier("fabric8KubernetesServiceImpl")
	private KubernetesService k8sService;

	@Override
	public List<JenkinsInstance> getAllJenkinsInstances() throws URISyntaxException, IOException {

		List<JenkinsInstance> jenkinsInstances = new ArrayList<JenkinsInstance>();

		List<Service> jenkinsServices = k8sService.getJenkinsServices();

		for (Service jenkinsService : jenkinsServices) {
			jenkinsService.getMetadata().getLabels();
			List<Pod> fabric8JenkinsPods = k8sService.getPods(jenkinsService.getMetadata().getLabels(),
					jenkinsService.getMetadata().getNamespace());

			Long failedPodCount = fabric8JenkinsPods.stream().filter(pod -> "Failed".equals(pod.getStatus().getPhase()))
					.count();
			Long runningPodCount = fabric8JenkinsPods.stream()
					.filter(pod -> "Running".equals(pod.getStatus().getPhase())).count();

			// TODO - Let the service return this list
			List<JenkinsPod> jenkinsPods = new ArrayList<>();

			for (Pod pod : fabric8JenkinsPods) {
				JenkinsPod jenkinsPod = new JenkinsPod();
				jenkinsPod.setMessage(pod.getStatus().getMessage());
				jenkinsPod.setPhase(pod.getStatus().getPhase());
				jenkinsPod.setName(pod.getMetadata().getName());
				jenkinsPods.add(jenkinsPod);
			}

			String url = "https://cps-hcl-sjs-workshop.westeurope.cloudapp.azure.com/"
					+ jenkinsService.getMetadata().getName();

			JenkinsServer jenkins = getJenkinsVersion(url, "admin", "");

			JenkinsInstance jenkinsInstance = new JenkinsInstance(jenkins.getVersion().getLiteralVersion(),
					jenkinsService.getMetadata().getName(), 3,
					jenkinsService.getMetadata().getNamespace());

			// jenkinsInstance.setPods(jp);
			jenkinsInstance.setFailedPodsCount(failedPodCount.intValue());
			jenkinsInstance.setRunningPodsCount(runningPodCount.intValue());
			jenkinsInstance.setEndPoint(url);
			jenkinsInstance.setAverageCPU("50m");
			jenkinsInstance.setAverageMemory("256Mi");
			jenkinsInstances.add(jenkinsInstance);
		}

		return jenkinsInstances;
	}

	public JenkinsServer getJenkinsVersion(String url, String username, String pwd) throws URISyntaxException {

		switch (url) {

		case "jenkins1":
			pwd = "mD7ZcpQaKf";
			break;

		case "jenkins2":
			pwd = "e4kCkLCtIA";
			break;

		case "jenkins4":
			pwd = "J7EALPw1tP";
			break;

		case "jenkins5":
			pwd = "98Am6eyGJj";
			break;

		default:
			pwd = "admin";
			break;

		}

		JenkinsServer jenkins = new JenkinsServer(new URI(url), "admin", pwd);

		return jenkins;

	}

}
