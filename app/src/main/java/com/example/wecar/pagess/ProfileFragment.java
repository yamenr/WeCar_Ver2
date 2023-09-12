package com.example.wecar.pagess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView firstName,LastName,Gmail1,Gmail2,Phone;
    Button editDetails,SignOut;
    private FirebaseServices Fbs;

    

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   init();

    }
    private void init()
    {
        Fbs = FirebaseServices.getInstance();
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
        firstName=getView().findViewById(R.id.tvFirstName);
        LastName=getView().findViewById(R.id.tvLastName);
        Gmail1=getView().findViewById(R.id.tvGmail1);
        Gmail2=getView().findViewById(R.id.tvGmail2);
        Phone=getView().findViewById(R.id.tvPhone);

        editDetails=getView().findViewById(R.id.btnEditProfile);
        SignOut=getView().findViewById(R.id.btnSignOut_ProfileFrg);

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fbs.getAuth().signOut();
                signout();
                gotoLoginFragment();
            }
        });
    }

    private void signout() {
        Fbs.getAuth().signOut();
        gotoLoginFragment();
    }

    private void gotoLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}