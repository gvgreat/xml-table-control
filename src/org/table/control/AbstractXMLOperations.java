/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.i18n.swing.locale.AbstractI18NMessages;
import org.xml.sax.SAXException;

/**
 * Class AbstractXMLOperations does the operations on an xml file that are specified by XMLOperations.
 * <P>
 * Any class that want to represent the data can extend this by simply overriding the following methods.
 * <ul>
 * <li>loadXML(String fileName)
 * <li>XMLTableDataModel<T> getTableDataModel()
 * <li>InputStream getSchemaFile()
 * <li>InputStream getTransformFile()
 * <li>InputStream getHelpFile()
 * </ul>
 * XMLTableDataModel<T> is the table model of the xml file that does the CRUD operations on the xml file itself.
 * @see XMLTableDataModel
 * @see XMLOperations
 * @author G. Vaidhyanathan
 */
public abstract class AbstractXMLOperations<T> implements XMLOperations<T> {
  private List<XMLTableDataListener> listeners = new ArrayList<XMLTableDataListener>(1);
  private T templateModel;
  private final ClassLoader classLoader = getClass().getClassLoader();
  private JAXBContext context = null;
  private final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
  // set up XSLT transformation
  private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
  private Schema schema;
  private Transformer transformer;
  private Validator validator;

  @SuppressWarnings("unchecked")
  protected AbstractXMLOperations() {
    try {
      context = JAXBContext.newInstance(getClass().getPackage().getName(), classLoader);
      schema = schemaFactory.newSchema(new StreamSource(getSchemaFile()));
      transformer = transformerFactory.newTransformer(new StreamSource(getTransformFile()));
      validator = schema.newValidator();
      templateModel = (T) unmarshal(null);
    } catch (JAXBException ex) {
      ex.printStackTrace();
    } catch (SAXException ex) {
      ex.printStackTrace();
    } catch (TransformerConfigurationException ex) {
      ex.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#addTableDataListener(org.table.control.TableDataListener)
   */
  @Override
  public void addTableDataListener(XMLTableDataListener listener) {
    listeners.add(listener);
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#removeTableDataListener(org.table.control.TableDataListener)
   */
  @Override
  public void removeTableDataListener(XMLTableDataListener listener) {
    listeners.remove(listener);
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#notifyDataChanged()
   */
  @Override
  public void notifyDataChanged() throws SAXException, JAXBException, IOException {
    validateData();
    for (XMLTableDataListener listener : listeners) {
      listener.dataChanged(getTableDataModel());
    }
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLOperations#loadXMLTemplate()
   */
  @Override
  public void loadXMLTemplate() throws Exception {
    loadXML(null);
  }

  protected Object unmarshal(String fileName) throws JAXBException {
    if (fileName == null && templateModel != null) {
      return templateModel;
    }
    Unmarshaller unmarshaller = context.createUnmarshaller();
    InputStream toUnmarshal = null;
    Object returnValue = null;
    try {
      toUnmarshal = fileName == null ? getTemplateFile() : new FileInputStream(fileName);
      unmarshaller.setSchema(schema);
      returnValue = unmarshaller.unmarshal(toUnmarshal);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    return returnValue;
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLOperations#saveXML(java.lang.String)
   */
  @Override
  public void saveXML(String fileName) {
    try {
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      FileOutputStream fos = new FileOutputStream(fileName);
      marshaller.marshal(getTableDataModel(), fos);
      fos.flush();
      fos.close();
    } catch (JAXBException ex) {
      ex.printStackTrace();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Validates the xml data manipulated
   * @throws JAXBException
   * @throws IOException
   * @throws SAXException
   */
  public void validateData() throws JAXBException, IOException, SAXException {
    validateData(getTableDataModel());
  }

  /**
   * Validates the xml data manipulated
   * @param object
   *          the object to be validated
   * @throws JAXBException
   * @throws IOException
   * @throws SAXException
   */
  public void validateData(Object object) throws JAXBException, IOException, SAXException {
    validator.validate(new JAXBSource(context, object));
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLOperations#transformXML()
   */
  @Override
  public void transformXML(String fileName) {
    try {
      File transformedFile = new File(fileName);
      transformer.transform(new JAXBSource(context, getTableDataModel()), new StreamResult(transformedFile));
      Desktop.getDesktop().open(transformedFile);
    } catch (TransformerException ex) {
      ex.printStackTrace();
    } catch (JAXBException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @return the JAXBContext associated with this operation
   */
  public JAXBContext getContext() {
    return context;
  }

  /**
   * Messages file used for Internationalization. Default implementation returns null
   * @return AbstractI18NMessages
   */
  public AbstractI18NMessages getMessages() {
    return null;
  }
}
