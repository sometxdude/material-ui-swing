package integration.gui.mock;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;
import mdlaf.utils.MaterialBorders;
import mdlaf.utils.MaterialColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.util.Map;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class DemoGUITest extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGUITest.class);

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOceanicTheme()));
            UIManager.put("Button.mouseHoverEnable", false); //Because the test are more difficulte with effect mouse hover
            JDialog.setDefaultLookAndFeelDecorated(true);
            JFrame.setDefaultLookAndFeelDecorated(false); //not support yet

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private static final DemoGUITest SINGLETON = new DemoGUITest();

    private GroupLayout layoutPanelOne;
    private GroupLayout layoutPanelTwo;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel panelOne = new JPanel();
    private JButtonNoMouseHoverNative buttonDefault = new JButtonNoMouseHoverNative("Ok");
    private JButton buttonUndo = new JButton("Undo");
    private JButton buttonDisabled = new JButton("I'm disabled");
    private JButtonNoMouseHoverNative buttonNormal = new JButtonNoMouseHoverNative("I'm a pure jbutton");
    private ContainerAction containerAction = new ContainerAction();
    private JTextField textFieldUsername = new JTextField();
    private JPasswordField passwordFiled = new JPasswordField();
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem menuItemJFileChooser = new JMenuItem("Choose");
    private JMenu menuFile = new JMenu("File");
    private JMenu themesMenu = new JMenu("Themes");
    private JFileChooser fileChooser;
    private JPanel panelTwo = new JPanel();
    private JTable table = new JTable();
    private JMenuItem gtk = new JMenuItem("GTK");
    private JMenuItem metal = new JMenuItem("Metal");
    private JMenuItem material = new JMenuItem("Material");
    private JMenuItem materialDark = new JMenuItem("Material Oceanic");
    private JMenuItem jmarsDark = new JMenuItem("Jmars Dark");


    private JMenu arrowMenuOne = new JMenu("Root Menu 1");
    private JMenu arrowMenuTwo = new JMenu("Root Menu 2");


    public JMenuItem getMaterialDark() {
        return materialDark;
    }

    public void initComponent() {

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
            Object key = entry.getKey();
            if (key instanceof String) {
                String keyString = (String) key;
                if (keyString.contains("TabbedPane")) {
                    LOGGER.debug("Key: " + keyString + " value: " + entry.getValue());
                }
            }
        }
        buttonDefault.setName("buttonDefault");
        buttonUndo.setName("buttonUndo");
        buttonUndo.setAction(containerAction.getEnableButtonDisabled());
        buttonDisabled.setName("buttonDisabled");
        buttonDisabled.setBackground(MaterialColors.COSMO_LIGHT_ORANGE);
        buttonDisabled.setAction(containerAction.getEnableButtonDisabled());
        buttonDisabled.setEnabled(true);
        buttonNormal.setName("buttonNormal");

        textFieldUsername.setName("usernameField");
        textFieldUsername.setText("Hello this is an test with AssertJ");
        textFieldUsername.addActionListener(containerAction.getListenerTextField());
        passwordFiled.setName("passwordField");
        passwordFiled.addActionListener(containerAction.getListenerPasswordField());

        initJMenuBar();

        table.setModel(new TableModelSecondPanel());

        initLayoutContentPanelOne();
        initLayoutContentPanelTwo();

        this.getRootPane().setDefaultButton(buttonDefault);

        tabbedPane.add(panelOne, "Panel One");
        tabbedPane.add(panelTwo, "Panel two");
        tabbedPane.add(new JPanel(), "Panel 3");
        tabbedPane.add(new JPanel(), "Panel 4");
        tabbedPane.add(new JPanel(), "Panel 5");
        tabbedPane.add(new JPanel(), "Panel 6");
        tabbedPane.add(new JPanel(), "Panel 7");
        tabbedPane.add(new JPanel(), "Panel 8");
        tabbedPane.add(new JPanel(), "Panel 9");

        this.setContentPane(tabbedPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initJMenuBar() {

        menuItemJFileChooser.setName("menuItemJFileChooser");
        menuFile.add(menuItemJFileChooser);
        menuFile.setName("nameFile");

        material.setAction(containerAction.getActionChangeTheme("Material lite"));
        metal.setAction(containerAction.getActionChangeTheme("Nimbus"));
        gtk.setAction(containerAction.getActionChangeTheme("GTK"));
        materialDark.setAction(containerAction.getActionChangeTheme("Material Oceanic"));
        jmarsDark.setAction(containerAction.getActionChangeTheme("JMars Dark"));

        themesMenu.add(material);
        themesMenu.add(metal);
        themesMenu.add(materialDark);
        themesMenu.add(jmarsDark);
        themesMenu.add(gtk);

        addSubMenus(arrowMenuOne, 5);
        addSubMenus(arrowMenuTwo, 3);

        menuBar.add(menuFile);
        menuBar.add(themesMenu);
        menuBar.add(arrowMenuOne);
        menuBar.add(arrowMenuTwo);
        menuBar.setName("menuBar");
        this.setJMenuBar(menuBar);

        menuItemJFileChooser.setAction(containerAction.getActionFileChooser());
    }

    public void addSubMenus(JMenu parent, int number) {
        for (int i = 1; i <= number; i++) {
            JMenu menu = new JMenu("Sub Menu " + i);
            parent.add(menu);

            addSubMenus(menu, number - 1);
            addMenuItems(menu, number);
        }
    }

    public void addMenuItems(JMenu parent, int number) {
        for (int i = 1; i <= number; i++) {
            parent.add(new JMenuItem("Item " + i));
        }
    }

    public void initLayoutContentPanelOne() {
        layoutPanelOne = new GroupLayout(panelOne);
        panelOne.setLayout(layoutPanelOne);

        layoutPanelOne.setAutoCreateGaps(true);
        layoutPanelOne.setAutoCreateContainerGaps(true);

        //Init position component with group layaut
        layoutPanelOne.setHorizontalGroup(
                layoutPanelOne.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(textFieldUsername)
                        .addComponent(passwordFiled)
                        .addGroup(layoutPanelOne.createSequentialGroup()
                                .addGap(50)
                                .addComponent(buttonDefault, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50)
                                .addComponent(buttonDisabled, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50)
                                .addComponent(buttonNormal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50)
                                .addComponent(buttonUndo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        layoutPanelOne.setVerticalGroup(
                layoutPanelOne.createSequentialGroup()
                        .addComponent(textFieldUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20)
                        .addComponent(passwordFiled, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20)
                        .addGroup(layoutPanelOne.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(buttonDefault)
                                .addComponent(buttonDisabled)
                                .addComponent(buttonNormal)
                                .addComponent(buttonUndo)
                        )
        );
    }

    public void initLayoutContentPanelTwo() {
        layoutPanelTwo = new GroupLayout(panelTwo);
        panelTwo.setLayout(layoutPanelTwo);

        layoutPanelTwo.setAutoCreateGaps(true);
        layoutPanelTwo.setAutoCreateContainerGaps(true);

        //Init position component with group layaut
        layoutPanelTwo.setHorizontalGroup(
                layoutPanelTwo.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(table)
        );

        layoutPanelTwo.setVerticalGroup(
                layoutPanelTwo.createSequentialGroup()
                        .addComponent(table, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    public synchronized void reloadUI() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    public synchronized void changeThemeWith(BasicLookAndFeel lookAndFeel) {
        try {
            // UIManager.getLookAndFeel().uninitialize();
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void enableTheme(JMenuItem menuItem) {
        menuItem.setEnabled(false);
        if (menuItem != material) {
            material.setEnabled(true);
        } else if (menuItem != gtk) {
            gtk.setEnabled(true);
        } else if (menuItem != metal) {
            metal.setEnabled(true);
        }
    }

    //getter and setter
    public JFileChooser getFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setName("fileChooserAction");
        return fileChooser;
    }

    public JMenuItem getMaterial() {
        return material;
    }

    public JMenuItem getGtk() {
        return gtk;
    }

    public JMenuItem getMetal() {
        return metal;
    }


    public JMenuItem getJmarsDark() {
        return jmarsDark;
    }

    public static DemoGUITest getInstance() {
        return SINGLETON;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SINGLETON.initComponent();
            }
        });
    }


}
