package extemp;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * The MenuBar class displays the menu for the GUI. It is responsible for all
 * event handling related to the menu buttons.
 */
public class MenuBar extends JMenuBar {

  /**
   * Serial id to make the compiler happy.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Hidden menu that appears when index articles is clicked.
   */
  private JPopupMenu menu = new JPopupMenu();

  /**
   * Creates a menu bar with an index and search button.
   */
  public MenuBar() {
    super();

    final FlowLayout flowLay = new FlowLayout(FlowLayout.LEFT);

    setLayout(flowLay);

    final JButton btnIndexArticles = new JButton("Index Articles");
    add(btnIndexArticles);

    final JMenuItem btnPastWeek = new JMenuItem("Past Week");
    menu.add(btnPastWeek);

    final JMenuItem btnPastMonth = new JMenuItem("Past Month");
    menu.add(btnPastMonth);

    final JMenuItem btnPast3Months = new JMenuItem("Past 3 Months");
    menu.add(btnPast3Months);

    final JMenuItem btnPast6Months = new JMenuItem("Past 6 Months");
    menu.add(btnPast6Months);

    final JMenuItem btnSinceIndex = new JMenuItem("Since Previous Index");
    menu.add(btnSinceIndex);
    
    final JButton btnSettings = new JButton("Settings");
    add(btnSettings);

    final JButton btnHelp = new JButton("Help");
    add(btnHelp);

    btnIndexArticles.addActionListener(action -> showPopup(action));

    btnPastWeek
        .addActionListener(action -> new BackgroundTask("Past Week").execute());

    btnPastMonth
        .addActionListener(action -> new BackgroundTask("Past Month").execute());

    btnPast3Months
        .addActionListener(action -> new BackgroundTask("Past 3 Months").execute());

    btnPast6Months
        .addActionListener(action -> new BackgroundTask("Past 6 Months").execute());

    btnSinceIndex
        .addActionListener(action -> new BackgroundTask("Since Previous").execute());
  }

  /**
   * Shows the menu when index articles is clicked.
   * 
   * @param event
   *          Event to find the location to display the popup menu.
   */
  private void showPopup(ActionEvent event) {
    // Get the event source
    Component pressed = (Component) event.getSource();

    // Get the location of the click
    Point location = pressed.getLocationOnScreen();

    menu.show(this, 0, 0);
    menu.setVisible(true);

    // Now set the location of the menu relative to the screen
    menu.setLocation(location.x, location.y + pressed.getHeight());
  }
}
