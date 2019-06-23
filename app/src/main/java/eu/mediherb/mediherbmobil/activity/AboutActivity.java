package eu.mediherb.mediherbmobil.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import eu.mediherb.mediherbmobil.R;


public class AboutActivity extends AppCompatActivity {
    TextView aboutView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final String aboutLink = getString(R.string.aboutLink);
        String aboutText = getString(R.string.aboutText);

        aboutView = findViewById(R.id.aboutTextView);

        SpannableString content = new SpannableString(aboutText + "\n\n" + aboutLink);
        content.setSpan(new UnderlineSpan(), aboutText.length() , content.length(),0);

        aboutView.setText(content);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(aboutLink));
                startActivity(i);
            }
        });

    }

}
