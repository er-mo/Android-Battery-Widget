

package com.em.batterywidget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.em.batterywidget.R;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class FragmentPreferences extends Activity {
	
	protected Method GetFragmentManager = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	getFragmentManager().beginTransaction().replace(android.R.id.content,
    			new PrefsFragment()).commit();
    }
	
    
	public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            PreferenceManager.setDefaultValues(getActivity(), R.xml.options, false);

            addPreferencesFromResource(R.xml.options);
        }
    }

    
	public boolean methodExist() {
		
        if (GetFragmentManager!=null)
        {
            try 
            {
                GetFragmentManager.invoke(this);
                return true;        
            } catch (IllegalArgumentException  e) {
            } catch (IllegalAccessException    e) {
            } catch (InvocationTargetException e) {
            }
        }
        
        return false;
    }

}
