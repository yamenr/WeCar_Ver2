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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.wecar.D_FireBase.Car;
import com.example.wecar.D_FireBase.CarItem;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.D_FireBase.User;
import com.example.wecar.MainActivity;
import com.example.wecar.R;
import com.example.wecar.utilities.CarListAdapter2;
import com.example.wecar.utilities.myAdapter1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarListMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarListMapFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView ivProfile;
    private FirebaseServices fbs;
    private CarListAdapter2 myAdapter;
    private SearchView srchView;
    private ArrayList<CarItem> cars, filteredList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarListMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarrListMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarListMapFragment newInstance(String param1, String param2) {
        CarListMapFragment fragment = new CarListMapFragment();
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
        recyclerView = getView().findViewById(R.id.rvCarlistMap);
        ivProfile = getView().findViewById(R.id.ivProfileCarListMapFragment);
        fbs = FirebaseServices.getInstance();
        fbs.setUserChangeFlag(false);
        /*if (fbs.getAuth().getCurrentUser() == null)
            fbs.setCurrentUser(fbs.getCurrentObjectUser()); */
        cars = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cars = getCars();
        myAdapter = new CarListAdapter2(getActivity(), cars);
        filteredList = new ArrayList<>();
        myAdapter.setOnItemClickListener(new CarListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                String selectedItem = cars.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("car", cars.get(position)); // or use Parcelable for better performance
                CarDetailsFragment cd = new CarDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit();
            }
        });
        srchView = getView().findViewById(R.id.srchViewCarListMapFragment);
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
        //((MainActivity)getActivity()).pushFragment(new CarsListFragment());
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProfileFragment();
            }
        });
    }

    private void applyFilter(String query) {
        // TODO: add onBackspace - old and new query
        if (query.trim().isEmpty())
        {
            myAdapter = new CarListAdapter2(getContext(), cars);
            recyclerView.setAdapter(myAdapter);
            //myAdapter.notifyDataSetChanged();
            return;
        }
        filteredList.clear();
        for(CarItem car : filteredList)
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
        myAdapter = new CarListAdapter2(getContext(), filteredList);
        recyclerView.setAdapter(myAdapter);

       /*
        myAdapter= new CarListAdapter2(getActivity(),filteredList);
        recyclerView.setAdapter(myAdapter); */

        myAdapter.setOnItemClickListener(new CarListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*
                // Handle item click here
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("car", filteredList.get(position)); // or use Parcelable for better performance
                CarDetailsFragment cd = new CarDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout,cd);
                ft.commit(); */
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
        return inflater.inflate(R.layout.fragment_carr_list_map, container, false);
    }

    public void gotoProfileFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new ProfileFragment());
        ft.commit();
    }

    public ArrayList<CarItem> getCars()
    {
        ArrayList<CarItem> cars = new ArrayList<>();

        try {
            cars.clear();
            fbs.getFire().collection("cars2")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    cars.add(document.toObject(CarItem.class));
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
    }


    @Override
    public void onPause() {
        super.onPause();
        User u = fbs.getCurrentUser();
        if (u != null && fbs.isUserChangeFlag())
            fbs.updateUser(u); // updating favorites

    }
}