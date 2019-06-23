package eu.mediherb.mediherbmobil.classes.custom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class BugReport {
    private Context context;
    private Intent email;
    private String text;
    private final String[] destination = {"mediherb.dipl@gmail.com"};
    private final String cc = "markus197@live.at";
    private final String subject = "MediHerbMobil - BugReport";

    public BugReport(Context context, String text){
        this.context = context;
        this.text = text;
        this.email = new Intent(Intent.ACTION_SEND);

        try {
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, destination);
            email.putExtra(Intent.EXTRA_CC, cc);
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, text);
        }
        catch (Exception e){
            Toast.makeText(context, "EMail an mediherb.dipl@gmail.com", Toast.LENGTH_LONG).show();
        }
    }

    public void sendEmail(){
        try{
            context.startActivity(Intent.createChooser(email, "Womit soll die E-Mail versendet werden?"));
        }catch (android.content.ActivityNotFoundException e){
            Toast.makeText(context, "Es wurde kein E-Mail Client auf dem Gerät gefunden", Toast.LENGTH_LONG).show();
            Log.e("Exception", e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    public void sendToast(){
        Toast.makeText(context, "Ein Fehler ist aufgetreten, ein Bug Report ist möglich", Toast.LENGTH_LONG).show();
    }
}
