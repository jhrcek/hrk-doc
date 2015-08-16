package cz.janhrcek.hrk.doc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

public class SimpleDumper {

    private final StringBuilder sb = new StringBuilder();

    /* Entry point of the Doclet */
    public static boolean start(RootDoc root) {
        Collector collector = new Collector();
        for (ClassDoc doc : root.classes()) {
            collector.addDoc(doc);
        }
        System.out.println(collector.formatData());
        return true;
    }

    /* This is essential for the doclet to be able to work with language features added in JDK 5 (Enums and Annotation) */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
