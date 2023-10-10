package com.example.wecar.pagess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wecar.MainActivity;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.R;
import com.example.wecar.D_FireBase.Car;
import com.example.wecar.utilities.myAdapter1;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private Spinner NameCarSpinner;
    private Spinner carModelSpinner;
    private Spinner startYearSpinner;
    private Spinner endYearSpinner;
    //private Spinner CarColorSpinner;

    private Button searchButton;
    private Button clearButton;

    RecyclerView recyclerView;
    FirebaseServices fbs;
    myAdapter1 myAdapter;
    ArrayList<Car> list, filteredList,modelscar;

//    private String[] MercedesListModels = {"Mercedes : ","a160","a170","a180","a190","a200","a220"
//            ,"a250","a35","a45 amg","c160","c180","c200","c230","c240","c250","c280","c300"
//            ,"c300e","c400","c450","c450","cla180","cla200","cla220","cla250","cla35 amg","cls220","cls280"
//            ,"cls300","cls320","cls350","cls400","cls450","cls500","cls63 amg"
//            ,"e200","e220","e250","e300","e350","e400","e43 amg","e63 amg"
//            ,"s250","s300","s350","s400","s450","s500","s63 amg","s650","slk"
//            ,"glc","gle","eqb"
//    };

//    private String[] BmwListModels = {"Bmw :","x1","x2","x3","x4","x5","x6"
//            ,"x7","m2","m3","m4","m5","m6","m8","i3","i8","220i","240"
//            ,"330i","320i","340i","420i","430i","440i","520i","530i","535i","640i","728i"
//            ,"740i","750i","z4","z3","m240i"};
//    private String[] kiaListModels = {"Kia : ","spotage","rio","optima","sorento","ceed","stonic"
//            ,"forte","niro","seltos"};
//    private String[] toyotaListModels = {"Toyota : ","Corolla","auris","camry","rav 4","yaris","supra"
//            ,"hilux","niro"};
   // private String[] hondaListModels = {"Honda : ","cr-v","civic","fr-v","pilot"};
   // private String[] fordListModels = {"Ford  : ","focus","galaxy","kuga","mustang"};
    //private String[] hyundaiListModels = {"Hyundai : ","accent","i20","i30","i40","i35","volester"
           // ,"tucson","santa fe"};
    private String[] SkodaListModels = {"Skoda : ","kodiaq","fabia","rapid","superb","yeti","kamiq"
            ,"octavia"};

    private String[] NameCarList = {"Select Name Car","Mercedes","volkswagen","Audi", "BMW","Kia","Toyota", "Honda", "Ford","Hyundai","Skoda"};

    private String[] carModelList = {"Select Car Model",
            "Mercedes : ","a160","a170","a180","a190","a200","a220"
            ,"a250","a35","a45 amg","c160","c180","c200","c230","c240","c250","c280","c300"
            ,"c300e","c400","c450","c450","cla180","cla200","cla220","cla250","cla35 amg","cls220","cls280"
            ,"cls300","cls320","cls350","cls400","cls450","cls500","cls63 amg"
            ,"e200","e220","e250","e300","e350","e400","e43 amg","e63 amg"
            ,"s250","s300","s350","s400","s450","s500","s63 amg","s650","slk"
            ,"glc","gle","eqb"
            ," ",
            "Bmw :","x1","x2","x3","x4","x5","x6"
            ,"x7","m2","m3","m4","m5","m6","m8","i3","i8","220i","240"
            ,"330i","320i","340i","420i","430i","440i","520i","530i","535i","640i","728i"
            ,"740i","750i","z4","z3","m240i",
            " ",
            " volkswagen : ","Golf","Gti","Caddy","jetta","scirocco","amarok"
            ,"tiguan","polo","passat",
            " ",
            " Audi : ","a1","a3","a7","a5","q3","q5"
            ,"q7","q8","r8","rs3","rs5","s1","s3","s7","s5","s3","s5","TT","TTrs",
            " ",
            "Kia : ","spotage","rio","optima","sorento","ceed","stonic"
            ,"forte","niro","seltos",
            " ",
            "Toyota : ","Corolla","auris","camry","rav 4","yaris","supra"
            ,"hilux","niro",
            " ",
            "Honda : ","cr-v","civic","fr-v","pilot",
            " ",
            "Ford  : ","focus","galaxy","kuga","mustang",
            " ",
            "Hyundai : ","accent","i20","i30","i40","i35","volester"
            ,"tucson","santa fe",
            " ",
            "Skoda : ","kodiaq","fabia","rapid","superb","yeti","kamiq"
            ,"octavia"
    };

    private String[] yearList = {"Select Year", "2023","2022","2021","2020","2019","2018","2017","2016","2015","2014",
            "2013","2012","2011","2010","2009","2008","2007","2006","2005","2004",
            "2003","2002","2001","2000"};

    private String[] colorsList={"select Color of car","Black","White","Blue",
            "Gray","Green","Red","Matte black","matte white","light blue "," Other..."};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        NameCarSpinner = view.findViewById(R.id.searchManufacturerSpinner);
        carModelSpinner = view.findViewById(R.id.searchCarModelSpinner);
        startYearSpinner = view.findViewById(R.id.searchStartYearSpinner);
       // CarColorSpinner=view.findViewById(R.id.searchCarColorSpinner1);
        endYearSpinner = view.findViewById(R.id.searchEndYearSpinner);
        searchButton = view.findViewById(R.id.btnSearch);
        clearButton = view.findViewById(R.id.btnClear);



        ArrayAdapter<String> NameCarAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, NameCarList);
        NameCarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NameCarSpinner.setAdapter(NameCarAdapter);

        ArrayAdapter<String> carModelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, carModelList);
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelSpinner.setAdapter(carModelAdapter);

//        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, colorsList);
//        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        CarColorSpinner.setAdapter(colorAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startYearSpinner.setAdapter(yearAdapter);
        endYearSpinner.setAdapter(yearAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelections();

            }
        });

        return view;
    }

    private void performSearch() {
        String selectNameCarDefaultStr = NameCarList[0];
        String selectCarDefaultStr = carModelList[0];
        //String selectColorDefaultStr=colorsList[0];
        String selectStartYearDefaultStr = yearList[0];
        String selectEndYearDefaultStr = yearList[0];

        String selectedNameCar = NameCarSpinner.getSelectedItem().toString();
        String selectedCarModel = carModelSpinner.getSelectedItem().toString();
       // String selectCarColor=CarColorSpinner.getSelectedItem().toString();
        String selectedStartYear = startYearSpinner.getSelectedItem().toString();
        String selectedEndYear = endYearSpinner.getSelectedItem().toString();

        boolean manFlag, modelFlag,colrFlag, yearStartFlag, yearEndFlag;
        manFlag = modelFlag =colrFlag= yearStartFlag = yearEndFlag = false;

        if (!selectedNameCar.equals(selectNameCarDefaultStr))
            manFlag = true;
        if (!selectedCarModel.equals(selectCarDefaultStr))
            modelFlag = true;
//        if (!selectCarColor.equals(selectColorDefaultStr))
//            colrFlag = true;
        if (!selectedStartYear.equals(selectStartYearDefaultStr))
            yearStartFlag = true;
        if (!selectedEndYear.equals(selectEndYearDefaultStr))
            yearEndFlag = true;

        filteredList.clear();
        for(Car car : list) {
            boolean manFound = false, modelFound = false,clFound=false, yearFound = false;

            if (manFlag) {
                if (car.getNameCar().toLowerCase().contains(selectedNameCar.toLowerCase()))
                    manFound = true;
            }

            if (modelFlag) {
                if (car.getCar_model().toLowerCase().contains(selectedCarModel.toLowerCase()))
                    modelFound = true;
            }
//            if (colrFlag) {
//                if (car.getColor().toLowerCase().contains(selectCarColor.toLowerCase()))
//                    modelFound = true;
//            }

            if (yearStartFlag && yearEndFlag) {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(selectedStartYear) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(selectedEndYear)) {
                    yearFound = true;
                }
            }
            else if (yearStartFlag && !yearEndFlag)
            {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(selectedStartYear) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(yearList[yearList.length - 1])) {
                    yearFound = true;
                }
            }
            else if (!yearStartFlag && yearEndFlag)
            {
                if (Integer.parseInt(car.getYear().toLowerCase()) >= Integer.parseInt(yearList[1]) &&
                        Integer.parseInt(car.getYear().toLowerCase()) <= Integer.parseInt(yearList[yearList.length - 1])) {
                    yearFound = true;
                }
            }

            if ((!manFlag) || (manFlag && manFound)) {
                if ((!modelFlag) || (modelFlag && modelFound)) {
                    //if ((!colrFlag) || (colrFlag && clFound)) {

                        if ((!yearStartFlag && !yearEndFlag) ||
                                ((yearStartFlag && !yearEndFlag) && yearFound) ||
                                ((!yearStartFlag && yearEndFlag) && yearFound) ||
                                ((yearStartFlag && yearEndFlag) && yearFound))
                        {
                            filteredList.add(car);
                        }
                    }
               // }
            }
        }

        myAdapter= new myAdapter1(getActivity(),filteredList);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new myAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("car", filteredList.get(position)); // or use Parcelable for better performance
                CarDetailsFragment cd = new CarDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });
    }

    private void clearSelections() {
        NameCarSpinner.setSelection(0);
        carModelSpinner.setSelection(0);
        //CarColorSpinner.setSelection(0);
        startYearSpinner.setSelection(0);
        endYearSpinner.setSelection(0);
        myAdapter= new myAdapter1(getActivity(),list);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.rvCarsSearchFragment);
        fbs = FirebaseServices.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list= new ArrayList<>(); filteredList = new ArrayList<>();
        myAdapter= new myAdapter1(getActivity(),list);
        recyclerView.setAdapter(myAdapter);
        getCarList();
        ((MainActivity)getActivity()).pushFragment(new SearchFragment());


    }

    private void getCarList() {
        fbs.getFire().collection("cars2").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Car car= dataSnapshot.toObject(Car.class);
                    list.add(car);
                }
                myAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}

/*
            if ((!manFlag) || (manFlag && manFound))
            {
                if ((!yearFlag) || (yearFlag && yearFound)) {
                    if ((!modelFlag) || (modelFlag && modelFound) )
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!modelFlag) || (modelFlag && modelFound))
            {
                if ((!manFlag) || (manFlag && manFound))
                {
                    if ((!yearFlag) || (yearFlag && yearFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!modelFlag) || (modelFlag && modelFound))
            {
                if ((!yearFlag) || (yearFlag && yearFound))
                {
                    if ((!manFlag) || (manFlag && manFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!yearFlag) || (yearFlag && yearFound))
            {
                if ((!modelFlag) || (modelFlag && modelFound))
                {
                    if ((!manFlag) || (manFlag && manFound))
                    {
                        filteredList.add(car);
                    }
                }
            }

            if ((!yearFlag) || (yearFlag && yearFound))
            {
                if ((!manFlag) || (manFlag && manFound))
                {
                    if ((!modelFlag) || (modelFlag && modelFound))
                    {
                        filteredList.add(car);
                    }
                }
            } */
