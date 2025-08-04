package file2image.UI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

import java.awt.Dimension;

public class MainFrame extends JFrame {

    public static final Dimension Size = new Dimension(400,400);

    public final SpringLayout Lay = new SpringLayout();
    public final JTabbedPane Tabs = new JTabbedPane();

    public MainFrame() {
        super("File2Img");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setPreferredSize(Size);
        setSize(Size);

        setLayout(Lay);
        Tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        Tabs.addTab("Single File", null);
        Tabs.addTab("Multiple Files", null);
        
        Lay.putConstraint(SpringLayout.WEST, Tabs, 0, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.EAST, Tabs, 0, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, Tabs, 0, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, Tabs, 0, SpringLayout.SOUTH, getContentPane());

        add(Tabs);
    }
}
