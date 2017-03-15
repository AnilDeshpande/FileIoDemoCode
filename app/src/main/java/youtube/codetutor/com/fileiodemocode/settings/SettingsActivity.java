package youtube.codetutor.com.fileiodemocode.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import youtube.codetutor.com.fileiodemocode.R;

/**
 * Created by anildeshpande on 3/9/17.
 */

public class SettingsActivity extends PreferenceActivity {

    public static final String TAG=SettingsActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_pref_settins);

    }
}
