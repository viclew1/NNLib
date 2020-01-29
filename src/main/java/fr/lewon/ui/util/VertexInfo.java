package fr.lewon.ui.util;

import java.awt.*;
import java.io.Serializable;

public class VertexInfo implements Serializable {

    private static final long serialVersionUID = -6409236296236374677L;

    private final Long id;
    private final String label;
    private final Color color;
    private final String hexColor;

    public VertexInfo(Long id, String label, Color color) {
        this.id = id;
        this.label = label;
        this.color = color;
        this.hexColor = color == null ? "#000000" : String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public String toString() {
        return this.label;
    }

    public Long getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public Color getColor() {
        return this.color;
    }

    public String getHexColor() {
        return this.hexColor;
    }
}