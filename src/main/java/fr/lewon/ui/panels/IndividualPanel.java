package fr.lewon.ui.panels;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import fr.lewon.Individual;
import fr.lewon.ui.util.ConnectionEdge;
import fr.lewon.ui.util.VertexInfo;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

public class IndividualPanel extends JPanel implements MouseWheelListener {

    private static final long serialVersionUID = 354536610348394850L;

    private JPanel mainPane;
    private mxGraphComponent graphComponent;
    private JGraphXAdapter jgxAdapter;
    private mxHierarchicalLayout layout;

    public IndividualPanel() {
        this.add(this.mainPane);
    }

    public void updateIndividual(Individual indiv) {
        Graph<VertexInfo, ConnectionEdge> graph = indiv.buildGraph();

        this.jgxAdapter = new JGraphXAdapter<>(graph);
        this.graphComponent = new mxGraphComponent(this.jgxAdapter);
        this.layout = new mxHierarchicalLayout(this.jgxAdapter);

        this.layout.setUseBoundingBox(false);
        this.layout.setDisableEdgeStyle(true);

        for (MouseWheelListener mwl : this.graphComponent.getMouseWheelListeners()) {
            this.graphComponent.removeMouseWheelListener(mwl);
        }
        this.graphComponent.addMouseWheelListener(this);
        this.graphComponent.setFocusable(true);

        HashMap<VertexInfo, mxICell> vertexToCellMap = this.jgxAdapter.getVertexToCellMap();
        for (VertexInfo vi : graph.vertexSet()) {
            this.jgxAdapter.setCellStyle("fillColor=" + vi.getHexColor(), new Object[]{vertexToCellMap.get(vi)});
        }
        this.layout.setOrientation(SwingConstants.WEST);
        this.layout.setInterRankCellSpacing(200);
        this.layout.setParallelEdgeSpacing(0);
        this.jgxAdapter.setCellsEditable(false);
        this.jgxAdapter.setAllowDanglingEdges(false);
        this.jgxAdapter.setVertexLabelsMovable(false);
        this.jgxAdapter.setCellsDisconnectable(false);
        this.jgxAdapter.setResetEdgesOnMove(true);
        this.jgxAdapter.setAutoSizeCells(true);

        SwingUtilities.invokeLater(() -> {
            this.mainPane.removeAll();
            this.layout.execute(this.jgxAdapter.getDefaultParent());
            this.jgxAdapter.refresh();
            this.mainPane.add(this.graphComponent);
            this.mainPane.getRootPane().updateUI();
        });
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //TODO impl zoom
    }
}
