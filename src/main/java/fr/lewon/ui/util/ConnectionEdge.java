package fr.lewon.ui.util;

import org.jgrapht.graph.DefaultEdge;

public class ConnectionEdge extends DefaultEdge {

    private static final long serialVersionUID = 5167236564736230127L;

    private String label;

    public ConnectionEdge(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
