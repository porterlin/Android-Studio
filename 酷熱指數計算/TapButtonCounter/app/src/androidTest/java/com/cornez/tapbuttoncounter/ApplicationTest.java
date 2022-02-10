package com.cornez.tapbuttoncounter;

/*import android.app.Application;
import android.test.ApplicationTestCase;*/

import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}*/

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;
import android.widget.Toast;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    private static final String NOTE_1 = "Note 1";
    private static final String NOTE_2 = "Note 2";


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private Solo solo;


    @Before
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddNote() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();

        solo.clickOnView(solo.getView(R.id.celminus));
        solo.clickOnView(solo.getView(R.id.celminus));
        solo.clickOnView(solo.getView(R.id.celminus));
        solo.clickOnView(solo.getView(R.id.comp));

        TextView view = null;
        view = (TextView)solo.getView(R.id.textView2);

        assertEquals( "25.9" , view.getText ());
    }

    @Test
    public void testEditNoteTitle() throws Exception {
        for (int i =0; i < 2; i++)
            solo.clickOnView(solo.getView(R.id.celplus));
        solo.clickOnView(solo.getView(R.id.comp));

        TextView view = null;
        view = (TextView)solo.getView(R.id.textView2);

        assertEquals( "31.0" , view.getText ());
    }

    private void deleteNotes() {
        /*
        //Click on first item in List
        solo.clickInList(1);
        //Click on delete action menu item
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_delete));
        //Long click first item in List
        solo.clickLongInList(1);
        //Click delete
        solo.clickOnText(solo.getString(R.string.menu_delete));*/
    }
}
