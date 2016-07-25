package hr.span.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import hr.span.processor.api.Generate;


@SupportedAnnotationTypes("hr.span.tmartincic.api.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TomoProcessor extends AbstractProcessor
{
    private static final String tag = "AnnotationProcessing";
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "");
        StringBuilder builder = new StringBuilder();

        builder.append("package hr.span.tmartincic.generated;\n");
        builder.append("\n");
        builder.append("public class GeneratedFromTomo {\n");
        builder.append("\n");
        builder.append("\tpublic String getMessage() {\n");
        builder.append("\t\treturn \"");

        for(Element element : roundEnv.getElementsAnnotatedWith(Generate.class))
        {
            String objectType = element.getSimpleName().toString();

            DeclaredType dt = (DeclaredType) element;
            List<? extends TypeMirror> someset = dt.getTypeArguments();

            for(TypeMirror instance : someset)
            {
                System.out.println(tag + " : " + instance.toString());
            }

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, objectType + "says hello");

            builder.append(objectType + " says hello! \\n");
        }

        builder.append("\";\n");
        builder.append("\t}\n");
        builder.append("}\n");

        Filer filer = processingEnv.getFiler();

        try {
            JavaFileObject sourceFile = filer.createSourceFile("hr.span.tmartincic.generated.GeneratedFromTomo");

            Writer writer = sourceFile.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            //nothing
        }

        return true;
    }
}
