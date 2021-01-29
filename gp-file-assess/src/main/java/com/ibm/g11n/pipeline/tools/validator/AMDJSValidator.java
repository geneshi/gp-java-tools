package com.ibm.g11n.pipeline.tools.validator;

import java.io.File;

public class AMDJSValidator extends BaseValidator {

    public AMDJSValidator(File tba_file, String type, String bundle_prefix) {
        super(tba_file, type, bundle_prefix);
        this.kvPattern="(^ *.*: *\\n *[\\\"\\'])|(^[ ,\\t,a-z,A-Z,\\_,\\\",0-9,\\(,\\),\\},\\{,\\$,\\},\\',\\,,\\.]*: *\\[*\\n* *[\\\"\\'])";
    }
    
    @Override
    protected boolean preCheck() {
        checkDNTTag();
        return true;
    }

}
