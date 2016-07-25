package hr.span.tmartincic.pizza.processor;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import hr.span.tmartincic.pizza.Factory;

public class PizzaProcessor extends AbstractProcessor
{
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<String, FactoryGroupedClasses>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv)
    {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }


    /** Factory annotation should be processed by this processor. */
    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        Set<String> supportedAnnnotations = new LinkedHashSet<>();
        supportedAnnnotations.add(Factory.class.getCanonicalName());
        return supportedAnnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    public void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        /**Search for the classes annotated with Factory annotation. */
        for(Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class))
        {
            /**check if the annotatedElement is a class (annotatedElement can be anything)*/
            if(annotatedElement.getKind() != ElementKind.CLASS)
            {
                error(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                return true; //Exit processing
            }

            //The annotatedElement is a class, let's cast it to retrieve information
            TypeElement typeElement = (TypeElement) annotatedElement;

            try
            {
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

                if(!isValidClass(annotatedClass))
                {
                    return true; //Error message printed in isValidClass, exit processing
                }

                //everything is fine, try to add the class
                FactoryGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedFactoryGroupName());

                if(factoryClass == null)
                {
                    String qualifiedGroupName = annotatedClass.getQualifiedFactoryGroupName();
                    factoryClass = new FactoryGroupedClasses(qualifiedGroupName);
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // checks if the id is conflicting with another @Factory annotated class with the same id
                // (if so, IdAlreadyUsedException is thrown)
                factoryClass.add(annotatedClass);
            }
            catch(IllegalArgumentException iae)
            {
                //@Factory.id() is empty - exception is thrown from the constructor of the FactoryAnnotatedClass class
                //if the id was not supplied in the annotation
                error(typeElement, iae.getMessage());
                return true;
            }
            catch (IdAlreadyUsedException e)
            {
                FactoryAnnotatedClass existing = e.getExisting();

                error(annotatedElement,
                        "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                        typeElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        existing.getTypeElement().getQualifiedName().toString());
                return true;
            }
        }

        //All classes are stores in FactoryGroups, generate the code for them
        try
        {
            for(FactoryGroupedClasses factoryClass : factoryClasses.values())
            {
                factoryClass.generateCode(elementUtils, filer);
            }

            factoryClasses.clear();
        }
        catch(IOException e)
        {
            error(null, e.getMessage());
        }

        return true;
    }


    /**Checks if the element represented by FactoryAnnotated class is a valid candidate (has public parameterless constructor,
     * is not abstract, inherits a specific type and is public (visibility). */
    private boolean isValidClass(FactoryAnnotatedClass item)
    {
        //FactoryAnnotatedClass holds the element which it annotates, retrieve it for more specific information
        TypeElement classElement = item.getTypeElement();

        //check if the class is public
        if(!(classElement.getModifiers().contains(Modifier.PUBLIC)))
        {
            error(classElement, "The class %s is not public.", classElement.getQualifiedName().toString());
            return false;
        }

        //check if it is not abstract

        if(classElement.getModifiers().contains(Modifier.ABSTRACT))
        {
            error(classElement, "The class %s is abstract, it's not annotable with @%s annotation.",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
            return false;
        }

        //Check inheritance - class must be childclass as specified in @Factory.type();
        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedFactoryGroupName()); //Meal, Calzone implements Meal I suppose? todo

        if(superClassElement.getKind() == ElementKind.INTERFACE)
        {
            if(!classElement.getInterfaces().contains(superClassElement.asType()))
            {
                error(classElement, "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
                return false;
            }
        }
        else
        {
            //check subclassing
            TypeElement currentClass = classElement;

            while(true)
            {
                /** Provjeravaj znacenja, a ne elemente kao fizicke komponente. */
                TypeMirror superClassType = currentClass.getSuperclass();

                /** Reached Object class, so exit. */
                if(superClassType.getKind() == TypeKind.NONE)
                {
                    error(classElement, "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                    return false;
                }

                if(superClassType.toString().equals(item.getQualifiedFactoryGroupName()))
                {
                    //required super class found
                    break;
                }

                /** Pretvori znacenje u element, i kreni ispocetka. */
                //moving up in inheritance tree
                currentClass = (TypeElement) typeUtils.asElement(superClassType);
            }
        }

        // check if there is a parameterless constructor
        for(Element enclosed : classElement.getEnclosedElements())
        {
            if(enclosed.getKind() == ElementKind.CONSTRUCTOR)
            {
                //check for the amount of parameters
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if(constructorElement.getParameters().size() == 0 && constructorElement.getModifiers().contains(Modifier.PUBLIC))
                {
                    //Empty constructor found
                    return true;
                }
            }
        }

        //no empty constructor found
        error(classElement, "The class %s must provide an emptry public constructor", classElement.getQualifiedName().toString());
        return false;
    }
}
