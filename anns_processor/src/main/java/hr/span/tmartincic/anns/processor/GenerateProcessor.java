package hr.span.tmartincic.anns.processor;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("hr.span.tmartincic.anns.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class GenerateProcessor extends AbstractProcessor
{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        //print a test message
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "neka poruka iz procesora");
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "neka poruka iz procesora");
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "neka poruka iz procesora");
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "neka poruka iz procesora");

//        System.out.println("neka poruka iz procesora");
//        System.out.println("apsfjmapogfj");

        StringBuilder builder = new StringBuilder();

        builder.append("package hr.span.tmartincic.anns.generated;\n");
        builder.append("\n");
        builder.append("public class GeneratedClass {\n");
        builder.append("\n");
        builder.append("\tpublic String getMessage() {\n");
        builder.append("\t\treturn \"Something\"\"");

        builder.append("\";\n");
        builder.append("\t}\n");
        builder.append("}\n");

        Filer filer = processingEnv.getFiler();

        try
        {
            JavaFileObject jfo = filer.createSourceFile("hr.span.tmartincic.anns.generated.GeneratedClass");

            Writer writer = jfo.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //find all elements that are annotated with @Generate annotation

//        Set<? extends Element> generateElements = roundEnv.getElementsAnnotatedWith(Generate.class);
//
//        for(Element generateElement : generateElements)
//        {
//            //@Generate annotation targets Type
//            //check if the current element is of Interface Type and ignore other Types
//            ElementKind kind = generateElement.getKind();
//
//        }

        return true;
    }
}

