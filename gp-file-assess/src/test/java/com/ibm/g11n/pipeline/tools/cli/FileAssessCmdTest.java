/*  
 * Copyright IBM Corp. 2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.g11n.pipeline.tools.cli;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @see ListBundlesCmd
 * @author srl
 *
 */
public class FileAssessCmdTest {
    
    static String cresFilePath;
    @BeforeClass
    public static void test1() {
        String GPCONFIG_FILE = System.getProperty("GP_CONFIG_FILE", "../test-gpconfig.json");
        cresFilePath = new File(GPCONFIG_FILE).getAbsolutePath();
    }
    
    @Test
    public void testJson() {
        File resFolder = new File("src/test/resource/json/");
        for(File resFile:resFolder.listFiles()) {
            System.out.println(String.format("Checking [%s]...", resFile.getName()));
            String[] cmd = {"assess-file", "-j", cresFilePath, "-t", "JSON", "-f", resFile.getAbsolutePath()};
            GPCmd.main(cmd);
        }
    }
    
    @Test
    public void testJava() {
        File resFolder = new File("src/test/resource/java/");
        for(File resFile:resFolder.listFiles()) {
            System.out.println(String.format("Checking [%s]...", resFile.getName()));
            String[] cmd = {"assess-file", "-j", cresFilePath, "-t", "JAVA", "-b", "ABC", "-f", resFile.getAbsolutePath()};
            GPCmd.main(cmd);
        }
    }
}
