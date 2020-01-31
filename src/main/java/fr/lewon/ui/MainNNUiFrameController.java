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
    private PopulationEvolutionPanel populationEvolutionPanel;
    private IndividualListPanel individualListPane;
    private JPanel appPanel;
    private JPanel containedAppPanel;
    private IndividualPanel individualPane;
    private JSplitPane mainSplitPane;
    private JSplitPane subSplitPane;

    private JFrame frame;

    public void updateInfos(PopulationInfos populationInfos) {
        this.frame.setTitle("NN Evolution - Generation " + populationInfos.getGeneration());
        this.populationEvolutionPanel.updateGraph(populationInfos);
        this.individualListPane.refreshIndividuals(populationInfos.getSortedPopulation());
        this.individualPane.updateIndividual(populationInfos.getSortedPopulation().get(0));
        this.updateStatusMessage("Updated - generation " + populationInfos.getGeneration());
        this.getFrame().repaint();
    }

    public void initFrame(JPanel appPanel) {
        this.frame = new JFrame("NN Evolution");
        this.containedAppPanel = appPanel;
        if (appPanel != null) {
            this.appPanel.add(this.containedAppPanel);
            this.containedAppPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MainNNUiFrameController.this.containedAppPanel.grabFocus();
                }
            });
            this.getFrame().setContentPane(this.mainSplitPane);
        } else {
            this.getFrame().setContentPane(this.subSplitPane);
        }

        this.getFrame().setMinimumSize(this.getFrame().getContentPane().getMinimumSize());
        this.getFrame().setPreferredSize(this.getFrame().getContentPane().getPreferredSize());
        this.getFrame().pack();
        this.getFrame().setVisible(true);
        this.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.status.setText("Initialization OK");
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void updateStatusMessage(String message) {
        this.status.setText(message);
    }

}
