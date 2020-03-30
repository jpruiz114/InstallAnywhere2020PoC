package com.macrovision.test;

import com.macrovision.test.util.Settings;

import javax.swing.*;
import java.io.*;

/**
 * An example which shows off a functional simple text editor.  Includes a variety of events.
 */
public class SimpleTextEditor extends JFrame {
    private javax.swing.JPanel jContentPane = null;

    private javax.swing.JScrollPane jScrollPane = null;

    private javax.swing.JTextArea jTextArea = null;

    private javax.swing.JFileChooser jFileChooser = null;

    private boolean hasChanged = false;

    private static final String title = "Simple Text Editor 1.1";

    private JMenuBar jJMenuBar = null;

    private JMenu jMenu = null;

    private JMenuItem jMenuItem = null;

    private JMenuItem jMenuItem1 = null;

    private JMenuItem jMenuItem2 = null;

    /**
     * This method initializes
     */
    public SimpleTextEditor() {
        super();
        initialize();
    }

    /**
     * This method initializes jJMenuBar
     *
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getJMenu());
        }
        return jJMenuBar;
    }

    /**
     * This method initializes jMenu
     *
     * @return javax.swing.JMenu
     */
    private JMenu getJMenu() {
        if (jMenu == null) {
            jMenu = new JMenu();
            jMenu.setText("File");
            jMenu.add(getJMenuItem());
            jMenu.add(getJMenuItem1());
            jMenu.add(getJMenuItem2());
        }
        return jMenu;
    }

    /**
     * This method initializes jMenuItem
     *
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItem() {
        if (jMenuItem == null) {
            jMenuItem = new JMenuItem();
            jMenuItem.setText("Open...");
            jMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    loadFile();
                }
            });
        }
        return jMenuItem;
    }

    /**
     * This method initializes jMenuItem1
     *
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItem1() {
        if (jMenuItem1 == null) {
            jMenuItem1 = new JMenuItem();
            jMenuItem1.setText("Save...");

            jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    saveFile();
                }
            });
        }
        return jMenuItem1;
    }

    /**
     * This method initializes jMenuItem2
     *
     * @return javax.swing.JMenuItem
     */
    private JMenuItem getJMenuItem2() {
        if (jMenuItem2 == null) {
            jMenuItem2 = new JMenuItem();
            jMenuItem2.setText("Exit...");
            jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    doExit();
                }
            });
        }
        return jMenuItem2;
    }

    public static void main(String[] args) {
        SimpleTextEditor ste = new SimpleTextEditor();
        ste.show();
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(new java.awt.BorderLayout());
            jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
            jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        return jContentPane;
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        Settings s = new Settings();

        this.setJMenuBar(getJJMenuBar());

        String initialHeight = s.get("initial.height");
        System.out.println("initialHeight = " + initialHeight);

        String initialWidth = s.get("initial.width");
        System.out.println("initialWidth = " + initialWidth);

        this.setContentPane(getJContentPane());
        this.setSize(new Integer(initialWidth).intValue(),
                new Integer(initialHeight).intValue());
        this.setTitle(title);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                doExit();
            }
        });
    }

    /**
     * This method initializes jScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private javax.swing.JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new javax.swing.JScrollPane();
            jScrollPane.setViewportView(getJTextArea());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jTextArea
     *
     * @return javax.swing.JTextArea
     */
    private javax.swing.JTextArea getJTextArea() {
        if (jTextArea == null) {
            jTextArea = new javax.swing.JTextArea();
            jTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent e) {
                    if (!hasChanged) {
                        setTitle(title + " *");
                        hasChanged = true;
                    }
                }
            });
        }
        return jTextArea;
    }

    /**
     * This method initializes jFileChooser
     *
     * @return javax.swing.JFileChooser
     */
    private javax.swing.JFileChooser getJFileChooser() {
        if (jFileChooser == null) {
            jFileChooser = new javax.swing.JFileChooser();
            jFileChooser.setMultiSelectionEnabled(false);
        }
        return jFileChooser;
    }

    private void loadFile() {
        int state = getJFileChooser().showOpenDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            File f = getJFileChooser().getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                getJTextArea().read(br, null);
                br.close();
                setTitle(title);
                hasChanged = false;
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void saveFile() {
        int state = getJFileChooser().showSaveDialog(this);
        if (state == JFileChooser.APPROVE_OPTION) {
            File f = getJFileChooser().getSelectedFile();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                getJTextArea().write(bw);
                bw.close();
                setTitle(title);
                hasChanged = false;
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void doExit() {
        if (hasChanged) {
            int state = JOptionPane.showConfirmDialog(this,
                    "File has been changed. Save before exit?");
            if (state == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (state == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        System.exit(0);
    }
}
