package eu.mediherb.mediherbmobil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import eu.mediherb.mediherbmobil.adapter.ExpTextView;
import eu.mediherb.mediherbmobil.adapter.MainButtonAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private MainButtonAdapter _adpadter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_2);

        list = findViewById(R.id.main_list);
        ArrayList<MainRow>rows = new ArrayList<>();
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));

        _adpadter = new MainButtonAdapter(this, this, rows);
        list.setAdapter(_adpadter);
        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainRow m = (MainRow) _adpadter.getItem(position);
        switch (position) {
            case 1:
                Intent intent = new Intent(this, Activity.class);
                intent.putExtra("NAME PARAMETER", "wert");
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;


        }
    }
}
