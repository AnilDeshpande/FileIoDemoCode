package youtube.codetutor.com.fileiodemocode;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by anildeshpande on 2/25/17.
 */

public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected static final String [] EXTERNAL_STORAGE_READ_WRITE_PERMISSIONS ={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    protected static final int EXTERNAL_STORAGE_PERMISSION =10;


    protected File externalStorageDirectory;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected String readFromFile(String fileName) throws FileNotFoundException, IOException {
        String readString="";

        FileInputStream fileInputStream=openFileInput(fileName);
        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder=new StringBuilder(readString);
        while ((readString=bufferedReader.readLine())!=null){
            stringBuilder.append(readString);
        }
        inputStreamReader.close();
        return stringBuilder.toString();
    }

    protected void writeToFile(String fileName,String sourceText,int MODE) throws
            FileNotFoundException,IOException{

        FileOutputStream fileOutputStream=openFileOutput(fileName,MODE);
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(sourceText);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private File writeToFile(File file,String sourceText) throws
            FileNotFoundException, IOException{
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write(sourceText);
        fileWriter.flush();
        fileWriter.close();
        return file;
    }

    protected boolean isStringEmpty(String string){
        if(string!=null && !string.equals("") && string.length()>0){
            return true;
        }else {
            return false;
        }
    }

    protected void requestRunTimePermissions(final Activity activity, final String [] permissions, final int customPermissionConstant, String reason){
        if(permissions.length==1){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])){

                Snackbar.make(findViewById(android.R.id.content),"App needs permission to work",Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,permissions,customPermissionConstant);
                            }
                        }).show();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{permissions[0]},customPermissionConstant);
            }
        }else if(permissions.length>1){
            Snackbar.make(findViewById(android.R.id.content),"App needs multiple permissions to "+reason,Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(activity,permissions,customPermissionConstant);
                }
            }).show();
        }
    }

    protected boolean arePermissionsGranted(String [] permissions){
        for(String permission: permissions){
            if(ActivityCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }

    protected void writeToExternalStorageFile(String filename,String text) throws
            FileNotFoundException, IOException {
        if(isExternalStorageWritable()){
            externalStorageDirectory=new File(Environment.getExternalStorageDirectory(),this.getPackageName());
            externalStorageDirectory.mkdir();
            writeToFile(new File(externalStorageDirectory,filename),text);
        }
    }

    protected String readTextFromExternalStorage(String fileName) throws
            FileNotFoundException, IOException{
        String readString="";
        if(isExternalStorageReadable()){
            if(externalStorageDirectory==null){
                externalStorageDirectory=new File(Environment.getExternalStorageDirectory(),this.getPackageName());
            }
            readString=readFromFile(new File(externalStorageDirectory.getAbsolutePath()+"/"+fileName));
        }
        return readString;
    }

    private String readFromFile(File file) throws FileNotFoundException, IOException{
       String readString="";
       BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
       StringBuilder stringBuilder=new StringBuilder(readString);
       while ((readString=bufferedReader.readLine())!=null){
           stringBuilder.append(readString);
       }
       bufferedReader.close();
       return stringBuilder.toString();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
