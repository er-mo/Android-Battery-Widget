/**
 * 
 */

package com.em.batterywidget.tests.launchers;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import com.em.batterywidget.AboutInfoActivity;
import com.em.batterywidget.R;

/**
 * @author erkmolla
 */
public class AboutInfoActivityTest extends ActivityUnitTestCase<AboutInfoActivity> {

    private Button mSummaryButton;
    private Intent mIntent;

    public AboutInfoActivityTest() {
        super(AboutInfoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mIntent = new Intent(Intent.ACTION_MAIN);
        // assertNotNull("Activity should be launched successfully",
        // getActivity());
        // startActivity(mIntent, null, null);
        // assertNotNull(getActivity());
    }

    @MediumTest
    public void testPreconditions() {
        // final BatteryWidgetActivity activity = getActivity();
        // mSummaryButton = (Button) activity.findViewById(R.id.summaryButton);
        // assertNotNull("Activity should be launched successfully",
        // getActivity());
    }

    @MediumTest
    public void testView() {
        AboutInfoActivity activity = getActivity();
        View mStatusView = activity.findViewById(R.id.state);
        View mPlugView = activity.findViewById(R.id.plug);
        View mLevelView = activity.findViewById(R.id.level);
        View mScaleView = activity.findViewById(R.id.scale);
        View mVoltageView = activity.findViewById(R.id.voltage);
        View mTemperatureView = activity.findViewById(R.id.temperature);
        View mTechnologyView = activity.findViewById(R.id.technology);
        View mHealthView = activity.findViewById(R.id.health);
        View mSummaryButton = activity.findViewById(R.id.summaryButton);
        View mSettingsButton = activity.findViewById(R.id.settingsButton);
        View mHistoryButton = activity.findViewById(R.id.historyButton);

        assertEquals(View.VISIBLE, mStatusView.getVisibility());
        assertEquals(View.VISIBLE, mPlugView.getVisibility());
        assertEquals(View.VISIBLE, mLevelView.getVisibility());
        assertEquals(View.VISIBLE, mScaleView.getVisibility());
        assertEquals(View.VISIBLE, mVoltageView.getVisibility());
        assertEquals(View.VISIBLE, mTemperatureView.getVisibility());
        assertEquals(View.VISIBLE, mTechnologyView.getVisibility());
        assertEquals(View.VISIBLE, mHealthView.getVisibility());
        assertEquals(View.VISIBLE, mSummaryButton.getVisibility());
        assertEquals(View.VISIBLE, mSettingsButton.getVisibility());
        assertEquals(View.VISIBLE, mHistoryButton.getVisibility());
    }

    @UiThreadTest
    public void testSubLaunch() {
        AboutInfoActivity activity = getActivity();
        mSummaryButton = (Button) activity.findViewById(R.id.summaryButton);
        mSummaryButton.performClick();
        assertNotNull(getStartedActivityIntent());
        assertTrue(isFinishCalled());
    }

    @MediumTest
    public void testLifeCycle() {
        AboutInfoActivity activity = getActivity();
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);
        getInstrumentation().callActivityOnPause(activity);
        getInstrumentation().callActivityOnStop(activity);
        getInstrumentation().callActivityOnDestroy(activity);
    }

}
