package com.ibm.g11n.pipeline.tools.cli;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ibm.g11n.pipeline.client.ServiceClient;
import com.ibm.g11n.pipeline.resfilter.ResourceFilter;
import com.ibm.g11n.pipeline.resfilter.ResourceFilterFactory;
import com.ibm.g11n.pipeline.tools.validator.BaseValidator;
import com.ibm.g11n.pipeline.tools.validator.JavaValidator;
import com.ibm.g11n.pipeline.tools.validator.JsonValidator;

/**
 * Assess a file to find the gap to onboard GP
 * 
 * @author Gene Shi
 */
@Parameters(commandDescription = "Assess a file to find the gap to onboard GP")
public class FileAssessCmd extends BaseCmd {
    @Parameter(
            names = {"-t", "-type"},
            description = "Resource file type",
            required = true)
    private String type;

    @Parameter(
            names = {"-f", "-file"},
            description = "File name to be imported",
            required = true)
    private String fileName;
    
    @Parameter(
            names = {"-b", "-bundle_prefix"},
            description = "The prefix of a bundle name",
            required = false)
    private String bundle_prefix;

    
    private String detectType(String fileName) {
        //TODO
        return "";
    }
    
    private boolean checkType(String type) {
        ResourceFilter filter = ResourceFilterFactory.getResourceFilter(type);
        return  filter == null ? false : true;
    }
    
    @Override
    protected void _execute() {
        File tba_file = new File(fileName);
        if(tba_file.exists() && tba_file.isFile()) {
            if(checkType(type)) {
                ServiceClient gpClient = getClient();
                BaseValidator validator = null;
                switch(type) {
                case "JSON":
                    validator = new JsonValidator(tba_file, type, bundle_prefix);
                    break;
                case "JAVA":
                case "JAVAUTF8":
                case "JAVAMSG":
                case "JAVAMSGUTF8":
                    validator = new JavaValidator(tba_file, type, bundle_prefix);
                    break;
                default:
                    validator = new BaseValidator(tba_file, type, bundle_prefix);
                }
                validator.check(jsonCreds, gpClient);
            } else {
                System.err.printf("Failed - Resource filter for %s is not available.", type);
            }
            
        } else {
            System.err.println("Failed - File is not a file or does not exist: " + fileName);
        }

    }

}
