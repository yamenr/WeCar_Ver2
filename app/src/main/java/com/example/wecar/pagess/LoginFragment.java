package com.example.wecar.pagess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.MainActivity;
import com.example.wecar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private EditText etUsername,etPassword;
    private TextView tvSignupLink;
    private TextView tvForgotPasswardLink;
    private Button btnLogin;
    private Button asGuest;
    private FirebaseServices fbs;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        // connecting components
        fbs=FirebaseServices.getInstance();
        if (fbs.getAuth().getCurrentUser() != null)
            gotoCarList();
        asGuest=getView().findViewById(R.id.btnGuest);


        etUsername=getView().findViewById(R.id.etUsernameLogin);
        tvSignupLink=getView().findViewById(R.id.tvSignupLinkLogin);
        tvForgotPasswardLink=getView().findViewById(R.id.tvForgotPasswordLogin);
        etPassword=getView().findViewById(R.id.etPasswordLogin);
        btnLogin=getView().findViewById(R.id.btnLoginLogin);

        asGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCarList();
                setNavigationBarVisible();
            }
        });

        tvSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFragment();
            }
        });

        tvForgotPasswardLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFrgotPasswordFragment();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation

                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                if(username.trim().isEmpty()||password.trim().isEmpty()){
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }
//                String name=fbs.getFire().collection("users").getParent().getId().toString();

                //Signup procedure

                fbs.getAuth().signInWithEmailAndPassword(username,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            //Toast.makeText(getActivity(), "you have succesfully logged", Toast.LENGTH_SHORT).show();
                            //gotoAddCarFragment();
                            gotoCarList();
                            Toast.makeText(getActivity(), "Welcome ", Toast.LENGTH_SHORT).show();

                            setNavigationBarVisible();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "failed to login! check user or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }

    private void setNavigationBarVisible() {
        ((MainActivity)getActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }

    public void gotoCarList() {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CarsListFragment());
        ft.commit();
    }
    private void gotoAddCarFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new AddCarFragment());
        ft.commit();
    }
    private void gotoSignupFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new SignupFragment());
        ft.commit();

    }
    private void gotoFrgotPasswordFragment() {
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new FrgotPasswordFragment());
        ft.commit();

    }


    public void gotologin(View view) {
        gotoCarList();
        setNavigationBarVisible();
    }
}