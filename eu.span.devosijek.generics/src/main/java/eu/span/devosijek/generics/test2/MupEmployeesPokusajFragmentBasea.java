//package eu.span.devosijek.generics.test2;
//
//import android.util.Log;
//
//import java.util.EnumSet;
//
///**
// * Created by tmartincic on 9/4/2015.
// */
//public class MupEmployeesPokusajFragmentBasea
//{
//
//    public <T> T check(Methods method, Object...args)
//    {
//        T result;
//
//        switch(method)
//        {
//            case IS_FILE_ON_DEVICE:
//            {
//                if(args.length > 1)
//                    throwArgCountException(Methods.IS_FILE_ON_DEVICE);
//                if(!(args[0] instanceof String))
//                    throwArgTypeException(Methods.IS_FILE_ON_DEVICE);
//                result = (T) new Boolean(Utils.isFileOnDevice((String) args[0]));
//                return result;
//            }
//            case SHOW_MESSAGE:
//            {
//                if(args.length > 1)
//                    throwArgCountException(Methods.SHOW_MESSAGE);
//                if(!(args[0] instanceof Integer || args[0] instanceof String))
//                    throwArgTypeException(Methods.SHOW_MESSAGE);
//
//                if(args[0] instanceof Integer)
//                    showMessage((Integer) args[0]);
//                else if(args[0] instanceof String)
//                    showMessage((String)args[0]);
//                break;
//            }
//            case SAVE_TO_LOCAL_STORAGE:
//            {
//                Utils.saveToLocalStorage(getActivity());
//                break;
//            }
//            case IS_CONNECTING_TO_INTERNET:
//            {
//                Utils.isConnectingToInternet(getActivity());
//                break;
//            }
//            case LOGOUT:
//            {
//                ((ActivityBase)getActivity()).logout();
//            }
//        }
//
//        return (T) Void.class;
//    }
//
//    public enum Methods
//    {
//        IS_FILE_ON_DEVICE,
//        SHOW_MESSAGE,
//        SAVE_TO_LOCAL_STORAGE,
//        IS_CONNECTING_TO_INTERNET,
//        LOGOUT
//    }
//
//
//
//    private static void throwArgCountException(Methods whichMethod)
//    {
//        String message = "";
//
//        switch(whichMethod)
//        {
//            case IS_FILE_ON_DEVICE:
//                message = Methods.IS_FILE_ON_DEVICE.name() + " - Required argument number - 1";
//                break;
//            case SAVE_TO_LOCAL_STORAGE:
//                //Ignore
//                break;
//            case SHOW_MESSAGE:
//                message = Methods.SHOW_MESSAGE.name() + " - Required argument number - 1";
//                break;
//        }
//
//        throw new RuntimeException(message);
//    }
//
//    private static void throwArgTypeException(Methods whichMethod)
//    {
//        String message = null;
//
//        switch(whichMethod)
//        {
//            case IS_FILE_ON_DEVICE:
//                message = Methods.IS_FILE_ON_DEVICE.name() + " - Required argument type - String";
//                break;
//            case SAVE_TO_LOCAL_STORAGE:
//                //Ignore
//                break;
//            case SHOW_MESSAGE:
//                message = Methods.SHOW_MESSAGE.name() + " - Required argument type - Integer OR String";
//                break;
//        }
//
//        throw new RuntimeException(message);
//    }
//
//    public static final String logout = "logout";
//    public static final String isFileOnDevice = "isFileOnDevice";
//    public static final String showMessage = "showMessage";
//    public static final String saveToLocalStorage = "saveToLocalStorage";
//    public static final String isConnectingToInternet = "isConnectingToInternet";
//    public static final String hasSdCard = "hasSdCard";
//
//    public static class nekaMetoda<Integer, String, Float>
//    {
//
//    }
//
//    public enum Method
//    {
//        metoda1, metoda2;
//
//        public void metodaZaPozvati(Integer a, String b, Float c) {}
//    }
//
//    public <E extends Enum<E>> String me(Enum<E> method)
//    {
//        Log.d("muptest", method.getClass().getSimpleName() + " " + method.name());
//        Class<Method> klasa = Method.class;
//        EnumSet.allOf(klasa);
//
//        return null;
//    }
//
//    public <T> T something(String method, Object...args)
//    {
//        T result;
//
//        switch(method)
//        {
//            case logout:
//                ((ActivityBase)getActivity()).logout();
//                break;
//            case isConnectingToInternet:
//                Utils.isConnectingToInternet(getActivity());
//                break;
//            case saveToLocalStorage:
//                Utils.saveToLocalStorage(getActivity());
//                break;
//            case isFileOnDevice:
//                if(args.length > 1)
//                    throwArgCountException(Methods.IS_FILE_ON_DEVICE);
//                if(!(args[0] instanceof String))
//                    throwArgTypeException(Methods.IS_FILE_ON_DEVICE);
//                result = (T) new Boolean(Utils.isFileOnDevice((String) args[0]));
//                return result;
//            case showMessage:
//                if(args.length > 1)
//                    throwArgCountException(Methods.SHOW_MESSAGE);
//                if(!(args[0] instanceof Integer || args[0] instanceof String))
//                    throwArgTypeException(Methods.SHOW_MESSAGE);
//                if(args[0] instanceof Integer)
//                    showMessage((Integer)args[0]);
//                else if(args[0] instanceof String)
//                    showMessage((String)args[0]);
//                break;
//            case hasSdCard:
//                result = (T) new Boolean(Utils.hasSdCard());
//                return result;
//        }
//
//        return (T) Void.class;
//    }
//}
