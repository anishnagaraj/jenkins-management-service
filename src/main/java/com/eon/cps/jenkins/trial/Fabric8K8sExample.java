package com.eon.cps.jenkins.trial;

import java.util.List;

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

		NamespaceList myNs = client.namespaces().list();

		ServiceList myServices = client.services().inNamespace("jenkins").list();

		// ServiceList myNsServices = client.services().inNamespace("default").list();

		// Service service = myServices.getItems().get(0);
		// service.getSpec().getType();

		// System.out.println(s);

		List<Service> services = myServices.getItems();

		for (Service service : services) {

			String serviceName = service.getMetadata().getName();

			if ("jenkins2".equals(serviceName)) {

				PodList podList = client.pods().inNamespace("jenkins").withLabels(service.getMetadata().getLabels()).list();
				List<Pod> pods = podList.getItems();
				for (Pod pod : pods) {
					System.out.println(pod.getStatus().getPhase());
					System.out.println(pod.getStatus().getMessage());
				}

			}
		}

	}

}
