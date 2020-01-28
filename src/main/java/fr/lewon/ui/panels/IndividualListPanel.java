package fr.lewon.ui.panels;

import fr.lewon.Individual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IndividualListPanel extends JPanel {

    private static final long serialVersionUID = 6961451766643934840L;

    private JScrollPane mainPane;
    private JTable individualsTable;
    private DefaultTableModel model;

    public IndividualListPanel() {
        this.add(this.mainPane);
        String[] columnNames = {"Ranking", "Score"};
        this.model = new DefaultTableModel(new String[][]{}, columnNames);
        this.individualsTable.setModel(this.model);
    }

    public void refreshIndividuals(List<Individual> individuals) {
        SwingUtilities.invokeLater(() -> {
            AtomicInteger cpt = new AtomicInteger();
            this.model.setRowCount(0);
            individuals.stream()
                    .map(i -> new String[]{String.valueOf(cpt.incrementAndGet()), String.format("%.2f", i.getFitness())})
                    .forEach(a -> this.model.addRow(a));
        });
    }
}
