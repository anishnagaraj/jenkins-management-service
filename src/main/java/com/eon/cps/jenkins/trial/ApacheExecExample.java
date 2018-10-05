package com.eon.cps.jenkins.trial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import com.eon.cps.jenkins.model.JenkinsInstance;

public class ApacheExecExample {
    int iExitValue;
    String sCommandString;

    public void runScriptUsingApache(String command){
        sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        //CommandLine oCmdLine = new CommandLine(command);
        //oCmdLine.addArgument(null, false);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
    }
    
	public void runScript() {
		String cmd = "/Users/anish.nagaraj/development/cps/maintain-jenkins/whoiam.sh";
		ProcessBuilder pb = new ProcessBuilder(cmd);

		try {
			Process process = pb.start();
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			String result = builder.toString();
			System.out.print(result);
			System.out.println("end of script execution");
		} catch (IOException e) {
			System.out.print("error");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

    public static void main(String args[]){
        ApacheExecExample testScript = new ApacheExecExample();
        //testScript.runScript();
        testScript.runScriptUsingApache("sh /Users/anish.nagaraj/development/cps/maintain-jenkins/getPods.sh");
    }
}
