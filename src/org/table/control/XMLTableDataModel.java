/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;

/**
 * The XMLTableDataModel interface specifies the methods that any model (object) that wants to be represented in a table
 * form.
 * @author G. Vaidhyanathan
 */
public interface XMLTableDataModel<T> extends Serializable {

  /**
   * Gets the list of column names to be shown in the table
   * @return list of column names
   */
  List<String> getColumnNames();

  /**
   * Adds a row to the model
   * @param row
   *          the row to be added to the model
   * @throws JAXBException
   */
  void addRow(T row) throws JAXBException;

  /**
   * Removes a row from the model, specifying the id
   * @param row
   *          the row to be removed
   */
  boolean removeRow(T row);

  /**
   * Updates the row
   * @param row
   *          the row to be updated
   */
  void updateRow(T row);

  /**
   * Gets the list of all rows from the model.
   * <P>
   * This should usually give a list of Sub Elements in the xml file
   * @return list of all rows
   */
  List<?> getAllRows();

  /**
   * Children can override this with the row element to be added to the table
   * @return T
   */
  T getNewElement();

  /**
   * Gives the type of the generic element
   * @return class of the type
   */
  Class<? extends T> getTypeClass();

  /**
   * Gets the total value of all the factors calculated
   * @return the total value
   */
  double getTotalValue();

  /**
   * Returns false if the field should not be editable while adding / updating
   * @param fieldName
   *          the name of the field
   * @return true if the field can be edited
   */
  boolean isEnabled(String fieldName);

  /**
   * Displays any additional data on the bottom of the panel. Can be empty string if nothing.
   * @return additional data required
   */
  String displayData();
  
  /**
   * @return true if new rows can be added
   */
  boolean canAddRow();
  
  /**
   * @return the calculated value that can be used for final calculation
   */
  double getCalculatedValue();
}
