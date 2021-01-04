package com.ibm.g11n.pipeline.tools.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JavaValidator extends BaseValidator {

    public JavaValidator(File tba_file, String type, String bundle_prefix) {
        super(tba_file, type, bundle_prefix);
    }

    @Override
    protected void checkPIICount() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(this.tba_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int countInSource = p.size();
        int countInXliff = countPattern(getFileContent(new File(this.xliffFile)), "<unit id=");

        if (countInSource == countInXliff)
            System.out.println("Pass - PII count checked");
        else
            System.err.println("Failed - PII count mismatch, please investigate: " + countInSource + ":" + countInXliff);
    }
}
