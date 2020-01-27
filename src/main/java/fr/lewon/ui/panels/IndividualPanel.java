package fr.lewon.ui.panels;

import javax.swing.*;

public class IndividualPanel extends JPanel {

    private static final long serialVersionUID = 354536610348394850L;

    private JTextField rankingTxt;
    private JTextField scoreTxt;
    private JPanel mainPane;

    public IndividualPanel(int ranking, double score) {
        this.add(this.mainPane);
        this.setRanking(ranking);
        this.setScore(score);
    }

    private void setScore(double score) {
        this.scoreTxt.setText(String.valueOf(score));
    }

    private void setRanking(int ranking) {
        this.rankingTxt.setText(String.valueOf(ranking));
    }
}
