/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlType;

/**
 * <code>AbstractXMLTableDataModel</code> represents the xml file.
 * <P>
 * JAXB classes can be created for the xml files using the "xjc" command on the respective schema file.
 * <P>
 * The root element that is created from the xjc can extend AbstractXMLTableDataModel to be represented as a table.
 * <P>
 * For example, consider the following xml file "employees.xml"
 * 
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;employees xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; xsi:noNamespaceSchemaLocation=&quot;employees.xsd&quot;&gt;
 *    &lt;employee&gt;
 *      &lt;name&gt;GV&lt;/name&gt;
 *      &lt;joining-date&gt;23-04-2007&lt;/joining-date&gt;
 *      &lt;department&gt;EPM&lt;/department&gt;
 *        ...
 *        ...
 *    &lt;/employee&gt;
 *    &lt;employee&gt;
 *      ...
 *      ...
 *    &lt;/employee&gt;
 * &lt;/employees&gt;
 * </pre>
 * 
 * <P>
 * Performing "xjc" on this will create ObjectFactory.java, Employees.java, and Employee.java. "Employees" is the class
 * of interest to be displayed in a table, and can inherit AbstractXMLTableDataModel. And a new class for XMLOperations,
 * say, <code>EmployeeOperations</code> can be added.
 * <P>
 * EmployeeOperations.getTableDataModel() shall return Employees instance
 * <P>
 * Also, the xml, xsd and xslt files can be placed in the same package that of the classes to enable the streams
 * required to be returned.
 * @see
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public abstract class AbstractXMLTableDataModel<T> implements XMLTableDataModel<T> {

  protected final Map<String, Boolean> editableMap = new HashMap<String, Boolean>(1);

  protected AbstractXMLTableDataModel() {
    populateMap();
  }

  /**
   * 
   */
  @SuppressWarnings("boxing")
  private void populateMap() {
    for (String fieldName : getTypeClass().getAnnotation(XmlType.class).propOrder()) {
      editableMap.put(fieldName, true);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#getAllRows()
   */
  @Override
  public List<T> getAllRows() {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#getColumnNames()
   */
  @Override
  public List<String> getColumnNames() {
    return Collections.emptyList();
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#removeRow(java.lang.Object)
   */
  @Override
  public boolean removeRow(T row) {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataModel#updateRow(java.lang.Object)
   */
  @Override
  public void updateRow(T row) {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#addRow(java.lang.Object)
   */
  @Override
  public void addRow(T row) throws JAXBException {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#getNewElement()
   */
  @Override
  public T getNewElement() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#getTotalValue()
   */
  @Override
  public double getTotalValue() {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#isEnabled(java.lang.String)
   */
  @SuppressWarnings("boxing")
  @Override
  public boolean isEnabled(String fieldName) {
    return editableMap.get(fieldName);
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#displayData()
   */
  @Override
  public String displayData() {
    return ControlConstants.EMPTY_STRING;
  }

  /**
   * @return true if new rows can be added
   */
  @Override
  public boolean canAddRow() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.XMLTableDataModel#getCalculatedValue()
   */
  @Override
  public double getCalculatedValue() {
    return 0;
  }
}
