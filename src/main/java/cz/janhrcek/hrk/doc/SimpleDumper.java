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
            appendClassInfo(cls);
        }
        System.out.println(sb.toString());
    }

    private void appendClassInfo(ClassDoc doc) {
        sb.append(getConstruct(doc)).append(" ").append(formatName(doc)).append("\n");

        ClassDoc superCls = doc.superclass();
        if (superCls != null) {
            sb.append("    extends ").append(formatName(superCls)).append("\n");
        }

        for (ClassDoc superIfc : doc.interfaces()) {
            sb.append("    implements ").append(formatName(superIfc)).append("\n");
        }
    }

    private String formatName(ClassDoc cd) {
        return cd.containingPackage() + " " + cd.name();
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
