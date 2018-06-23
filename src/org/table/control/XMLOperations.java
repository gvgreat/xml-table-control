/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.i18n.swing.locale.AbstractI18NMessages;
import org.xml.sax.SAXException;

/**
 * @author G. Vaidhyanathan
 */
public interface XMLOperations<T> {

  /**
   * Loads the xml template for the operations.
   * <P>
   * This is the default xml with or without values
   * @throws Exception
   */
  void loadXMLTemplate() throws Exception;

  /**
   * Loads an xml file into the application
   * @param fileName
   *          the xml file name
   * @throws Exception
   */
  void loadXML(String fileName) throws Exception;

  /**
   * Saves the xml file
   * @param fileName
   *          the xml file name
   */
  void saveXML(String fileName);

  /**
   * Gets the table element associated with the operations
   * @return the table element
   */
  XMLTableDataModel<T> getTableDataModel();

  /**
   * Adds a table data listener
   * @param listener
   *          TableDataListener
   */
  void addTableDataListener(XMLTableDataListener listener);

  /**
   * Removes a table data listener
   * @param listener
   *          TableDataListener
   */
  void removeTableDataListener(XMLTableDataListener listener);

  /**
   * Notifies the listeners attached to this, that the data has changed
   * @throws SAXException
   * @throws IOException
   * @throws JAXBException
   */
  void notifyDataChanged() throws SAXException, JAXBException, IOException;

  /**
   * Validates the xml data manipulated
   * @throws JAXBException
   * @throws IOException
   * @throws SAXException
   */
  void validateData() throws JAXBException, IOException, SAXException;

  /**
   * Gets the xml schema file (xsd) for the operations to validate
   */
  InputStream getSchemaFile();

  /**
   * Validates the xml data manipulated
   * @param object
   *          the object to be validated
   * @throws JAXBException
   * @throws IOException
   * @throws SAXException
   */
  void validateData(Object object) throws JAXBException, IOException, SAXException;

  /**
   * Gives the XSL Transformation file
   * @return
   */
  InputStream getTransformFile();

  /**
   * Transforms the XML file with the XSL
   * @param fileName
   *          the file to be saved
   * @see #getTransformFile()
   */
  void transformXML(String fileName);

  /**
   * Gets the help file for this operation
   * @return the help file
   */
  InputStream getHelpFile();

  /**
   * @return the JAXBContext associated with this operation
   */
  JAXBContext getContext();

  /**
   * @return the Short title for the operations (Acronym or abbreviation)
   */
  String getShortTitle();

  /**
   * @return the title for the operations
   */
  String getTitle();

  /**
   * Gets the template file for this operation
   * @return
   */
  InputStream getTemplateFile();

  /**
   * Messages file used for Internationalization
   * @return  AbstractI18NMessages
   */
  AbstractI18NMessages getMessages();
}
