package cz.janhrcek.hrk.doc;

import com.sun.javadoc.ClassDoc;
import java.util.HashMap;
import java.util.Map;

public class Collector {

    private final Map<Construct, Integer> ids = new HashMap<>();
    private final Map<Integer, Integer> inheritancePairs = new HashMap<>();
    private int idGen = 0;

    public void addDoc(ClassDoc doc) {
        //Store construct info
        int docId = store(getConstruct(doc));

        //Store superclass construct info + inheritance pair if any
        ClassDoc superCls = doc.superclass();
        if (superCls != null) {
            int supId = store(getConstruct(superCls));
            inheritancePairs.put(docId, supId);
        }

        //Store super interfaces construct infos + inherit. pairs if any
        for (ClassDoc superIfc : doc.interfaces()) {
            int supIfc = store(getConstruct(superIfc));
            inheritancePairs.put(docId, supIfc);
        }
    }

    /**
     * @param constr the construct to store
     * @return id assigned to the construct
     */
    private int store(Construct constr) {
        if (ids.containsKey(constr)) {
            return ids.get(constr);
        } else {
            ids.put(constr, ++idGen);
        }
        System.out.println("    stored " + constr + " with id " + idGen);
        return idGen;
    }

    private Construct getConstruct(ClassDoc doc) {
        ConstructType type = getConstructType(doc);
        String pkg = doc.containingPackage().name();
        String name = doc.name();
        return new Construct(type, pkg, name);
    }

    private ConstructType getConstructType(ClassDoc doc) {
        if (doc.isEnum()) { // Check before isClass, because that returns true for enums
            return ConstructType.ENUM;
        } else if (doc.isClass()) {
            return ConstructType.CLASS;
        } else if (doc.isAnnotationType()) { // Check before isInterface, because that returns true for annotations
            return ConstructType.ANNOTATION;
        } else if (doc.isInterface()) {
            return ConstructType.INTERFACE;
        } else {
            throw new IllegalStateException("Unexpected Construct type " + doc.toString());
        }
    }

    public String formatData() {
        StringBuilder sb = new StringBuilder();

        //Append constructs
        for (Map.Entry<Construct, Integer> entry : ids.entrySet()) {
            sb.append(entry.getKey())
                    .append(' ')
                    .append(entry.getValue())
                    .append('\n');
        }

        //Append inheritance pairs
        sb.append('[');
        for (Map.Entry<Integer, Integer> pair : inheritancePairs.entrySet()) {
            sb.append('(').append(pair.getKey()).append(',').append(pair.getValue()).append("),");
        }
        sb.deleteCharAt(sb.length() - 1); // remove trailing ','
        sb.append(']');

        return sb.toString();
    }
}
