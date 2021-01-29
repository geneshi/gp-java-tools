/*  
 * Copyright IBM Corp. 2015, 2018
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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;


/**
 * Globalization Pipeline command interface main entry.
 * 
 * @author Yoshito Umaoka
 */
public class GPCmd {
    // short term workaround for TLS version issue on Java 7
    static {
        System.setProperty("https.protocols", "TLSv1.2");
    };

    @Parameters(commandDescription = "Display command help")
    private static class HelpCmd {
    }

    public static void main(String[] args) {
        GPCmd gpCmd = new GPCmd();
        JCommander jc = new JCommander(gpCmd);

        jc.setProgramName("GPCmd");

        jc.addCommand("help", new HelpCmd());

        // bundles
        jc.addCommand("list-bundle", new ListBundlesCmd(), "list");
        jc.addCommand("show-bundle", new ShowBundleCmd(), "show");
        jc.addCommand("create-bundle", new CreateBundleCmd(), "create");
        jc.addCommand("update-bundle", new UpdateBundleCmd(), "update");
        jc.addCommand("delete-bundle", new DeleteBundleCmd(), "delete");
        jc.addCommand("copy-bundle", new CopyBundleCmd(), "copy");
        jc.addCommand("copy-all-bundles", new CopyAllBundlesCmd());
        jc.addCommand("export", new ExportCmd());
        jc.addCommand("import", new ImportCmd());
        jc.addCommand("list-mt-languages", new ListMTLanguagesCmd());
        jc.addCommand("clean-bundles", new CleanBundlesCmd(), "clean");

        // users
        jc.addCommand("list-users", new ListUsersCmd());
        jc.addCommand("create-user", new CreateUserCmd());
        jc.addCommand("delete-user", new DeleteUserCmd());
        jc.addCommand("reset-user-password" , new ResetUserPasswordCmd());

        // documents
        jc.addCommand("list-documents", new ListDocumentsCmd());
        jc.addCommand("show-document", new ShowDocumentCmd());
        jc.addCommand("create-document", new CreateDocumentCmd());
        jc.addCommand("delete-document", new DeleteDocumentCmd());
        jc.addCommand("update-document", new UpdateDocumentCmd());
        jc.addCommand("export-document", new ExportDocumentCmd());
        jc.addCommand("import-document", new ImportDocumentCmd());
        
        jc.addCommand("assess-file", new FileAssessCmd());

        try {
            jc.parse(args);
            String cmdName = jc.getParsedCommand();
            if (cmdName == null || cmdName.equalsIgnoreCase("help")) {
                jc.usage();
            } else {
                BaseCmd parsedCmd = (BaseCmd)jc.getCommands().get(cmdName).getObjects().get(0);
                parsedCmd.execute();
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            jc.usage();
            System.exit(1);
        }
    }
}
