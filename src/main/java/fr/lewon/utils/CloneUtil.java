package fr.lewon.utils;

import com.rits.cloning.Cloner;

public enum CloneUtil {

    INSTANCE;

    private Cloner cloner;

    CloneUtil() {
        this.cloner = new Cloner();
    }

    public <T> T deepCopy(T object) {
        return this.cloner.deepClone(object);
    }

}
