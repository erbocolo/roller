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
/*
 * WeblogEntrySet.java
 *
 * Created on January 17, 2006, 12:44 PM
 */

package org.apache.roller.weblogger.webservices.adminprotocol.sdk;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/**
 * This class describes a set of member entries. 
 *
 * @author jtb
 */
public class MemberEntrySet extends EntrySet {
    interface Tags {
        String MEMBERS = "members";
    }       
        
    public MemberEntrySet(String urlPrefix) {
        setHref(urlPrefix + "/" + Types.MEMBERS);        
    }    

    public MemberEntrySet(Document d, String urlPrefix) throws UnexpectedRootElementException {
        populate(d, urlPrefix);
    }
    
    public MemberEntrySet(InputStream stream, String urlPrefix) throws JDOMException, IOException, UnexpectedRootElementException {               
        SAXBuilder sb = new SAXBuilder();
        Document d = sb.build(stream);

        populate(d, urlPrefix);        
    }    
    
    private void populate(Document d, String urlPrefix) throws UnexpectedRootElementException {
        Element root = d.getRootElement();
        String rootName = root.getName();
        if (!rootName.equals(Tags.MEMBERS)) {
            throw new UnexpectedRootElementException("ERROR: Incorrect root element", Tags.MEMBERS, rootName);
        }
        List<Element> members = root.getChildren(MemberEntry.Tags.MEMBER, NAMESPACE);
        if (members != null) {
            List<MemberEntry> entries = new ArrayList<MemberEntry>();
            for (Element member : members) {
                MemberEntry entry = new MemberEntry(member, urlPrefix);
                entries.add(entry);
            }
            setEntries(entries.toArray(new Entry[0]));
        }
        setHref(urlPrefix + "/" + Types.MEMBERS);
    }
        
    public String getType() {
        return Types.MEMBERS;
    }    
}
