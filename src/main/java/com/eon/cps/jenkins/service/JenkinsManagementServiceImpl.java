
package com.eon.cps.jenkins.service;

import java.net.URI;
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
	public List<JenkinsInstance> getAllJenkinsInstances() {

		List<JenkinsInstance> jenkinsInstances = new ArrayList<JenkinsInstance>();

		List<Service> jenkinsServices = k8sService.getJenkinsServices();

		for (Service jenkinsService : jenkinsServices) {
			jenkinsService.getMetadata().getLabels();
			List<Pod> jenkinsPods = k8sService.getPods(jenkinsService.getMetadata().getLabels());

			Long failedPodCount = jenkinsPods.stream().filter(pod -> "Failed".equals(pod.getStatus().getPhase()))
					.count();
			Long runningPodCount = jenkinsPods.stream().filter(pod -> "Running".equals(pod.getStatus().getPhase()))
					.count();

			// TODO - Let the service return this list
			List<JenkinsPod> jp = new ArrayList<>();

			for (Pod pod : jenkinsPods) {

				JenkinsPod jenkinsPod = new JenkinsPod();
				jenkinsPod.setMessage(pod.getStatus().getMessage());
				jenkinsPod.setPhase(pod.getStatus().getPhase());
				jenkinsPod.setName(pod.getMetadata().getName());
				jp.add(jenkinsPod);

			}

			JenkinsInstance jenkinsInstance = new JenkinsInstance("2.138.1", jenkinsService.getMetadata().getName(), 3,
					jenkinsService.getMetadata().getNamespace());
			
			
			
			jenkinsInstance.setPods(jp);
			jenkinsInstance.setFailedPodsCount(failedPodCount.intValue());
			jenkinsInstance.setRunningPodsCount(runningPodCount.intValue());
			jenkinsInstances.add(jenkinsInstance);
		}
		

		return jenkinsInstances;
	}

}
