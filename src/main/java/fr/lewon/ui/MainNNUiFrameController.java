package fr.lewon.ui;

import fr.lewon.ui.panels.IndividualListPanel;
import fr.lewon.ui.panels.IndividualPanel;
import fr.lewon.ui.panels.PopulationEvolutionPanel;
import fr.lewon.utils.PopulationInfos;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainNNUiFrameController {

    private JLabel status;
    private JPanel mainPane;
    private PopulationEvolutionPanel populationEvolutionPanel;
    private IndividualListPanel individualListPane;
    private JPanel appPanel;
    private JPanel containedAppPanel;
    private IndividualPanel individualPane;

    private JFrame frame;

    public MainNNUiFrameController() {
        this.frame = new JFrame("NN Evolution");
        this.frame.setContentPane(this.mainPane);
        this.status.setText("Initialization OK");
    }

    public void updateInfos(PopulationInfos populationInfos) {
        this.frame.setTitle("NN Evolution - Generation " + populationInfos.getGeneration());
        this.populationEvolutionPanel.updateGraph(populationInfos);
        this.individualListPane.refreshIndividuals(populationInfos.getSortedPopulation());
        this.individualPane.updateIndividual(populationInfos.getSortedPopulation().get(0));
        this.updateStatusMessage("Updated - generation " + populationInfos.getGeneration());
        this.getFrame().repaint();
    }

    public void initAppPanel(JPanel appPanel) {
        this.containedAppPanel = appPanel;
        if (appPanel != null) {
            this.appPanel.add(this.containedAppPanel);
            this.containedAppPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MainNNUiFrameController.this.containedAppPanel.grabFocus();
                }
            });
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
