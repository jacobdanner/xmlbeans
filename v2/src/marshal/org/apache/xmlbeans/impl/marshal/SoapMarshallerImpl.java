/*   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.xmlbeans.impl.marshal;

import org.apache.xmlbeans.SoapMarshaller;
import org.apache.xmlbeans.EncodingStyle;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.binding.bts.BindingLoader;

import javax.xml.stream.XMLStreamReader;
import javax.xml.namespace.QName;

class SoapMarshallerImpl implements SoapMarshaller
{
    SoapMarshallerImpl(BindingLoader loader,
                       RuntimeBindingTypeTable typeTable,
                       EncodingStyle encodingStyle)
    {
    }

    public XMLStreamReader marshalType(Object obj,
                                       QName elementName,
                                       QName schemaType,
                                       String javaType,
                                       XmlOptions options)
        throws XmlException
    {
        throw new AssertionError("UNIMP: this=" + this);
    }

    public XMLStreamReader marshalReferenced(Object obj,
                                             QName elementName,
                                             QName schemaType,
                                             String javaType,
                                             XmlOptions options)
        throws XmlException
    {
        throw new AssertionError("UNIMP: this=" + this);
    }
}
