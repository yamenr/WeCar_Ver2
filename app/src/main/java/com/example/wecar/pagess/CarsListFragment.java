package com.example.wecar.pagess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.wecar.MainActivity;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.R;
import com.example.wecar.D_FireBase.Car;
import com.example.wecar.utilities.CarListAdapter2;
import com.example.wecar.utilities.myAdapter1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseServices fbs;
    private myAdapter1 myAdapter;
    private ArrayList<Car> list, filteredList;
    private FloatingActionButton btnAdd;
    private SearchView srchView;
    private Button favIcon;
    private Map<String, Car> carsMap;

        // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarsListFragment newInstance(String param1, String param2) {
        CarsListFragment fragment = new CarsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        recyclerView = getView().findViewById(R.id.rvCarlist);
        btnAdd = getView().findViewById(R.id.floatingButtonAddCarList);
        fbs = FirebaseServices.getInstance();
        //carsMap = new HashMap<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        filteredList = new ArrayList<>();
        //carsMap = getCarsMap();
        myAdapter = new myAdapter1(getActivity(), list);
        recyclerView.setAdapter(myAdapter);
        favIcon = getView().findViewById(R.id.ivFavoriteIcon);


        myAdapter.setOnItemClickListener(new myAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = list.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("car", list.get(position)); // or use Parcelable for better performance
                CarDetailsFragment cd = new CarDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });

        fbs.getFire().collection("cars").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        srchView = getView().findViewById(R.id.srchViewCarListFragment);
        srchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //applyFilter(newText);
                return false;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddCarFragment();
            }
        });
        btnAdd.setVisibility(View.INVISIBLE); // currently hidden

        ((MainActivity)getActivity()).pushFragment(new CarsListFragment());
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty())
        {
            myAdapter = new myAdapter1(getContext(), list);
            recyclerView.setAdapter(myAdapter);
            //myAdapter.notifyDataSetChanged();
            return;
        }
        filteredList.clear();
        for(Car car : list)
        {
            if (car.getCar_model().toLowerCase().contains(query.toLowerCase()) ||
                    car.getCar_num().toLowerCase().contains(query.toLowerCase()) ||
                    car.getColor().toLowerCase().contains(query.toLowerCase()) ||
                    car.getKilometre().toLowerCase().contains(query.toLowerCase()) ||
                    car.getEngine_capacity().toLowerCase().contains(query.toLowerCase()) ||
                    car.getHorse_power().toLowerCase().contains(query.toLowerCase()) ||
                    car.getManufacturer().toLowerCase().contains(query.toLowerCase()) ||
                    car.getNameCar().toLowerCase().contains(query.toLowerCase()) ||
                    car.getOwners().toLowerCase().contains(query.toLowerCase()) ||
                    car.getTest().toLowerCase().contains(query.toLowerCase()) ||
                    car.getYear().toLowerCase().contains(query.toLowerCase()) ||
                    car.getPrice().toLowerCase().contains(query.toLowerCase()) ||
                    car.getGear_shifting_model().toLowerCase().contains(query.toLowerCase()))
            {
                filteredList.add(car);
            }
        }
        if (filteredList.size() == 0)
        {
            showNoDataDialogue();
            return;
        }
        myAdapter = new myAdapter1(getContext(), filteredList);
        recyclerView.setAdapter(myAdapter);
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

    private void showNoDataDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("No Results");
        builder.setMessage("Try again!");
        builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars_list, container, false);
    }

    public void gotoAddCarFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddCarFragment());
        ft.commit();
    }

    /*
    public Map<String, Car> getCarsMap()
    {
        Map<String, Car> cars = new HashMap<>();

        try {
            cars.clear();
            fbs.getFire().collection("cars")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    cars.put(document.getId(), document.toObject(Car.class));
                                }

                                CarListAdapter2 adapter = new CarListAdapter2(getActivity(), cars);
                                recyclerView.setAdapter(adapter);
                                //addUserToCompany(companies, user);
                            } else {
                                //Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Log.e("getCompaniesMap(): ", e.getMessage());
        }

        return cars;
    } */
}