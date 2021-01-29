package com.ibm.g11n.pipeline.tools.cli;

import java.util.Set;

import com.ibm.g11n.pipeline.client.ServiceException;

public class CleanBundlesCmd extends BaseCmd {

    @Override
    protected void _execute() {
        try {
            Set<String> bundleIds = getClient().getBundleIds();
            for(String bundleId:bundleIds) {
                getClient().deleteBundle(bundleId);
                System.out.println("Bundle '" + bundleId + "' was successfully deleted.");
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
