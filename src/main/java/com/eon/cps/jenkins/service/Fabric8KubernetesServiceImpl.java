package com.eon.cps.jenkins.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
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
		
		List<String> jenkinsNamespaces = getJenkinsNamespaces(k8sClient);
		
		List<Service> jenkinsServices = new ArrayList<>();
		
		for(String ns: jenkinsNamespaces) {
			ServiceList f8AllSvcObj = k8sClient.services().inNamespace(ns).list();
			List<Service> allServices = f8AllSvcObj.getItems();
			List<Service> services = allServices.stream()
					.filter(service -> ("LoadBalancer".equals(service.getSpec().getType())))
					.collect(Collectors.toList());
			jenkinsServices.addAll(services);
		}
		
		return jenkinsServices;

	}

	@Override
	public List<Pod> getPods(Map<String, String> labels, String nameSpace) {
		// TODO - To be injected
		KubernetesClient k8sClient = new DefaultKubernetesClient();
		// TODO - Don't hard-code
		return k8sClient.pods().inNamespace(nameSpace).withLabels(labels).list().getItems();
	}
	
	
	private List<String> getJenkinsNamespaces(KubernetesClient client) {
		NamespaceList nameSpaces = client.namespaces().list();

		List<Namespace> nsList = nameSpaces.getItems();

		List<String> jenkinsNamespaces = new ArrayList<>();

		ArrayList<String> k8sNamesapces = new ArrayList<String>(
				Arrays.asList("default", "kube-public", "kube-system", "monitoring"));

		nsList.stream().forEach(ns -> {
			String currentNs = ns.getMetadata().getName();
			if (!k8sNamesapces.contains(currentNs)) {
				jenkinsNamespaces.add(ns.getMetadata().getName());
			}

		});
		return jenkinsNamespaces;
	}

}
