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
package org.apache.xmlbeans.test.performance.xerces;

import java.io.CharArrayReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.xmlbeans.test.performance.utils.Constants;
import org.apache.xmlbeans.test.performance.utils.PerfUtil;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class POReadAllXercesSax
{
  public static void main(String[] args) throws Exception
  {

    final int iterations =  Constants.ITERATIONS;
    String filename;

    if(args.length == 0){
      filename = Constants.PO_INSTANCE_1;
    }
    else if(args[0].length() > 1){
      filename = Constants.XSD_DIR+Constants.P+args[0];
    }
    else{
      switch( Integer.parseInt(args[0]) )
      {
      case 1: filename = Constants.PO_INSTANCE_1; break;
      case 2: filename = Constants.PO_INSTANCE_2; break;  
      case 3: filename = Constants.PO_INSTANCE_3; break;
      case 4: filename = Constants.PO_INSTANCE_4; break;
      case 5: filename = Constants.PO_INSTANCE_5; break;
      case 6: filename = Constants.PO_INSTANCE_6; break;
      case 7: filename = Constants.PO_INSTANCE_7; break;
      default: filename = Constants.PO_INSTANCE_1; break;
      }
    }

    POReadAllXercesSax test = new POReadAllXercesSax();
    PerfUtil util = new PerfUtil();
    long cputime;
    int hash = 0;

    // get the xmlinstance
    char[] chars = util.fileToChars(filename);
        
    // warm up the vm
    cputime = System.currentTimeMillis();
    for(int i=0; i<iterations; i++){
      CharArrayReader reader = new CharArrayReader(chars);     
      hash += test.run(reader);
    }
    cputime = System.currentTimeMillis() - cputime;

    // run it again for the real measurement
    cputime = System.currentTimeMillis();
    for(int i=0; i<iterations; i++){
      CharArrayReader reader = new CharArrayReader(chars);    
      hash += test.run(reader);
    }
    cputime = System.currentTimeMillis() - cputime;
    
      
    // print the results
    System.out.print(Constants.DELIM+test.getClass().getSimpleName()+" filesize="+chars.length+" ");
    System.out.print("hash "+hash+" ");
    System.out.print("time "+cputime+"\n");
  }

  private int run(CharArrayReader reader) throws Exception 
  {
    // "unmarshall" the xml instance
    InputSource is = new InputSource(reader);
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    factory.setValidating(false);
    SAXParser parser = factory.newSAXParser();
    MyHandler handler = new MyHandler();
    // the handler will tally the string length sum
    parser.parse(is, handler);
   
    return handler.getStringLengthSum();
  }

  // SAX event handler class
  public static class MyHandler extends DefaultHandler
  {
    private int iStringSum = 0;
    private float sum = 0;
    private boolean inStringForSum = false;
    private boolean inPrice = false;

    public void startElement(String uri, 
                             String localName, 
                             String qName, 
                             Attributes attributes) throws SAXException
    {
      
      if(uri.compareTo(Constants.PO_NS) == 0 &&
         (localName.compareTo(Constants.sAddress) == 0 ||
         localName.compareTo(Constants.sName) == 0 ||
          localName.compareTo(Constants.sDesc) == 0) )
      {
        inStringForSum = true;
        return;
      }

      if(uri.compareTo(Constants.PO_NS) == 0 &&
         localName.compareTo(Constants.sPrice) == 0)
      {
        inPrice = true;
        return;
      }

    }

    public void endElement(String uri, 
                           String localName, 
                           String qName) throws SAXException
    {
      if(uri.compareTo(Constants.PO_NS) == 0 &&
         (localName.compareTo(Constants.sAddress) == 0 ||
         localName.compareTo(Constants.sName) == 0 ||
          localName.compareTo(Constants.sDesc) == 0) )
      {
        inStringForSum = false;
        return;
      }

      if(uri.compareTo(Constants.PO_NS) == 0 &&
         localName.compareTo(Constants.sPrice) == 0)
      {
        inPrice = false;
        return;
      }

    }

    public void characters(char[] ch,
                           int start,
                           int length) throws SAXException
    {
      if(inStringForSum){
        //System.out.println("characters length = "+length);
        iStringSum += length;
      }
      else if(inPrice){
        sum += Float.parseFloat(new String(ch,start,length));
      }
    }

    public int getStringLengthSum(){return iStringSum;}
  }
}
