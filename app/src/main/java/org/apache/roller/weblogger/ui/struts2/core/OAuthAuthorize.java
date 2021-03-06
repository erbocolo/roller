/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */
package org.apache.roller.weblogger.ui.struts2.core;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.pojos.User;
import org.apache.roller.weblogger.ui.struts2.util.UIAction;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;


/**
 * Allow user to authorized OAuth access to his/her account.
 */
public class OAuthAuthorize extends UIAction implements ServletRequestAware {
    private static Log log = LogFactory.getLog(OAuthAuthorize.class);
    private String appDesc = null;
    private String token = null;
    private String callback = null;


    public OAuthAuthorize() {
        this.pageTitle = "oauthAuthorize.title";
    }
    
    
    // override default security, we do not require an action weblog
    @Override
    public boolean isWeblogRequired() {
        return false;
    }


    @SkipValidation
    @Override
    public String execute() {
        try {
            User ud = getAuthenticatedUser();


        } catch (Exception ex) {
            log.error("ERROR fetching user information", ex);
        }

        return SUCCESS;
    }

    /**
     * @return the appDesc
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the callback
     */
    public String getCallback() {
        return callback;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return getAuthenticatedUser().getUserName();
    }

    public void setServletRequest(HttpServletRequest request) {
        this.appDesc = (String)request.getAttribute("CONS_DESC");
        this.token = (String)request.getAttribute("TOKEN");
        this.callback = (String)request.getAttribute("CALLBACK");
        if (this.getCallback() == null) {
            this.callback = "";
        }
    }
}
