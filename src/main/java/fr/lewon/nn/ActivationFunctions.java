package fr.lewon.nn;

import java.util.function.Function;

public enum ActivationFunctions {

    SIGMOID((v) -> 1 / (1 + Math.exp(-v)));

    private Function<Double, Double> function;

    ActivationFunctions(Function<Double, Double> function) {
        this.function = function;
    }

    public double process(double value) {
        return this.function.apply(value);
    }

}