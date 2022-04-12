package com.example.jogo_da_velha.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jogo_da_velha.R;
import com.example.jogo_da_velha.databinding.FragmentInicioBinding;


public class Fragment_Inicio extends Fragment {

    private FragmentInicioBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);

        binding.bntJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_Inicio.this)
                        .navigate(R.id.action_fragment_Inicio_to_fragment_Jogo);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        // para "retirar" a toolbar
        //pegar uma referencia do tipo AppCompatActivity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        //ocultar a actionBar
        minhaActivity.getSupportActionBar().hide();

        }

}
