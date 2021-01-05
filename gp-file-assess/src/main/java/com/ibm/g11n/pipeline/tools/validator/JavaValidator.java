package com.ibm.g11n.pipeline.tools.validator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaValidator extends BaseValidator {

    protected String javaResPattern;
    protected ResourceBundle javaBundle;

    public JavaValidator(File tba_file, String type) {
        super(tba_file, type);
        this.javaResPattern = "^(?!\\s*#|!)\\s*(.*?[^\\\\])\\s*[=:]((?:.*[^\\\\]\\\\(?:\\\\\\\\)*\\s*\\n)*.*)";
        try {
            URL[] urls = { tba_file.getParentFile().toURI().toURL() };
            ClassLoader loader = new URLClassLoader(urls);

            this.javaBundle = ResourceBundle.getBundle(getBundleName(tba_file.getName()), Locale.getDefault(), loader);

        } catch (MalformedURLException e) {// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    private boolean check_duplicated_key() {
//        ArrayList<String> kl = new ArrayList<String>();
//
//        Pattern r = Pattern.compile(this.javaResPattern, Pattern.MULTILINE);
//        Matcher m = r.matcher(this.content);
//        while (m.find()) {
//            String key = m.group(1);
//            if (kl.contains(key)) {
//                System.err.println(
//                        "Failed - Duplicated key found in Java file, please remove them and try again: " + m.group(1));
//                return false;
//            } else {
//                kl.add(key);
//            }
//        }
//        System.out.println("Pass - Not found duplicated keys in Java file");
//        return true;
//    }

    @Override
    protected int countPattern(String str, String pattern) {
        int count = 0;
        Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
        Matcher m = r.matcher(str);
        while (m.find())
            count++;
        return count;
    }

    protected int countBundle(ResourceBundle theBundle) {
        return theBundle.keySet().size();
    }

    private String getBundleName(String propFilename) {
        // propFilename should be ended with ".properties". If not, return the
        // original filename
        if (propFilename.contains(".properties"))
            return propFilename.substring(0, propFilename.lastIndexOf(".")); // remove
                                                                             // suffix

        return propFilename;
    }

    @Override
    protected void checkPIICount() {
        // int countInSource = countPattern(this.content, this.javaResPattern);
        int countInSource = countBundle(this.javaBundle);
        int countInXliff = countPattern(getFileContent(new File(this.xliffFile)), "<unit id=");

        if (countInSource == countInXliff)
            System.out.println("Pass - PII count checked");
        else
            System.err
                    .println("Failed - PII count mismatch, please investigate: " + countInSource + ":" + countInXliff);
    }

//    @Override
//    protected boolean preCheck() {
//        return this.check_duplicated_key();
//    }

}
