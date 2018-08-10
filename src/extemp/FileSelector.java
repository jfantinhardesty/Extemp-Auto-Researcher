package extemp;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/*
 * 
 */
public class FileSelector {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static JTable myTable;

  private static TableModel dataModel;

  private static JScrollPane tableScrollPane;

  private static List<List<Object>> tableData;

  /**
   * Creates new form JTable_Files_Name
   */
  public FileSelector() {
    myTable = new JTable();
    tableData = new ArrayList<List<Object>>();
    initComponents();
  }

  /**
   * 
   */
  private void initComponents() {

    setTableModel();

    myTable.getColumnModel().getColumn(0).setPreferredWidth(0);
    myTable.getColumnModel().getColumn(1).setPreferredWidth(0);
    myTable.getColumnModel().getColumn(2).setPreferredWidth(0);
    myTable.getColumnModel().getColumn(3).setPreferredWidth(0);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    tableScrollPane = new JScrollPane(myTable);
    tableScrollPane.setPreferredSize(new Dimension(600, 150));
    tableScrollPane.setMinimumSize(new Dimension(600, 150));
    myTable.setAutoCreateRowSorter(true);
    myTable.setShowGrid(true);
    myTable.setColumnSelectionAllowed(true);
  }

  private void setTableModel() {
    final List<Object> columnNames = new ArrayList<Object>(
        Arrays.asList("Number ", "Path", "Title", "Score"));

    dataModel = new AbstractTableModel() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      public int getColumnCount() {
        return columnNames.size();
      }

      public int getRowCount() {
        return tableData.size();
      }

      public Object getValueAt(int row, int col) {
        return tableData.get(row).get(col);
      }

      public String getColumnName(int column) {
        return (String) columnNames.get(column);
      }

      public Class<?> getColumnClass(int column) {
        switch (column) {
        case 0:
          return Integer.class;
        case 1:
          return String.class;
        case 2:
          return String.class;
        case 3:
          return Double.class;
        default:
          return null;
        }
      }
    };

    myTable.setModel(dataModel);

    ((AbstractTableModel) myTable.getModel()).fireTableDataChanged();
  }

  public static void updateData(List<List<Object>> newData) {
    tableData = newData;
    ((AbstractTableModel) myTable.getModel()).fireTableDataChanged();
  }

  public JScrollPane getTable() {
    return tableScrollPane;
  }
}