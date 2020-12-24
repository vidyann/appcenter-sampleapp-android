package ms.appcenter.sampleapp.android;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;


import org.junit.Rule;
import org.junit.After;
import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import com.google.android.apps.common.testing.accessibility.framework.integrations.AccessibilityViewCheckException;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import java.util.List;
import java.util.Locale;
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityViewCheckResult;




/**
 * Instrumentation test, which will execute on an Android device. The tests also include accessibility checks
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();
    StringBuilder exceptionMessage = new StringBuilder();


    @After
    public void TearDown() {
        reportHelper.label("Stopping App");
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ms.appcenter.sampleapp.android", appContext.getPackageName());
    }

    
    @Test
    public void accessibilityChecks() {
        //Enable accessibility and retry the checks
        try {
            AccessibilityChecks.enable().setRunChecksFromRootView(true);
            ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);
            //AccessibilityChecks.enable();
            //try (ActivityScenario scenario = ActivityScenario.launch(MainActivity.class)) {
            try {
                // onView(withId(R.id.crashButton)).perform(click());
                //onView(withId(R.id.titles)).perform(clickBetweenTwoTitles("Red", "Green"));
                onView(allOf(isDescendantOfA(withId(R.id.pager_title_strip)), withText("Build"))).perform(click());
            }
            catch (AccessibilityViewCheckException e) {
                //assertEquals(1, e.getResults().size());
                //reportHelper.label(e.getResults().get(0).getMessage().toString());
                reportHelper.label(getMessages(e, "WelcomeView"));

            }
            try {
                onView(allOf(isDescendantOfA(withId(R.id.pager_title_strip)), withText("Test"))).perform(click());
            }
            catch (AccessibilityViewCheckException e) {
                reportHelper.label(getMessages(e, "BuildView"));
            }
            try {
                onView(allOf(isDescendantOfA(withId(R.id.pager_title_strip)), withText("Distribute"))).perform(click());
            }
            catch (AccessibilityViewCheckException e) {
                reportHelper.label(getMessages(e, "TestView"));
            }


        } catch (IllegalStateException exception) {
        }
    }
    
    private String getMessages(AccessibilityViewCheckException e, String viewName) {
        // Lump all error result messages into one string to be the exception message
        List<AccessibilityViewCheckResult> results = e.getResults();

        String errorCountMessage = (results.size() == 1) ? "There was 1 accessibility error in " + viewName + ":\n" : String.format(Locale.US, "There were %d accessibility errors in " + viewName + ":\n", results.size());
        exceptionMessage.append(errorCountMessage);
        for (int i = 0; i < results.size(); i++) {
            if (i > 0) {
                exceptionMessage.append(",\n");
            }
            AccessibilityViewCheckResult result = results.get(i);
            //exceptionMessage.append(resultDescriptor.describeResult(result));
            exceptionMessage.append(result.getMessage().toString());
        }
        return exceptionMessage.toString();
    }

    

}
