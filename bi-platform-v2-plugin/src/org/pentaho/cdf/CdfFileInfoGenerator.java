/*!
* Copyright 2002 - 2013 Webdetails, a Pentaho company.  All rights reserved.
* 
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package org.pentaho.cdf;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IFileInfo;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.api.engine.SolutionFileMetaAdapter;
import org.pentaho.platform.engine.core.solution.FileInfo;
import org.w3c.dom.Document;

/**
 * Parses a Dom4J document and creates an IFileInfo object containing the
 * xcdf info.
 * 
 * @author Will Gorman (wgorman@pentaho.com)
 * @author Aaron Phillips (aphillips@pentaho.com) modified for latest plugin spec
 */
public class CdfFileInfoGenerator extends SolutionFileMetaAdapter {

  private Log logger = LogFactory.getLog(CdfFileInfoGenerator.class);

  public CdfFileInfoGenerator() {
  }

  public IFileInfo getFileInfo(ISolutionFile solutionFile, InputStream in) {
    Document doc = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(in);
      
      String result = "dashboard";  //$NON-NLS-1$
      
      XPath xpath = XPathFactory.newInstance().newXPath();
      String author = (String) xpath.evaluate("/cdf/author", doc, XPathConstants.STRING); //$NON-NLS-1$
      String description = (String) xpath.evaluate("/cdf/description", doc, XPathConstants.STRING); //$NON-NLS-1$
      String icon = (String) xpath.evaluate("/cdf/icon", doc, XPathConstants.STRING); //$NON-NLS-1$
      String title = (String) xpath.evaluate("/cdf/title", doc, XPathConstants.STRING); //$NON-NLS-1$

      IFileInfo info = new FileInfo();
      info.setAuthor(author);
      info.setDescription(description);
      info.setDisplayType(result);
      info.setIcon(icon);
      info.setTitle(title);
      return info;

    } catch (Exception e) {
      logger.error(Messages.getErrorString("CdfFileInfoGenerator.ERROR_0001_PARSING_XCDF"), e); //$NON-NLS-1$
      return null;
    }
  }
}
