package eu.mediherb.mediherbmobil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import eu.mediherb.mediherbmobil.classes.MainRow;
import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.MainButtonAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private MainButtonAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.mainList);
        ArrayList<MainRow>rows = new ArrayList<>();
        rows.add(new MainRow(R.drawable.ic_photo_camera, R.string.photoTextBig, R.string.photoTextSmall));
        rows.add(new MainRow(R.drawable.ic_leaf, R.string.plantIdentTextBig, R.string.plantIdentTextSmall));
        rows.add(new MainRow(R.drawable.ic_medidosis, R.string.compainSearchTextBig, R.string.compainSearchTextSmall));
        rows.add(new MainRow(R.drawable.ic_search, R.string.plantsListBig, R.string.plantsListSmall));

        _adapter = new MainButtonAdapter(this, this, rows);
        list.setAdapter(_adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.action_menu_home:
                Toast.makeText(this, "Home gedrückt!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_menu_photo:
                Toast.makeText(this, "Fotoerkennung gedrückt!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_menu_plantident:
                Toast.makeText(this, "PlantIdent gedrückt!", Toast.LENGTH_LONG).show();
                return true;
            case  R.id.action_menu_complain:
                Toast.makeText(this, "Beschwerde gedrückt!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_menu_about:
                Toast.makeText(this, "Über gedrückt!", Toast.LENGTH_LONG).show();
                return true;

            default:
                    return super.onOptionsItemSelected(item);
        }



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
