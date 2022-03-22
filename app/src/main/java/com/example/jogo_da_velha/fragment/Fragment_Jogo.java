package com.example.jogo_da_velha.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jogo_da_velha.R;
import com.example.jogo_da_velha.databinding.FragmentJogoBinding;


public class Fragment_Jogo extends Fragment {
    private FragmentJogoBinding binding;
    //vetor para agrupar os botoes
    private Button[] botoes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJogoBinding .inflate(inflater, container , false);

        //instanciar o vetor
        botoes = new Button[9];
        //agrupar os botoes no vetor
        botoes[0] = binding.btn00;
        botoes[1] = binding.btn01;
        botoes[2] = binding.btn02;
        botoes[3] = binding.btn10;
        botoes[4] = binding.btn11;
        botoes[5] = binding.btn12;
        botoes[6] = binding.btn20;
        botoes[7] = binding.btn21;
        botoes[8] = binding.btn22;

        //associa os botoes
        for (Button btn : botoes){
            btn.setOnClickListener(ListenerBotoes);
        }

        return binding.getRoot();

    }

    private View.OnClickListener ListenerBotoes = btPress -> {
       //pega o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai as duas ultimos caracteres do nomeBotao
        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        //estrai a posicao em linha e coluna
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));


    };
}