package com.facu.ohata.flap.bird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {

	private  SpriteBatch batch;
	private Texture[] passaro;
	private  Texture fundo;
	private  Texture canoTopo;
	private  Texture canoBaixo;

	//movimentação

	private int movimentaX = 0;
	private  float variacao = 0;
	private  float gravidade = 0;
	private float posicaoInicialVerticalPassaro = 0;

	//Tela
	private  float larguraDispositivo;
	private  float alturaDispositivo;
	private  float posicaoCanoshorizontal;
	private  float espacoEntreCanos;

	//cria meus componenstes iniciais
	@Override
	public void create () {
		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

		batch = new SpriteBatch();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanoshorizontal = larguraDispositivo;
		espacoEntreCanos = 150;

	}

	private void inicializarTexturas() {

		passaro = new Texture[3];
		passaro[1] = new Texture("passaro1.png");
		passaro[2] = new Texture("passaro2.png");
		passaro[3] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");

	}

	//desenha meus componenstes na tela
	@Override
	public void render () {

		verificarEstadoJogo();
		desenharTexturas();
		


	}

	private void verificarEstadoJogo() {
		//joga o passaro pra cima
		boolean toqueTela = Gdx.input.justTouched();
		if (Gdx.input.justTouched())
		{
			gravidade = -25;
		}
		if (posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		//anima meu passaro
		variacao += Gdx.graphics.getDeltaTime() * 10;

		if (variacao > 3)
			variacao = 0;


		gravidade++;

	}

	private void desenharTexturas() {
		batch.begin();
		batch.draw(fundo, 0,0,larguraDispositivo,alturaDispositivo);
		//posiciona meus sprites
		batch.draw(passaro[(int)variacao], movimentaX, posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoshorizontal - 100, alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos);
		batch.draw(canoTopo, posicaoCanoshorizontal -100, alturaDispositivo / 2 + espacoEntreCanos);
		batch.end();
	}

	@Override
	public void dispose () {

	}
}
