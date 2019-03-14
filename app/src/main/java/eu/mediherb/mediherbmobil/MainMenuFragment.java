package eu.mediherb.mediherbmobil;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnComplainSearch).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toComplainSearchFragment));
        view.findViewById(R.id.btnPhoto).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toPhotoFragment));
        view.findViewById(R.id.btnPlantIdent).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toPlantIdentFragment));
        view.findViewById(R.id.btnPlantsList).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toPlantListFragment));
    }
}
