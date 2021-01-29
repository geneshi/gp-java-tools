package com.ibm.g11n.pipeline.tools.validator;

import java.io.File;

public class GlobalizedJSValidator extends BaseValidator {

    public GlobalizedJSValidator(File tba_file, String type, String bundle_prefix) {
        super(tba_file, type, bundle_prefix);
        this.kvPattern = "\"(.*?)\" {0,3}:\\s{0,20}(?:(?:\"(.*?)\")|(?:\\[[^:]*?\\]))";//including both normal entry and string array
    }
    
    @Override
    protected boolean preCheck() {
        checkDNTTag();
        return true;
    }
    
}
