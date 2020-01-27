package fr.lewon.ui.panels;

import fr.lewon.utils.ImageUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ExportToolsPanel extends JPanel {

    private static final long serialVersionUID = -7252501371547814102L;

    private JButton exportBtn;
    private JTextField directoryTxt;
    private JButton selectDirectoryBtn;
    private JPanel mainPane;

    private JFileChooser chooser;

    public ExportToolsPanel() {
        try {
            this.selectDirectoryBtn.setIcon(new ImageIcon(ImageUtil.INSTANCE.resizeImageConserveHeight("/icons/book.png", (int) this.selectDirectoryBtn.getPreferredSize().getHeight())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(this.mainPane);
        this.chooser = new JFileChooser();
        File currentDir = new File(".");
        this.chooser.setCurrentDirectory(currentDir);
        this.directoryTxt.setText(currentDir.getAbsolutePath());
        this.chooser.setDialogTitle("Select export directory");
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.selectDirectoryBtn.addActionListener(e -> this.selectDirectory(e));
        this.exportBtn.addActionListener(e -> this.exportSelectedIndividuals(e));
    }

    private void exportSelectedIndividuals(ActionEvent e) {
    }

    private void selectDirectory(ActionEvent e) {
        if (this.chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.directoryTxt.setText(this.chooser.getSelectedFile().getAbsolutePath());
            this.chooser.setCurrentDirectory(this.chooser.getSelectedFile());
        }
    }

}
