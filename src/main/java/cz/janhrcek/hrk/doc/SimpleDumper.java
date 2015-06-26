package cz.janhrcek.hrk.doc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import java.util.Objects;

public class SimpleDumper {

    private final StringBuilder sb = new StringBuilder();

    /* Entry point of the Doclet */
    public static boolean start(RootDoc root) {
        new SimpleDumper().process(root);
        return true;
    }

    /* Arbitratily named helper */
    public void process(RootDoc root) {
        for (ClassDoc cls : root.classes()) {
            appendDocInfo(cls);
        }
        System.out.println(sb.toString());
    }

    private void appendDocInfo(ClassDoc doc) {
        sb.append(formatConstruct(doc)).append("\n");

        ClassDoc superCls = doc.superclass();
        if (superCls != null) {
            sb.append("    extends ").append(formatConstruct(superCls)).append("\n");
        }

        for (ClassDoc superIfc : doc.interfaces()) {
            // Interface EXTENDS Interface, Class IMPLEMENTS Interface
            String keyword = doc.isInterface() ? "extends" : "implements";
            sb.append("    ").append(keyword).append(" ").append(formatConstruct(superIfc)).append("\n");
        }
    }

    private String formatConstruct(ClassDoc doc) {
        return getConstruct(doc) + " " + doc.containingPackage() + " " + doc.name();
    }

    private String getConstruct(ClassDoc cd) {
        Objects.requireNonNull(cd, "class doc");
        if (cd.isEnum()) { // Check before isClass, because that returns true for enums
            return "enum";
        } else if (cd.isClass()) {
            return "class";
        } else if (cd.isAnnotationType()) { // Check before isInterface, because that returns true for annotations
            return "annotation";
        } else if (cd.isInterface()) {
            return "interface";
        } else {
            throw new IllegalStateException("Unexpected contruct!");
        }
    }

    /* This is essential for the doclet to be able to work with language features added in JDK 5 (Enums and Annotation) */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
