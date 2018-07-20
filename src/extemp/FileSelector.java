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
  public FileSelector(List<List<Object>> tableData) {
    myTable = new JTable();
    this.tableData = tableData;
    initComponents();
  }

  /*
   * 
   */
  private void initComponents() {

    setTableModel();

    myTable.getColumnModel().getColumn(4).setMaxWidth(0);
    myTable.getColumnModel().getColumn(4).setMinWidth(0);
    myTable.getColumnModel().getColumn(4).setPreferredWidth(0);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    tableScrollPane = new JScrollPane(myTable);
    tableScrollPane.setMaximumSize(new Dimension(1200, 1200));
    tableScrollPane.setSize(new Dimension(1200, 1200));
    myTable.setAutoCreateRowSorter(true);
    myTable.setShowGrid(true);
    myTable.setColumnSelectionAllowed(true);
  }

  private void setTableModel() {
    final List<Object> columnNames = new ArrayList<Object>(
        Arrays.asList("Number ", "Path", "Title", "Score", ""));

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
        return (String)columnNames.get(column);
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