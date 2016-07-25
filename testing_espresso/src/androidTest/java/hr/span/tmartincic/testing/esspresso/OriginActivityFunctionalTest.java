//package hr.span.tmartincic.testing.esspresso;
//
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.AmbiguousViewMatcherException;
//import android.support.test.espresso.NoMatchingViewException;
//import android.support.test.espresso.ViewAction;
//import android.support.test.espresso.ViewFinder;
//import android.test.ActivityInstrumentationTestCase2;
//import android.test.InstrumentationTestSuite;
//import android.test.suitebuilder.TestSuiteBuilder;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import org.junit.Before;
//import org.junit.runners.AllTests;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//import static android.support.test.espresso.assertion.ViewAssertions.*;
//
////espresso
//public class OriginActivityFunctionalTest extends ActivityInstrumentationTestCase2<EspressoOne>
//{
//    private ActivityOne activityOne;
//    private EspressoOne espressoOne;
//
//    public OriginActivityFunctionalTest()
//    {
//        super(EspressoOne.class);
//    }
//
//    /**
//     * Input some text into EditText
//     * Click the button
//     * Output the text to TextView
//     * Assert that EditText and TextView contain the same text
//     */
//
//    private static final String text = "testing espresso";
//    private static final String wrong = "wrong";
//    private static final String textToInput = "input text";
//
//    @Before
//    public void setUp() throws Exception
//    {
//        super.setUp();
//
//        /**If you want to send key events via your test, you have to turn off the
//         * touch mode in the emulator via setActivityInitialTouchMode(false) in your setup() method of the test.*/
//        setActivityInitialTouchMode(false);
//
//        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
//        espressoOne = getActivity();
//    }
//
//    /**Inputs text to EditText, clicks a button which copies the entered text to the TextView*/
//    public void testTransferText() throws Exception
//    {
//        //clear the editText and input some text
//        onView(withId(R.id.espressoone_et))
//                .perform(clearText())
//                .perform(typeText(textToInput), closeSoftKeyboard());
//
//        //clear the textView -> throws error:
//        //Error performing 'replace text' on view...
////        onView(withId(R.id.espressoone_tv))
////                .perform(clearText());
//
//        //Workaround -> define our own custom ViewAction
//        TextView tv = (TextView) espressoOne.findViewById(R.id.espressoone_tv);
//        Log.d("EspressoTesting", "TextView text before - '" + tv.getText().toString() + "'");
//        ViewAction clearTv = new ClearTextViewAction();
//        onView(withId(R.id.espressoone_tv)).perform(clearTv);
//        Log.d("EspressoTesting", "TextView text after - '" + tv.getText().toString() + "'");
//
//        //perform button click -> click should copy the string from EditText to TextView
//        onView(withId(R.id.espressoone_btn))
//                .perform(click());
//
//        //verify that the entered text in TextView is equal to the one in EditText
//        onView(withId(R.id.espressoone_tv))
//                .check(matches(withText(textToInput)));
//    }
//
//    /**Starts the second activity with a button click*/
//    public void testStartEspressoTwo() throws Exception
//    {
//        onView(withId(R.id.espressoone_starttwo_btn)).perform(click());
//    }
//
//    /**Tell android framework to run this test tolerance amount of times and
//     * declare the test as failed if it fails tolerance amount of times.
//     *
//     * android.test package testing
//     * */
////    @FlakyTest(tolerance = 3)
////    public void testStartSecondActivity() throws Exception
////    {
////        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ActivityTwo.class.getName(), null, false);
////
////        //find button and click it
////        Button btn = (Button) activityOne.findViewById(R.id.espresso_btn);
////
////        //clicks the view
////        TouchUtils.clickView(this, btn);
////
////        //waits 2 seconds for the ActivityTwo to start, otherwise return null
////        ActivityTwo activityTwo = (ActivityTwo) monitor.waitForActivityWithTimeout(2000);
////        assertNotNull(activityTwo);
////
////        TextView textView = (TextView) activityTwo.findViewById(R.id.activitytwo_textview);
////
////        //check that the textview is on the screen
////        ViewAsserts.assertOnScreen(activityTwo.getWindow().getDecorView(), textView);
////
////        Log.d("FunctionalTesting", "Tw text - " + textView.getText().toString());
////
////        //validate text
////        assertEquals("Text incorrect", activityTwo.getResources().getString(R.string.activity_two_textview), textView.getText().toString());
////
////        //pressback and click again
////        this.sendKeys(KeyEvent.KEYCODE_BACK);
////        TouchUtils.clickView(this, btn);
////    }
//}
//
