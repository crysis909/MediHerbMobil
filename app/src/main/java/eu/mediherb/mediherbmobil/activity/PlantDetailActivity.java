package eu.mediherb.mediherbmobil.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multilevelview.MultiLevelRecyclerView;
import com.multilevelview.models.RecyclerViewItem;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.mediherb.mediherbmobil.R;
import eu.mediherb.mediherbmobil.adapter.ViewPagerAdapter;
import eu.mediherb.mediherbmobil.classes.custom.BugReport;
import eu.mediherb.mediherbmobil.classes.api.MediHerbService;
import eu.mediherb.mediherbmobil.classes.api.RetrofitClient;
import eu.mediherb.mediherbmobil.classes.custom.Item;
import eu.mediherb.mediherbmobil.classes.custom.MyAdapter;
import eu.mediherb.mediherbmobil.classes.gson.Blossom;
import eu.mediherb.mediherbmobil.classes.gson.Botanical;
import eu.mediherb.mediherbmobil.classes.gson.Herb;
import eu.mediherb.mediherbmobil.classes.gson.HerbBlossom;
import eu.mediherb.mediherbmobil.classes.gson.HerbColor;
import eu.mediherb.mediherbmobil.classes.gson.Leaf;
import eu.mediherb.mediherbmobil.classes.gson.Medical;
import eu.mediherb.mediherbmobil.classes.gson.Pictures;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDetailActivity extends AppCompatActivity {

    private Herb herb;
    private Botanical botanical;
    private Medical medical;
    private List<Leaf> leaf;
    private Blossom blossom;
    private List<Item> itemList;
    private Pictures pictures;

    private MultiLevelRecyclerView listInfo;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        try {
            Gson gson = new Gson();
            String json = getIntent().getStringExtra("Herb");
            herb = gson.fromJson(json, Herb.class);

            viewPager = findViewById(R.id.herbDetailImage);
            toolbar = findViewById(R.id.herbDetailToolbar);

            listInfo = findViewById(R.id.herbDetailList);
            listInfo.setLayoutManager(new LinearLayoutManager(this));

            toolbar.setTitle(herb.getNameTrival());
            toolbar.setSubtitle(herb.getNameWissenschaft());

            itemList = new ArrayList<>();


            Item botItem = new Item(0);
            Item medItem = new Item(0);
            Item leafItem = new Item(0);
            Item blosItem = new Item(0);

            botItem.setText("Botanisch");
            medItem.setText("Medizinisch");
            leafItem.setText("Blatt");
            blosItem.setText("Blüte");

            itemList.add(botItem);
            itemList.add(medItem);
            itemList.add(leafItem);
            itemList.add(blosItem);

            setSupportActionBar(toolbar);

            loadJSON();
        }catch (Exception e){
            BugReport report = new BugReport(this, "Message:\n\n" + e.getMessage() + "\nStackTrace:\n\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }

    private List<?> addBotanical(Botanical botanical)
    {
        String[] mainTitles = {"Höhe", "Familie", "Pflanzentyp", "Auffälligkeiten", "Verbreitung", "Wuchsraum"};

        List<RecyclerViewItem> titleList = new ArrayList<>();
        List<RecyclerViewItem> heightList = new ArrayList<>();
        List<RecyclerViewItem> familieList = new ArrayList<>();
        List<RecyclerViewItem> typeList = new ArrayList<>();
        List<RecyclerViewItem> abnormList = new ArrayList<>();
        List<RecyclerViewItem> locationList = new ArrayList<>();
        List<RecyclerViewItem> areaList = new ArrayList<>();

        //Titles
        for (String mainTitle : mainTitles) {
            Item item = new Item(1);
            item.setText(mainTitle);
            titleList.add(item);
        }

        Item heightItem = new Item(2);
        heightItem.setText(herb.getHoeheFrom().toString() + "cm - " + herb.getHoeheTo().toString() + "cm");
        heightList.add(heightItem);

        Item familieItem = new Item(2);
        familieItem.setText(herb.getFamilie());
        familieList.add(familieItem);

        Item typeItem = new Item(2);
        typeItem.setText(herb.getPflanzentype());
        typeList.add(typeItem);

        Item abnormItem = new Item(2);
        abnormItem.setText(herb.getAuffaelligkeiten());
        abnormList.add(abnormItem);

        //Area
        for(int x = 0; x < botanical.getAreas().size(); x++){
            Item item = new Item(2);
            String subText = botanical.getAreas().get(x).getGebiet();

            item.setText(subText);
            areaList.add(item);
        }
        //Location
        for(int x = 0; x < botanical.getLocations().size(); x++){
            Item item = new Item(2);
            String subText = botanical.getLocations().get(x).getStandort();

            item.setText(subText);
            locationList.add(item);
        }

        titleList.get(0).addChildren(heightList);
        titleList.get(1).addChildren(familieList);
        titleList.get(2).addChildren(typeList);
        titleList.get(3).addChildren(abnormList);
        titleList.get(4).addChildren(areaList);
        titleList.get(5).addChildren(locationList);

        return new ArrayList<>(titleList);
    }

    private List<?> addMedical(Medical medical)
    {
        String[] mainTitles = {"Wirkstoff", "Dosierung", "Hilft bei", "Einnahme", "Verwendbar", "Nebenwirkungen"};
        List<RecyclerViewItem> titleList = new ArrayList<>();
        List<RecyclerViewItem> subnList = new ArrayList<>();
        List<RecyclerViewItem> dosList = new ArrayList<>();
        List<RecyclerViewItem> helpList = new ArrayList<>();
        List<RecyclerViewItem> takeList = new ArrayList<>();
        List<RecyclerViewItem> useList = new ArrayList<>();
        List<RecyclerViewItem> sideList = new ArrayList<>();

        //Titles
        for (String mainTitle : mainTitles) {
            Item item = new Item(1);
            item.setText(mainTitle);
            titleList.add(item);
        }

        for(int x = 0; x < medical.getSubstances().size(); x++){
            String substance = medical.getSubstances().get(x).getSubstance();
            Item subItem = new Item(2);
            subItem.setText(substance);
            subnList.add(subItem);
        }

        Item dosItem = new Item(2);
        dosItem.setText(herb.getDosierung());
        dosList.add(dosItem);

        for(int x = 0; x < medical.getSymptoms().size(); x++){
            String symptom = medical.getSymptoms().get(x).getSymptom();
            Item helpItem = new Item(2);
            helpItem.setText(symptom);
            helpList.add(helpItem);
        }

        for(int x = 0; x < medical.getTakings().size(); x++){
            String take = medical.getTakings().get(x).getTaking();
            Item takeItem = new Item(2);
            takeItem.setText(take);
            takeList.add(takeItem);
        }

        for(int x = 0; x < medical.getParts().size(); x++){
            String part = medical.getParts().get(x).getPart();
            Item partItem = new Item(2);
            partItem.setText(part);
            useList.add(partItem);
        }

        for(int x = 0; x < medical.getSideEffects().size(); x++){
            String sideEffect = medical.getSideEffects().get(x).getNebenwirkung();
            Item sideItem = new Item(2);
            sideItem.setText(sideEffect);
            sideList.add(sideItem);
        }

        titleList.get(0).addChildren(subnList);
        titleList.get(1).addChildren(dosList);
        titleList.get(2).addChildren(helpList);
        titleList.get(3).addChildren(takeList);
        titleList.get(4).addChildren(useList);
        titleList.get(5).addChildren(sideList);

        return new ArrayList<>(titleList);
    }

    private List<?> addLeaf(Leaf leaf)
    {
        String[] mainTitles = {"Farbe", "Blattform", "Blattrand"};

        List<RecyclerViewItem> titleList = new ArrayList<>();
        List<RecyclerViewItem> colorList = new ArrayList<>();
        List<RecyclerViewItem> leafFormList = new ArrayList<>();
        List<RecyclerViewItem> leafEdgeList = new ArrayList<>();

        for (String mainTitle : mainTitles) {
            Item item = new Item(1);
            item.setText(mainTitle);
            titleList.add(item);
        }

        Item color = new Item(2);
        color.setText(leaf.getFarbe());
        colorList.add(color);

        Item leafForm = new Item(2);
        leafForm.setText(leaf.getForm());
        leafFormList.add(leafForm);

        Item leafEdge = new Item(2);
        leafEdge.setText(leaf.getRand());
        leafEdgeList.add(leafEdge);

        titleList.get(0).addChildren(colorList);
        titleList.get(1).addChildren(leafFormList);
        titleList.get(2).addChildren(leafEdgeList);

        return new ArrayList<>(titleList);
    }


    private List<?> addBlossom(Blossom blossom)
    {
        String[] mainTitles = {"Farben", "Blütezeit", "Blattform", "Blattrand"};

        List<RecyclerViewItem> titleList = new ArrayList<>();
        List<RecyclerViewItem> colorList = new ArrayList<>();
        List<RecyclerViewItem> heydayList = new ArrayList<>();
        List<RecyclerViewItem> shapeList = new ArrayList<>();
        List<RecyclerViewItem> infloreList = new ArrayList<>();

        //Titles
        for (String mainTitle : mainTitles) {
            Item item = new Item(1);
            item.setText(mainTitle);
            titleList.add(item);
        }

        //Colors
        for(int x = 0; x < blossom.getColors().size(); x++){
            Item item = new Item(2);
            HerbColor color = blossom.getColors().get(x);
            String subText = color.getColor();

            item.setText(subText);
            colorList.add(item);
        }

        //Heyday / Shape / Inflorescence
        for(int x = 0; x < blossom.getHerbs().size(); x++){
            HerbBlossom herbBlossom = blossom.getHerbs().get(x);

            Item heydayItem = new Item(2);
            Item shapeItem = new Item(2);
            Item infloreItem = new Item(2);

            String heydayText = "von " + getMonth(herbBlossom.getBluetezeitFrom()) + " bis " + getMonth(herbBlossom.getBluetezeitTo());
            String shapeText = herbBlossom.getBluetenform();
            String infloreText = herbBlossom.getStand();

            heydayItem.setText(heydayText);
            shapeItem.setText(shapeText);
            infloreItem.setText(infloreText);

            heydayList.add(heydayItem);
            shapeList.add(shapeItem);
            infloreList.add(infloreItem);
        }

        titleList.get(0).addChildren(colorList);
        titleList.get(1).addChildren(heydayList);
        titleList.get(2).addChildren(shapeList);
        titleList.get(3).addChildren(infloreList);

        return new ArrayList<>(titleList);
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    private void createList(){
        MyAdapter myAdapter = new MyAdapter(this, itemList, listInfo);

        listInfo.setAdapter(myAdapter);

        //If you are handling the click on your own then you can
        //multiLevelRecyclerView.removeItemClickListeners();
        listInfo.setAccordion(false);

    }

    private void createBotanical(Botanical botanical)
    {
        this.botanical = botanical;

        for (Item item: itemList) {
            if(item.getText().equals("Botanisch"))
                item.addChildren((List<RecyclerViewItem>) addBotanical(botanical));
        }

        createList();
    }

    private void createMedical(Medical medical)
    {
        this.medical = medical;

        for (Item item: itemList) {
            if(item.getText().equals("Medizinisch"))
                item.addChildren((List<RecyclerViewItem>) addMedical(medical));
        }

        createList();
    }

    private void createLeaf(Leaf leaf)
    {
        this.leaf.set(0, leaf);

        for (Item item: itemList) {
            if(item.getText().equals("Blatt"))
                item.addChildren((List<RecyclerViewItem>) addLeaf(leaf));
        }

        createList();
    }

    private void createBlossom(Blossom blossom)
    {
        this.blossom = blossom;

        for (Item item: itemList) {
            if(item.getText().equals("Blüte"))
                item.addChildren((List<RecyclerViewItem>) addBlossom(blossom));
        }

        createList();

    }

    private void createPictures(Pictures pictures) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, pictures.getPictures());
        viewPager.setAdapter(adapter);
    }

    private void loadJSON() {
        try {
            int herbID = herb.getHerbID();
            MediHerbService service = RetrofitClient.getRetrofit().create(MediHerbService.class);

            final Call<Botanical> botCall = service.getBotanicalInfo(herbID);
            final Call<Medical> medCall = service.getMedicalInfo(herbID);
            final Call<List<Leaf>> leafCall = service.getLeafInfo(herbID);
            final Call<Blossom> blosCall = service.getBlossomInfo(herbID);
            final Call<Pictures> picCall = service.getPictures(herbID);

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(PlantDetailActivity.this);
            progressDialog.setMessage("Lade....");
            progressDialog.setTitle("Details");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();

            botCall.enqueue(new Callback<Botanical>() {
                @Override
                public void onResponse(@NotNull Call<Botanical> call, @NotNull Response<Botanical> response) {
                    try {
                        botanical = response.body();
                        progressDialog.dismiss();
                        createBotanical(botanical);
                    }
                    catch (Exception e){
                        Toast.makeText(PlantDetailActivity.this, "Es gibt ein Problem mit der Datenbank, es konnten leider nicht alle Botanischedaten abgefragt werden.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Botanical> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantDetailActivity.this, "Unable to load botanical details\nCheck your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            medCall.enqueue(new Callback<Medical>() {
                @Override
                public void onResponse(@NotNull Call<Medical> call, @NotNull Response<Medical> response) {
                    try {
                        medical = response.body();
                        progressDialog.dismiss();
                        createMedical(medical);
                    }
                    catch (Exception e){
                        Toast.makeText(PlantDetailActivity.this, "Es gibt ein Problem mit der Datenbank, es konnten leider nicht alle Medizinischedaten abgefragt werden.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Medical> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantDetailActivity.this, "Unable to load medical details\nCheck your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            leafCall.enqueue(new Callback<List<Leaf>>() {
                @Override
                public void onResponse(@NotNull Call<List<Leaf>> call, @NotNull Response<List<Leaf>> response) {
                    try {
                        leaf = response.body();
                        progressDialog.dismiss();
                        createLeaf(leaf.get(0));
                    }
                    catch (Exception e){
                        Toast.makeText(PlantDetailActivity.this, "Es gibt ein Problem mit der Datenbank, es konnten leider nicht alle Daten des Blattes abgefragt werden.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Leaf>> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantDetailActivity.this, "Unable to load leaf details\nCheck your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            blosCall.enqueue(new Callback<Blossom>() {
                @Override
                public void onResponse(@NotNull Call<Blossom> call, @NotNull Response<Blossom> response) {
                    try {
                        blossom = response.body();
                        progressDialog.dismiss();
                        createBlossom(blossom);
                    }
                    catch (Exception e) {
                        Toast.makeText(PlantDetailActivity.this, "Es gibt ein Problem mit der Datenbank, es konnten leider nicht alle Daten der Blühte abgefragt werden.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Blossom> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantDetailActivity.this, "Unable to load blossom deatils\nCheck your internet connection", Toast.LENGTH_SHORT).show();
                    Log.e("API", t.getMessage());
                }
            });

            picCall.enqueue(new Callback<Pictures>() {
                @Override
                public void onResponse(@NotNull Call<Pictures> call, @NotNull Response<Pictures> response) {
                    try {
                        pictures = response.body();
                        progressDialog.dismiss();
                        createPictures(pictures);
                    }
                    catch (Exception e){
                        Toast.makeText(PlantDetailActivity.this, "Es gibt ein Problem mit der Datenbank, es konnten leider nicht alle Bilder abgefragt werden.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Pictures> call, @NotNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(PlantDetailActivity.this, "Unable to load Pictures\nCheck your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            BugReport report = new BugReport(this, "Message:\n\n" + e.getMessage() + "\nStackTrace:\n\n" + Arrays.toString(e.getStackTrace()));
            report.sendToast();
            report.sendEmail();
        }
    }

}
