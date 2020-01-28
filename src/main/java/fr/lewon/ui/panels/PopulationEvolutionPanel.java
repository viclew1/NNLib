package fr.lewon.ui.panels;

import fr.lewon.utils.PopulationInfos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.ArrayList;
import java.util.List;

public class PopulationEvolutionPanel extends ChartPanel {

    private static final long serialVersionUID = -9200853230658936382L;

    private double[][] bestScoreDatas;
    private double[][] averageDatas;
    private double[][] tendencyDatas;
    private List<PopulationInfos> graphInfos;
    private int maxSize;

    public PopulationEvolutionPanel() {
        this(1000);
    }

    public PopulationEvolutionPanel(int maxSize) {
        super(ChartFactory.createXYLineChart("Evolution stats", "Generation", "Score", new DefaultXYDataset()));
        this.maxSize = maxSize;
        this.graphInfos = new ArrayList<>(maxSize);
        DefaultXYDataset dataset = (DefaultXYDataset) this.getChart().getXYPlot().getDataset();
        this.bestScoreDatas = new double[2][maxSize];
        this.averageDatas = new double[2][maxSize];
        this.tendencyDatas = new double[2][maxSize];
        dataset.addSeries("Average", this.averageDatas);
        dataset.addSeries("Best individual", this.bestScoreDatas);
        dataset.addSeries("Tendency", this.tendencyDatas);
    }

    public void updateGraph(PopulationInfos infos) {
        if (this.graphInfos.size() == this.maxSize) {
            this.graphInfos.remove(0);
        }
        this.graphInfos.add(infos);

        for (int i = 0; i < this.graphInfos.size(); i++) {
            PopulationInfos inf = this.graphInfos.get(i);
            this.bestScoreDatas[0][1 + i] = inf.getGeneration();
            this.averageDatas[0][1 + i] = inf.getGeneration();
            this.tendencyDatas[0][1 + i] = inf.getGeneration();

            this.bestScoreDatas[1][1 + i] = inf.getMaxScore();
            this.averageDatas[1][1 + i] = inf.getAverage();

            List<Double> savedScores = new ArrayList<>();
            for (int j = i - 10; j < i + 10; j++) {
                if (j >= 0 && j < this.graphInfos.size()) {
                    savedScores.add(this.bestScoreDatas[1][1 + j]);
                }
            }
            this.tendencyDatas[1][1 + i] = savedScores.stream().mapToDouble(d -> d).average().orElse(0);

        }
        for (int i = this.graphInfos.size(); i < this.maxSize; i++) {
            this.bestScoreDatas[0][i] = this.bestScoreDatas[0][this.graphInfos.size() - 1];
            this.bestScoreDatas[1][i] = this.bestScoreDatas[1][this.graphInfos.size() - 1];
            this.averageDatas[0][i] = this.averageDatas[0][this.graphInfos.size() - 1];
            this.averageDatas[1][i] = this.averageDatas[1][this.graphInfos.size() - 1];
            this.tendencyDatas[0][i] = this.tendencyDatas[0][this.graphInfos.size() - 1];
            this.tendencyDatas[1][i] = this.tendencyDatas[1][this.graphInfos.size() - 1];
        }
        this.getChart().getXYPlot().setDataset(this.getChart().getXYPlot().getDataset());
    }
}
