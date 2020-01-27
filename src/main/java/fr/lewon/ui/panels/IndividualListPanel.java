package fr.lewon.ui.panels;

import fr.lewon.Individual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IndividualListPanel extends JPanel {

    private static final long serialVersionUID = 6961451766643934840L;

    private JScrollPane mainPane;
    private JTable individualsTable;

    public IndividualListPanel() {
        this.add(this.mainPane);
    }

    public void refreshIndividuals(List<Individual> individuals) {
        String[] columnNames = {"Ranking", "Score"};
        AtomicInteger cpt = new AtomicInteger();
        String[][] values = individuals.stream()
                .map(i -> new String[]{String.valueOf(cpt.incrementAndGet()), String.format("%.2f", i.getFitness())})
                .toArray(String[][]::new);
        TableModel model = new DefaultTableModel(values, columnNames);
        this.individualsTable.setModel(model);
    }
}
