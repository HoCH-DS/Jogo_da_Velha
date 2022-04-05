package com.example.jogo_da_velha.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jogo_da_velha.R;
import com.example.jogo_da_velha.databinding.FragmentJogoBinding;
import com.example.jogo_da_velha.util.PrefsUtil;

import java.util.Arrays;
import java.util.Random;


public class Fragment_Jogo extends Fragment {
    private FragmentJogoBinding binding;

    //vetor para agrupar os botoes
    private Button[] botoes;

    //variavel que representa o tabuleiro
    private String[][] tabuleiro;

    //variavel para os simbolos
    private String simbJog1, simbJog2, simbolo;

    //variavel Random para sortear o jogador
    private Random random;

    //variavel para contar o numero de jogadas
    private int numJogadas = 0;

    //variaveis para o placar
    private int placarJog1 = 0, placarJog2 = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //verifica qual botao foi clicado no menu
        switch (item.getItemId()){
            //caso tenha clicado no resetar
            case R.id.menu_resetar:
                placarJog1 = 0;
                placarJog2 = 0;
                resetar();
                atualizarPlacar();
                break;
            //caso tenha clicado no preference
            case R.id.menu_pref:
                NavHostFragment.findNavController(Fragment_Jogo.this).navigate(R.id.action_fragment_Jogo_to_pref_Fragment);
                break;
        }


        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // habilita o menu neste fragment
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        binding = FragmentJogoBinding .inflate(inflater, container , false);

        //instanciar o vetor de botoes
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
        //
        tabuleiro = new String[3][3];

        //preencer o tabuleiro com ""
        for(String[] vetor :tabuleiro){
            Arrays.fill(vetor, "");
        }

        //intancia o Random
        random = new Random();

        //define os simbolos dos jogadores
        simbJog1 = PrefsUtil.getsimboloJo1(getContext());
        simbJog2 = PrefsUtil.getsimboloJo2(getContext());

        //alterar os simbolo no jogador no placar
        binding.JoagadorX.setText(getResources().getString(R.string.jog1, simbJog1));
        binding.JogadorO.setText(getResources().getString(R.string.jog2, simbJog2));

        //sortea quem inicia o jogo
        sorteia();

        //atualiza a vez
        atualizaVez();

        //retorna a view do fragment
        return binding.getRoot();

    }

    private void sorteia(){
        if (random.nextBoolean()){
            simbolo = simbJog1;
        }else{
            simbolo = simbJog2;
        }
    }

    private void atualizaVez(){
        //verivica de quem é a vez e acende o placar do jogador
        if(simbolo.equals(simbJog1)){
            binding.JogadorO.setBackgroundColor(getResources().getColor(R.color.pink_700));
            binding.JoagadorX.setBackgroundColor(getResources().getColor(R.color.blue_500));
            binding.PlacarJog1.setBackgroundColor(getResources().getColor(R.color.pink_700));
            binding.PlacarJog2.setBackgroundColor(getResources().getColor(R.color.blue_500));

        }else{
            binding.JoagadorX.setBackgroundColor(getResources().getColor(R.color.pink_700));
            binding.JogadorO.setBackgroundColor(getResources().getColor(R.color.blue_500));
            binding.PlacarJog2.setBackgroundColor(getResources().getColor(R.color.pink_700));
            binding.PlacarJog1.setBackgroundColor(getResources().getColor(R.color.blue_500));
        }
    }

    private boolean venceu(){
        //verifica se venceu na linha
        for(int i = 0; i < 3; i++){
            if (tabuleiro[i][0].equals(simbolo) &&
                    tabuleiro[i][1].equals(simbolo) && tabuleiro[i][2].equals(simbolo)){
                return true;
            }
        }
        //verivica se venceu na coluna
        for(int i = 0; i < 3; i++){
            if (tabuleiro[0][i].equals(simbolo) &&
                    tabuleiro[1][i].equals(simbolo) && tabuleiro[2][i].equals(simbolo)){
                return true;
            }
        }
        //verifica se venceu na diagonal
        if (tabuleiro[0][0].equals(simbolo) &&
                tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) &&
                tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)){
            return true;
        }

        return false;

    }

    private void atualizarPlacar(){
        binding.PlacarJog1.setText(placarJog1+"");
        binding.PlacarJog2.setText(placarJog2+"");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private View.OnClickListener ListenerBotoes = btPress -> {
        //incrementa o numero de jogadas
        numJogadas++;

        //pega o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());

        //extrai as duas ultimos caracteres do nomeBotao
        String posicao = nomeBotao.substring(nomeBotao.length()-2);

        //estrai a posicao em linha e coluna
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        //marca no tabuleiro o simbolo que foi jogar
        tabuleiro[linha][coluna] = simbolo;

        //trocar o texto do botao que foi clicado
        Button botao = (Button) btPress;

        //troca o texto do botao que foi clicado
        botao.setText(simbolo);

        //desabilitar o botao
        botao.setClickable(false);

        //trocar backgrond do botao
        botao.setBackgroundColor(Color.rgb(224, 224, 224));

        //verifica se venceu
        if(numJogadas >= 5 && venceu()){
            Toast.makeText(getContext(),R.string.venceu, Toast.LENGTH_SHORT).show();

            if (simbolo.equals(simbJog1)){
                placarJog1++;
            }else {
                placarJog2++;
            }
            // atualiza o placar
            atualizarPlacar();
            //chama o metodo resetar
            resetar();

        }else if(numJogadas == 9){
            //verifica se deu velha
            Toast.makeText(getContext(),R.string.velha, Toast.LENGTH_SHORT).show();
            //chama o metodo resetar
            resetar();
        }else{
            //inverter a vez
            simbolo = simbolo.equals(simbJog1) ? simbJog2 :simbJog1;
            //chama o metodo
            atualizaVez();

        }

    };

    //metodo para resetar
    private void resetar(){

        for(String[] vetor :tabuleiro){
            Arrays.fill(vetor, "");
        }

        for (int i = 0; i < botoes.length; i++){
            botoes[i].setText("");
            //desabilitar o botao
            botoes[i].setClickable(true);
            //trocar backgrond do botao
            botoes[i].setBackgroundColor(getResources().getColor(R.color.pink_700));

        }
        numJogadas = 0;
        sorteia();
        atualizaVez();
    }

    public Button[] getBotoes() {
        return botoes;
    }
}