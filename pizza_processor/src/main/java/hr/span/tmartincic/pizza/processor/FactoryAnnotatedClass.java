package hr.span.tmartincic.pizza.processor;


import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import org.apache.commons.lang3.StringUtils;

import hr.span.tmartincic.pizza.Factory;

public class FactoryAnnotatedClass
{
    private TypeElement annotatedClassElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    private String id;

    public FactoryAnnotatedClass(TypeElement classElement) throws IllegalArgumentException
    {
        this.annotatedClassElement = classElement;

        /** Retrieve the annotation that's written above the class signature. */
        Factory annotation = classElement.getAnnotation(Factory.class);
        id = annotation.id(); //Read the id value (like "Calzone" or "Tiramisu")

        /**We are throwing the error exception here for the process method of the Processor to handle.
         * Otherwise we would have to pass the Messager object to this class instance and notify the process
         * method later to print out the message. */
        if(StringUtils.isEmpty(id))
        {
            //qualified name - name of the package + simple name of the class ie. package.com.MyClass
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty, that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }

        /**Get the class for the annotation
         * try block is executed if the class is already compiled (probably in an external jar)
         * catch block executes if the class is our own class, therefor it's not compiled at compile time
         * (before Processor runs) */
        try
        {
            Class<?> clazz = annotation.type();
            qualifiedSuperClassName = clazz.getCanonicalName();
            simpleTypeName = clazz.getSimpleName();
        }
        /** Thrown if we try to access non-compiled class (we cannot access its' "name" fields).
         * The exception contains TypeMirror representation of our non-compiled class.*/
        catch (MirroredTypeException mte)
        {
            //cast is to DeclaredType since we know that this is a class (checked before in processor)
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }

    public String getId()
    {
        return id;
    }

    public String getQualifiedFactoryGroupName()
    {
        return qualifiedSuperClassName;
    }

    public String getSimpleFactoryGroupName()
    {
        return simpleTypeName;
    }

    /**
     * The original element that was annotated with @Factory
     */
    public TypeElement getTypeElement()
    {
        return annotatedClassElement;
    }
}
