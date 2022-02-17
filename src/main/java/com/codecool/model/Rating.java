package com.codecool.model;

public enum Rating {
    G, PG, PG_13, R, NC_17;

    @Override
    public String toString() {
        // Since enum names can't contain the '-' character, we need to change it to '_'
        return super.toString().replace('_', '-');
    }

    public static Rating of(String name) {
        // We can't override valueOf, so we have to create a different method.
        return valueOf(name.replace('-', '_'));
    }
}
