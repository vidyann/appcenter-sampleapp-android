package ms.appcenter.sampleapp.android;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.filters.LargeTest;


import org.junit.Rule;
import org.junit.After;
import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import androidx.test.espresso.accessibility.AccessibilityChecks;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();

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
        /*AccessibilityChecks.enable().setRunChecksFromRootView(true);*/
        AccessibilityChecks.enable();
        try (ActivityScenario scenario = ActivityScenario.launch(MainActivity.class)) {
           // onView(withId(R.id.crashButton)).perform(click());
            //onView(withId(R.id.titles)).perform(clickBetweenTwoTitles("Red", "Green"));
            onView(allOf(isDescendantOfA(withId(R.id.pager_title_strip)), withText("Build"))).perform(click());

        }
    }

}
