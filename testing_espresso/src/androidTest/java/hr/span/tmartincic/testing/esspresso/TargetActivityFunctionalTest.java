//package hr.span.tmartincic.testing.esspresso;
//
//import android.test.ActivityInstrumentationTestCase2;
//import android.test.UiThreadTest;
//import android.widget.TextView;
//
///** Verifies the direct modification of a view. */
//public class TargetActivityFunctionalTest extends ActivityInstrumentationTestCase2<ActivityTwo>
//{
//    private static final String tag = "FunctionalTesting";
//
//    public TargetActivityFunctionalTest()
//    {
//        super(ActivityTwo.class);
//    }
//
////    private static final String NEW_TEXT = "new text";
////
////    public void testSetText() throws Exception
////    {
////        ActivityTwo activityTwo = getActivity();
////
////        final TextView textView = (TextView) activityTwo.findViewById(R.id.activitytwo_tv);
////
////        //set text
////        getActivity().runOnUiThread(new Runnable()
////        {
////            @Override
////            public void run()
////            {
////                textView.setText(NEW_TEXT);
////            }
////        });
////
////        getInstrumentation().waitForIdleSync();
////        assertEquals("Text incorrect", NEW_TEXT, textView.getText().toString());
////    }
////
////    @UiThreadTest
////    public void testSetTextWithAnnotation() throws Exception {
////
////        ActivityTwo activity = getActivity();
////
////        // search for the textView
////        final TextView textView = (TextView) activity
////                .findViewById(R.id.activitytwo_tv);
////
////        textView.setText(NEW_TEXT);
////        assertEquals("Text incorrect", NEW_TEXT, textView.getText().toString());
////    }
//}
