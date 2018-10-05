package com.eon.cps.jenkins.trial;

import java.net.URI;
import java.net.URISyntaxException;

import com.offbytwo.jenkins.JenkinsServer;

public class TestJenkinsAPI {

	public static void main(String[] args) {
		try {
			JenkinsServer jenkins = new JenkinsServer(new URI("https://cps-hcl-sjs-workshop.westeurope.cloudapp.azure.com/jenkins2"), "admin", "xyz");
			
			jenkins.getVersion();
			
			System.out.println(jenkins.getVersion());
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
