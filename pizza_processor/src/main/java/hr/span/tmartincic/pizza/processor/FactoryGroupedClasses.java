package hr.span.tmartincic.pizza.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

/** Class which groups all FactoryAnnotatedClasses. */
public class FactoryGroupedClasses
{
    private static final String SUFFIX = "Factory";
    private static final String INNER_SUFFIX = "Constant";

    private String qualifiedClassName;

    private Map<String, FactoryAnnotatedClass> itemsMap = new LinkedHashMap<String, FactoryAnnotatedClass>();

    public FactoryGroupedClasses(String qualifiedClassName)
    {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(FactoryAnnotatedClass toInsert) throws IdAlreadyUsedException
    {
        FactoryAnnotatedClass existing = itemsMap.get(toInsert.getId());

        if(existing != null)
        {
            throw new IdAlreadyUsedException(existing);
        }

        itemsMap.put(toInsert.getId(), toInsert);
    }

    public void generateCode(Elements elementUtils, Filer filer) throws IOException
    {
        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName); //hr.span.tmartincic.pizza.demo.Meal
        PackageElement packageElement = elementUtils.getPackageOf(superClassName);
        String factoryClassName = superClassName.getSimpleName() + SUFFIX; //Meal + Factory (SUFFIX -> Factory)

        /**Not possible - throws NPE, since we're trying to create Type from an element which we're creating at the same time
         * therefor the element does not exist. Solution -> add statically or generate in previous round in this processor. */
//        String innerEnumClassName = superClassName.getSimpleName() + INNER_SUFFIX; //Meal + Constant
//        TypeElement innerEnumName = elementUtils.getTypeElement(innerEnumClassName);

        /** Retrieve info for MealConstants which Meal is enclosing type off */
        List<? extends Element> mealEnclosed = superClassName.getEnclosedElements();

        List<Element> enumFields = new ArrayList<>();
        Element innerEnumClass = null;

        for(Element enclosedElement : mealEnclosed)
        {
            //check if it's type enum class
            if(enclosedElement.getKind() == ElementKind.ENUM)
            {
                innerEnumClass = enclosedElement;

                for(Element enclosedEnumField : enclosedElement.getEnclosedElements())
                {
                    if(enclosedEnumField.getKind() == ElementKind.ENUM_CONSTANT)
                    {
                        enumFields.add(enclosedEnumField);
                    }
                }

                break; //only scan the first one
            }
        }

        JavaFileObject jfo = filer.createSourceFile(qualifiedClassName + SUFFIX);
        Writer writer = jfo.openWriter();

        /**Method construction*/
        MethodSpec.Builder methodBuilder =
                MethodSpec.methodBuilder("create" + superClassName.getSimpleName()) //create + Meal
                .addModifiers(Modifier.PUBLIC)
//                .returns(void.class) //vrati Meal
//                .addParameter(String.class, "id")
                /** Can't use */
//                .addParameter(Object.class, "id")
                .addParameter(TypeName.get(innerEnumClass.asType()) ,"id")
                .beginControlFlow("if(id == null)")
                .addStatement("throw new IllegalArgumentException(\"id is null!\")")
                .endControlFlow();

        TypeName returnType = TypeName.get(superClassName.asType());
        methodBuilder.returns(returnType);

        int i = 0;
        /** Assumption - number of @Factory annotated elements is equal to number of enum fields. */
        for(FactoryAnnotatedClass item : itemsMap.values())
//        for(int i = 0; i < itemsMap.values().size(); i++)
        {
            String name = superClassName.getSimpleName() + "."
                    + innerEnumClass.getSimpleName() + "."
                    + enumFields.get(i).getSimpleName().toString();

//            methodBuilder.beginControlFlow("if($S.equals(id))", item.getId())
//            methodBuilder.beginControlFlow("if(" + enumFields.get(i).getSimpleName().toString() + ".equals(id))")
            methodBuilder.beginControlFlow("if(" + name + ".equals(id))")
                        .addStatement("return new " + item.getTypeElement().getSimpleName().toString() + "()")
                        .endControlFlow();

            i++;
        }

        methodBuilder.addStatement("throw new IllegalArgumentException(\"Unknown id = \" + id)");
        MethodSpec createMethod = methodBuilder.build();
        //End of method construction

        /** Inner public Enum class declaration for meal types */
//        TypeSpec.Builder innerEnumBuilder = TypeSpec.enumBuilder(innerEnumClassName)
//                                            .addModifiers(Modifier.PUBLIC);
//
//        for(FactoryAnnotatedClass item : itemsMap.values())
//        {
//            innerEnumBuilder.addEnumConstant(item.getTypeElement().getSimpleName().toString());
//        }
        //End of inner public Enum class declaration for types
        /** Enclosing class construction */
        TypeSpec classSpec =
                TypeSpec.classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createMethod)
//                .addType(innerEnumBuilder.build())
                .build();
        //End of enclosing class construction

        /** End line. */
        JavaFile jf = JavaFile.builder(packageElement.toString(), classSpec).build();

        /** Add at end. */
        writer.write(jf.toString());
        writer.flush();
        writer.close();
    }
}
