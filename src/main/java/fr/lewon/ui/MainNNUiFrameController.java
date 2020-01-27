package fr.lewon.ui;

import fr.lewon.ui.panels.PopulationEvolutionPanel;
import fr.lewon.utils.PopulationInfos;

import javax.swing.*;

public class MainNNUiFrameController {

    private JLabel status;
    private JPanel mainPane;
    private PopulationEvolutionPanel populationEvolutionPanel;

    private JFrame frame;

    public MainNNUiFrameController() {
        this.frame = new JFrame("NN Evolution");
        this.frame.setContentPane(this.mainPane);
        this.status.setText("");
        this.frame.pack();
    }

    public void updateInfos(PopulationInfos populationInfos) {
        this.populationEvolutionPanel.updateGraph(populationInfos);
        this.getFrame().repaint();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void updateStatusMessage(String message) {
        this.status.setText(message);
    }

    public static void main(String[] args) {
        new MainNNUiFrameController().getFrame().setVisible(true);
    }
}
