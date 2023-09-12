package com.example.wecar.pagess;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wecar.D_FireBase.CarItem;
import com.example.wecar.MainActivity;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.R;
import com.example.wecar.D_FireBase.Car;
import com.example.wecar.utilities.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCarFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView img;
    private String imageStr;
    private EditText etnameCar,ethorse_power,etOwners,etColor,etCar_num,
            etManufacturer,etYear,etCar_model,etTest,etkilometre,
            etEngine_capacity,etGear_shifting_model,etPrice, etPhone;
    private Button btnAddCar;
    private FirebaseServices fbs;
    private Utils utils;
    Spinner colorSpinner,yearOfCarSpinner;
    //////////////////////try spinner////////////////////////////////////////////////////////
    //try new spinner//
    private String selectedColorSpinner;

    private Spinner ColorSpinner;
    private  ArrayAdapter<CharSequence>colorAdapter;

    String[] Colors={"select Color of car","black","white","black",
            "gray","green","red","matte black","matte white","light blue "," Other..."};

    String[] years={"select year of car","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014",
            "2013","2012","2011","2010","2009","2008","2007","2006","2005","2004",
            "2003","2002","2001","2000"," Other..."};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCarFragment newInstance(String param1, String param2) {
        AddCarFragment fragment = new AddCarFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_car, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
                                                   // ---->    פרטי הוספת רכב    <----
        //editText
        fbs=FirebaseServices.getInstance();
        utils = Utils.getInstance();
        etnameCar=getView().findViewById(R.id.etNameCarAddFragment);
        ethorse_power=getView().findViewById(R.id.etHorsePowerAddFragment);
        etOwners=getView().findViewById(R.id.etOwnersAddFragment);
        etPhone=getView().findViewById(R.id.etPhoneAddFragment);
        etCar_num=getView().findViewById(R.id.etCarNumberAddFragment);
        etManufacturer=getView().findViewById(R.id.etManufacturerAddFragment);
        //etYear=getView().findViewById(R.id.etYearAddFragment);
        etCar_model=getView().findViewById(R.id.etCarModelAddFragment);
        etTest=getView().findViewById(R.id.etTestAddFragment);
        etkilometre=getView().findViewById(R.id.etKilometreAddFragment);
        etEngine_capacity=getView().findViewById(R.id.etEngineCapacityAddFragment);
        etGear_shifting_model=getView().findViewById(R.id.etGearShiftAddFragment);
        etPrice=getView().findViewById(R.id.etPriceAddFragment);
        //button for add car
        btnAddCar=getView().findViewById(R.id.btnAddCarADDFragment);
        img = getView().findViewById(R.id.ivCarAddCarFragment);

        //spinner for the color of car
        colorSpinner=getView().findViewById(R.id.ColorSpinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(), R.layout.item_file,Colors);
        adapter.setDropDownViewResource(R.layout.item_file);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
             //   Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner for the year of car

        yearOfCarSpinner=getView().findViewById(R.id.yearOfCarSpinner);
        ArrayAdapter<String>adapter2=new ArrayAdapter<String>(getActivity(), R.layout.item_file,years);
        adapter.setDropDownViewResource(R.layout.item_file);
        yearOfCarSpinner.setAdapter(adapter2);

        yearOfCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adding to firestore  'car' collection

                addToFirestore();
            }
        });
//////////////////////try spinner////////////////////////////////////////////////////////

        colorAdapter= ArrayAdapter.createFromResource(getActivity(),R.array.array_colors,R.layout.spinner_layout);
        ////////////////////////////////try new spinner ///////////////////////////
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colorSpinner.setAdapter(colorAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ((MainActivity)getActivity()).pushFragment(new AddCarFragment());
    }

    private void addToFirestore() {

        String nameCar, horse_power, owners, phone,
                car_num, manufacturer, year, car_type, Car_model,
                test ,kilometre,Engine_capacity,Gear_shifting_model,price ;
        String color;
//get data from screen

        nameCar=etnameCar.getText().toString();
        horse_power=ethorse_power.getText().toString();
        owners=etOwners.getText().toString();
        color=colorSpinner.getSelectedItem().toString();
        phone = etPhone.getText().toString();
        car_num=etCar_num.getText().toString();
        manufacturer=etManufacturer.getText().toString();
        year=yearOfCarSpinner.getSelectedItem().toString();
        Car_model=etCar_model.getText().toString();
        test=etTest.getText().toString();
        kilometre=etkilometre.getText().toString();
        Engine_capacity=etEngine_capacity.getText().toString();
        Gear_shifting_model="DSG";
        price=etPrice.getText().toString();
        //מספר טלפון לא חייב לבדוק
//        if(phone==null){
//            phone="-";
//            return;
//        }
        if (nameCar.trim().isEmpty()                                        ||
                horse_power.trim().isEmpty()                                ||
                owners.trim().isEmpty()                                     ||
                color.trim().isEmpty()                                      ||
                car_num.trim().isEmpty()                                    ||
                manufacturer.trim().isEmpty()                               ||
                year.trim().isEmpty()                                       ||
                Car_model.trim().isEmpty()                                  ||
                test.trim().isEmpty()                                       ||
                kilometre.trim().isEmpty()                                  ||
                Engine_capacity.trim().isEmpty()                            ||
                Gear_shifting_model.trim().isEmpty()                        ||
                price.trim().isEmpty())

        {
            Toast.makeText(getActivity(), "sorry some data missing incorrect !", Toast.LENGTH_SHORT).show();
            return;
        }

        Car car;
        CarItem car2;
        if (fbs.getSelectedImageURL() == null)
        {
            car= new Car(nameCar, horse_power, owners, phone, color ,
                    car_num, manufacturer, year, Car_model,
                    test ,kilometre,Engine_capacity,Gear_shifting_model,price,"");
            car2 = new CarItem(UUID.randomUUID().toString(), nameCar, horse_power, owners, phone, color ,
                    car_num, manufacturer, year, Car_model,
                    test ,kilometre,Engine_capacity,Gear_shifting_model,price,"");
        }
        else {
            car = new Car(nameCar, horse_power, owners, phone, color,
                    car_num, manufacturer, year, Car_model,
                    test, kilometre, Engine_capacity, Gear_shifting_model, price, fbs.getSelectedImageURL().toString());
            car2 = new CarItem(UUID.randomUUID().toString(), nameCar, horse_power, owners, phone, color,
                    car_num, manufacturer, year, Car_model,
                    test, kilometre, Engine_capacity, Gear_shifting_model, price, fbs.getSelectedImageURL().toString());

        }

        fbs.getFire().collection("cars").add(car)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getActivity(), "ADD Car is Succesed ", Toast.LENGTH_SHORT).show();
                    Log.e("addToFirestore() - add to collection: ", "Successful!");
                    gotoCarList();
                }
            }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("addToFirestore() - add to collection: ", e.getMessage());
                    }
                });

        try {
            fbs.getFire().collection("cars2").add(car2)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Toast.makeText(getActivity(), "ADD Car is Succesed ", Toast.LENGTH_SHORT).show();
                            Log.e("addToFirestore() - add to collection: ", "Successful!");
                            //gotoCarList();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("addToFirestore() - add to collection: ", e.getMessage());
                        }
                    });
        }
        catch (Exception ex)
        {
            Log.e("AddCarFragment: addToFirestore()", ex.getMessage());
        }
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            utils.uploadImage(getActivity(), selectedImageUri);
        }
    }

    public void gotoCarList() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CarsListFragment());
        ft.commit();
    }

    public void toBigImg(View view) {
    }

    /*
    public void uploadImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            imageStr = "images/" + UUID.randomUUID() + ".jpg"; //+ selectedImageUri.getLastPathSegment();
            StorageReference imageRef = fbs.getStorage().getReference().child("images/" + selectedImageUri.getLastPathSegment());

            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //selectedImageUri = uri;
                            fbs.setSelectedImageURL(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please choose an image first", Toast.LENGTH_SHORT).show();
        }
    } */
}