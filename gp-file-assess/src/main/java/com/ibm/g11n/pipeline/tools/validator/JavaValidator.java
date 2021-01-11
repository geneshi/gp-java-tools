package com.ibm.g11n.pipeline.tools.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaValidator extends BaseValidator {
    public JavaValidator(File tba_file, String type, String bundle_prefix) {
        super(tba_file, type, bundle_prefix);
    }

    @Override
    protected boolean preCheck() {
        checkDNTTag();
        checkMSGFormatTag();
        return true;
    }

    protected void checkMSGFormatTag() {
        // NLS_MESSAGEFORMAT comments:
        // https://w3-03.ibm.com/globalization/page/2089#IBMJDK21
        String msgFormatPattern = "^\\s*?#\\s*?(NLS_MESSAGEFORMAT_(?:ALL|NONE|SKIP|VAR))\\s*$";
        Pattern r = Pattern.compile(msgFormatPattern, Pattern.MULTILINE);
        Matcher m = r.matcher(this.content);

        Set<String> msgFormatTagFound = new HashSet<String>();
        while (m.find()) {
            msgFormatTagFound.add(m.group(1));
        }

        // Below rules and messages are to be refined
        if (msgFormatTagFound.size() > 1) {
            System.err.println(String.format(
                    "Warning - More than one NLS_MessageFormat comments found: [%s]. Please separate them into different files.",
                    msgFormatTagFound));
            return;
        } else if (msgFormatTagFound.size() == 0) {
            System.err.println(String.format(
                    "Warning - No NLS_MessageFormat comment found. Cannot perform file type check for [%s].",
                    this.type));
            return;
        } else { // size == 1
            String comment = (String) msgFormatTagFound.toArray()[0];
            if (!isMatchingFileType(comment)) {
                System.err.println(String.format("Warning - File type [%s] does not match comment [%s]. Please check.",
                        this.type, comment));
                return;
            }
        }
        System.out.println("Pass - Message Format comment checked");
        return;
    }

    private boolean isMatchingFileType(String comment) {
        // Mapping info:
        // https://w3.ibm.com/w3publisher/gssc-wg-gp/getting-started-for-gssc-pm/supported-pii-format
        Map<String, String> typeCommentMap = new HashMap<String, String>();
        typeCommentMap.put("JAVA", "NLS_MESSAGEFORMAT_VAR");
        typeCommentMap.put("JAVAUTF8", "NLS_MESSAGEFORMAT_VAR");
        typeCommentMap.put("JAVAMSG", "NLS_MESSAGEFORMAT_ALL");
        typeCommentMap.put("JAVAMSGUTF8", "NLS_MESSAGEFORMAT_ALL");

        return typeCommentMap.get(this.type).equals(comment);
    }

    @Override
    protected int countInSource() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(this.tba_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int countInSource = p.size();
        return countInSource;
    }
}
