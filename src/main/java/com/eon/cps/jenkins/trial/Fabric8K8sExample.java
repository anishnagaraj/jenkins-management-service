package com.eon.cps.jenkins.trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Fabric8K8sExample {

	public static void main(String[] args) {
		KubernetesClient client = new DefaultKubernetesClient();

		/*List<String> jenkinsNamespaces = getJenkinsNamespaces(client);
		System.out.println(jenkinsNamespaces);*/

		//getJenkinsServices(client);

	}

	private static List<String> getJenkinsNamespaces(KubernetesClient client) {
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

	private static void getJenkinsServices(KubernetesClient client) {
		ServiceList myServices = client.services().inNamespace("jenkins").list();

		// ServiceList myNsServices = client.services().inNamespace("default").list();

		// Service service = myServices.getItems().get(0);
		// service.getSpec().getType();

		// System.out.println(s);

		List<Service> services = myServices.getItems();

		for (Service service : services) {

			String serviceName = service.getMetadata().getName();

			if ("jenkins2".equals(serviceName)) {

				PodList podList = client.pods().inNamespace("jenkins").withLabels(service.getMetadata().getLabels())
						.list();
				List<Pod> pods = podList.getItems();
				for (Pod pod : pods) {
					System.out.println(pod.getStatus().getPhase());
					System.out.println(pod.getStatus().getMessage());
				}

			}
		}
	}

}
