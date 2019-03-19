package eu.mediherb.mediherbmobil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import eu.mediherb.mediherbmobil.MainRow;
import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.MainButtonAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private MainButtonAdapter _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_2);

        list = findViewById(R.id.mainList);
        ArrayList<MainRow>rows = new ArrayList<>();
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));
        rows.add(new MainRow(R.drawable.button_border, R.string.text1, R.string.text2));

        _adapter = new MainButtonAdapter(this, this, rows);
        list.setAdapter(_adapter);
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
        MainRow m = (MainRow) _adapter.getItem(position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, PhotoActivity.class);
                //intent.putExtra("NAME PARAMETER", "wert");
                break;
            case 1:
                intent = new Intent(this, PlantIdentActivity.class);

                break;
            case 2:
                intent = new Intent(this, ComplainSearchActivity.class);
                break;
            case 3:
                intent = new Intent(this, PlantListActivity.class);
                break;
            case 4:
                intent = new Intent(this, AboutActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
