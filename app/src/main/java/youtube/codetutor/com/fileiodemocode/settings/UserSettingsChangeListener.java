package youtube.codetutor.com.fileiodemocode.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by anildeshpande on 3/6/17.
 */

public class UserSettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG=UserSettingsChangeListener.class.getSimpleName();

    private Context mContext;


    public UserSettingsChangeListener(Context context){
        this.mContext =context;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Toast.makeText(mContext,s+" value changed: "+sharedPreferences.getString(s,"Unkown"),Toast.LENGTH_SHORT).show();
    }
}
