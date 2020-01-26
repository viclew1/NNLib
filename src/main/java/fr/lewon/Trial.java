package fr.lewon;

import fr.lewon.exceptions.NNException;
import fr.lewon.utils.PopulationInfos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Trial {

    private JFrame jfGraph;
    private double[][] bestScoreDatas;
    private double[][] averageDatas;
    private JFreeChart chart;
    private List<PopulationInfos> graphInfos;
    private static final int MAX_GRAPHINFOS_SIZE = 1000;

    public Trial() {
        this.graphInfos = new ArrayList<>(Trial.MAX_GRAPHINFOS_SIZE);
        this.jfGraph = new JFrame("Graph");
        this.jfGraph.setSize(640, 400);
        DefaultXYDataset dataSet = new DefaultXYDataset();
        this.bestScoreDatas = new double[2][Trial.MAX_GRAPHINFOS_SIZE];
        this.averageDatas = new double[2][Trial.MAX_GRAPHINFOS_SIZE];
        dataSet.addSeries("Moyenne", this.averageDatas);
        dataSet.addSeries("Meilleur individu", this.bestScoreDatas);
        this.chart = ChartFactory.createXYLineChart("popInfos", "Generation", "Score", dataSet);
        ChartPanel graphPane = new ChartPanel(this.chart) {

            private static final long serialVersionUID = 8497834706731165571L;

            @Override
            public void paint(Graphics g) {
                super.getRootPane().updateUI();
                super.paint(g);
                Trial.this.chart.getXYPlot().setDataset(Trial.this.chart.getXYPlot().getDataset());
            }

        };
        this.jfGraph.add(graphPane);
        this.jfGraph.setVisible(true);
    }

    /**
     * Step where the individuals should have their fitness set
     *
     * @param population
     * @throws NNException
     */
    public abstract void execute(List<Individual> population) throws NNException;

    public void processBetweenGenerationsActions(PopulationInfos infos) {
        if (this.graphInfos.size() == Trial.MAX_GRAPHINFOS_SIZE) {
            this.graphInfos.remove(0);
        }
        this.graphInfos.add(infos);

        double[] lastPointBestScore = new double[2];
        double[] lastPointAverage = new double[2];
        for (int i = 0; i < this.graphInfos.size(); i++) {
            PopulationInfos inf = this.graphInfos.get(i);
            this.bestScoreDatas[0][1 + i] = inf.getGeneration();
            this.averageDatas[0][1 + i] = inf.getGeneration();

            this.bestScoreDatas[1][1 + i] = inf.getMaxScore();
            this.averageDatas[1][1 + i] = inf.getAverage();

            lastPointBestScore[0] = this.bestScoreDatas[0][1 + i];
            lastPointBestScore[1] = this.bestScoreDatas[1][1 + i];
            lastPointAverage[0] = this.averageDatas[0][1 + i];
            lastPointAverage[1] = this.averageDatas[1][1 + i];
        }
        for (int i = this.graphInfos.size(); i < Trial.MAX_GRAPHINFOS_SIZE; i++) {
            this.bestScoreDatas[0][i] = lastPointBestScore[0];
            this.bestScoreDatas[1][i] = lastPointBestScore[1];
            this.averageDatas[0][i] = lastPointAverage[0];
            this.averageDatas[1][i] = lastPointAverage[1];
        }
        this.jfGraph.repaint();
    }

}
