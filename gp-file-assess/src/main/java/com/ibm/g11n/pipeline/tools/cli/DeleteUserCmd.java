/*  
 * Copyright IBM Corp. 2015,2016
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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ibm.g11n.pipeline.client.ServiceException;

/**
 * Deletes a service instance user.
 * 
 * @author Visaahan Anandarajah
 */
@Parameters(commandDescription = "Deletes user with given ID")
final class DeleteUserCmd extends BaseCmd {
    @Parameter(
            names = { "-d", "--id" },
            description = "User ID to delete account for",
            required = true)
    private String id;

    @Override
    protected void _execute() {
        try {
            getClient().deleteUser(id);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        System.out.println("User '" + id + "' was successfully deleted.");
    }
}
