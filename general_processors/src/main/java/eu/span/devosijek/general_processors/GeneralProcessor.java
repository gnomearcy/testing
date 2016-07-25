package eu.span.devosijek.general_processors;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import eu.span.devosijek.general_processors_api.Subscriber;
import eu.span.devosijek.general_processors_api.Utility;
public class GeneralProcessor extends AbstractProcessor
{
    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> supportedAnnnotations = new LinkedHashSet<>();
        supportedAnnnotations.add(Subscriber.class.getCanonicalName());
        supportedAnnnotations.add(Utility.class.getCanonicalName());
        return supportedAnnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        for(Element t : roundEnv.getElementsAnnotatedWith(Subscriber.class))
        {
            TypeElement elem = (TypeElement) t;

            try
            {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(elem.getQualifiedName() + "Generated");

                Writer w = jfo.openWriter();

                try
                {
                    PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(elem);
                    PrintWriter pw = new PrintWriter(w);

                    pw.println("package " + packageElement.toString() + ";");
                    pw.println("public class " + elem.getSimpleName() + "Generated {");
                    pw.println("public static void printMe() {");
                    pw.println("System.out.println(this.getClass().getSimpleName());");
                    pw.println("}}");

                    pw.flush();
                }
                finally
                {
                    w.close();
                }
            }
            catch(IOException e)
            {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, e.getMessage());
            }

        }

        for(Element t : roundEnv.getElementsAnnotatedWith(Utility.class))
        {
            TypeElement elem = (TypeElement) t;

            try
            {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(elem.getQualifiedName() + "Generated");

                Writer w = jfo.openWriter();

                try
                {
                    PrintWriter pw = new PrintWriter(w);
                    PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(elem);

                    pw.println("package " + packageElement.toString() + ";");
                    pw.println("public class " + elem.getSimpleName() + "Generated {");
                    pw.println("public static void printMe() {");
                    pw.println("System.out.println(this.getClass().getSimpleName());");
                    pw.println("}}");

                    pw.flush();
                }
                finally
                {
                    w.close();
                }
            }
            catch(IOException e)
            {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, e.getMessage());
            }
        }

        return true;
    }
}
