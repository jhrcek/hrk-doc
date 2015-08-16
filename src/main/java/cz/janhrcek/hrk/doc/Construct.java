package cz.janhrcek.hrk.doc;

import java.util.Objects;

public class Construct {

    private final ConstructType type;
    private final String pkg;
    private final String name;

    public Construct(ConstructType type, String pkg, String name) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(pkg);
        Objects.requireNonNull(name);
        this.type = type;
        this.pkg = pkg;
        this.name = name;
    }

    public ConstructType getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", type, pkg, name);
    }

    @Override
    public int hashCode() {
        int hash = 53 * 3 + pkg.hashCode();
        return 53 * hash + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Construct other = (Construct) obj;
        if (!pkg.equals(other.pkg)) {
            return false;
        }
        if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
