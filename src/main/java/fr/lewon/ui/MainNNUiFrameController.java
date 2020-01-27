package fr.lewon.ui;

import fr.lewon.ui.panels.IndividualListPanel;
import fr.lewon.ui.panels.PopulationEvolutionPanel;
import fr.lewon.utils.PopulationInfos;

import javax.swing.*;

public class MainNNUiFrameController {

    private JLabel status;
    private JPanel mainPane;
    private PopulationEvolutionPanel populationEvolutionPanel;
    private IndividualListPanel individualListPane;
    private JPanel appPanel;

    private JFrame frame;

    public MainNNUiFrameController() {
        this.frame = new JFrame("NN Evolution");
        this.frame.setContentPane(this.mainPane);
        this.status.setText("");
    }

    public void updateInfos(PopulationInfos populationInfos) {
        this.frame.setTitle("NN evolution - Generation " + populationInfos.getGeneration());
        this.populationEvolutionPanel.updateGraph(populationInfos);
        this.individualListPane.refreshIndividuals(populationInfos.getSortedPopulation());
        this.getFrame().repaint();
    }

    public void initAppPanel(JPanel appPanel) {
        if (appPanel != null) {
            this.appPanel.add(appPanel);
        } else {
            this.mainPane.remove(this.appPanel);
        }
        this.frame.pack();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void updateStatusMessage(String message) {
        this.status.setText(message);
    }

}
