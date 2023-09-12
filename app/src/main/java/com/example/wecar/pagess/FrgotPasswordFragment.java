package com.example.wecar.pagess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wecar.MainActivity;
import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrgotPasswordFragment extends Fragment {

    private FirebaseServices fbs;
    private EditText etEmail;
    private Button btnReset;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FrgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrgotPasswordFragment newInstance(String param1, String param2) {
        FrgotPasswordFragment fragment = new FrgotPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_frgot_password, container, false);
    }

    public void onStart() {
        super.onStart();
        // connecting components
        fbs=FirebaseServices.getInstance();
        etEmail=getView().findViewById(R.id.etForgotPassword);
        btnReset=getView().findViewById(R.id.btnResetPassword);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbs.getAuth().sendPasswordResetEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "check your Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "failed . check the Email address you entered !", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        ((MainActivity)getActivity()).pushFragment(new FrgotPasswordFragment());
    }
}