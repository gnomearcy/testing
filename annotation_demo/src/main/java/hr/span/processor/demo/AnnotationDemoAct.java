package hr.span.processor.demo;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hr.span.processor.api.Generate;

@Generate
public class AnnotationDemoAct extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_demo);

        showMessageTomoProcessor();
    }

    private void showMessageTomoProcessor() {
        Object helloAnnotations = null;
        try {
            helloAnnotations = Class.forName("hr.span.tmartincic.annotation_demo.generated.GeneratedFromTomo").newInstance();

            String message = (String)invoke(helloAnnotations, "getMessage");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", null);
            builder.show();
        }
        catch(ClassNotFoundException cnfe)
        {
            Toast.makeText(this, "Class not found exception", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            e.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error processing annotations");
            builder.setMessage(e.getMessage());
            builder.setPositiveButton("Ok", null);
            builder.show();
        }
    }

    public static Object invoke(Object object, String methodName, Object... args) throws Exception {
        Class[] parameterTypes = new Class[args.length];
        Exception runtimeException = new Exception("Problem with dynamic invocation of method '" + methodName + "'");
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(object, args);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (NoSuchMethodException e) {

            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        }
    }
}
