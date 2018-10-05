package com.eon.cps.jenkins.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

@org.springframework.stereotype.Service("fabric8KubernetesServiceImpl")
public class Fabric8KubernetesServiceImpl implements KubernetesService {

	@Override
	public List<Service> getJenkinsServices() {
		// TODO - To be injected
		KubernetesClient k8sClient = new DefaultKubernetesClient();
		ServiceList f8AllSvcObj = k8sClient.services().inAnyNamespace().list();
		List<Service> allServices = f8AllSvcObj.getItems();

		// List<String> k8sCoreNamespaces = Arrays.asList("default", "kube-system",
		// "monitoring");

		// TODO - Don't hard-code
		List<Service> jenkinsServices = allServices.stream()
				.filter(service -> ("jenkins".equals(service.getMetadata().getNamespace())
						&& "LoadBalancer".equals(service.getSpec().getType())))
				.collect(Collectors.toList());
		return jenkinsServices;

	}

	@Override
	public List<Pod> getPods(Map<String, String> labels) {
		// TODO - To be injected
		KubernetesClient k8sClient = new DefaultKubernetesClient();
		// TODO - Don't hard-code
		return k8sClient.pods().inNamespace("jenkins").withLabels(labels).list().getItems();
	}

}
