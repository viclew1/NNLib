package fr.lewon.ui.panels;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import fr.lewon.Individual;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class IndividualPanel extends JPanel implements MouseMotionListener, MouseListener {

    private static final long serialVersionUID = 354536610348394850L;

    private JPanel mainPane;
    private mxGraphComponent graphComponent;
    private Point lastRegisteredPoint;

    public IndividualPanel() {
        this.add(this.mainPane);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
    }

    public void updateIndividual(Individual indiv) {
        this.mainPane.removeAll();
        Graph<Long, DefaultEdge> graph = indiv.buildGraph();
        JGraphXAdapter jgxAdapter = new JGraphXAdapter<>(graph);
        this.graphComponent = new mxGraphComponent(jgxAdapter);
        mxGraphLayout layout = new mxCompactTreeLayout(jgxAdapter);

        this.graphComponent.setConnectable(false);
        this.graphComponent.getGraph().setCellsEditable(false);
        this.graphComponent.getGraph().setAllowDanglingEdges(false);
        this.graphComponent.getGraph().setVertexLabelsMovable(false);
        this.graphComponent.getGraph().setCellsDisconnectable(false);
        this.graphComponent.getGraph().setResetEdgesOnMove(true);
        this.graphComponent.getGraph().refresh();

        layout.execute(jgxAdapter.getDefaultParent());
        this.mainPane.add(this.graphComponent);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        System.out.println("Lol" + this.lastRegisteredPoint + "/ " + point);
        if (this.lastRegisteredPoint != null && this.graphComponent != null) {
            double dx = point.getX() - this.lastRegisteredPoint.getX();
            double dy = point.getY() - this.lastRegisteredPoint.getY();
            this.graphComponent.setLocation((int) (this.graphComponent.getX() + dx), (int) (this.graphComponent.getY() + dy));
        }
        this.lastRegisteredPoint = point;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("huhu");
        this.lastRegisteredPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
