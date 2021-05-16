package com.facu.ohata.flap.bird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Color;
import java.util.Random;

import static java.awt.Color.*;


public class Jogo extends ApplicationAdapter {

	private  SpriteBatch batch;
	private Texture[] passaro;
	private  Texture fundo;
	private  Texture canoTopo;
	private  Texture canoBaixo;

	//movimentação

	private int movimentaX = 0;
	private  float variacao = 0;
	private  int gravidade = 0;
	private  int pontos = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private Random random;

	//Colisão
	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;

	//Tela
	private  float larguraDispositivo;
	private  float alturaDispositivo;
	private  float posicaoCanoshorizontal;
	private float posicaoCanosVertical;
	private  float espacoEntreCanos;

	BitmapFont textoPontuacao;
	private boolean passouCano = false;

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
		espacoEntreCanos = 350;

		//mostra minha pontuação
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		textoPontuacao.getData().setScale(10);

	}

	private void inicializarTexturas() {

		batch = new SpriteBatch();
		random = new Random();
		//anima meu passaro
		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");

	}

	//desenha meus componenstes na tela
	@Override
	public void render () {

		verificarEstadoJogo();
		validarPontos();
		desenharTexturas();
		detectarColisao();



	}

	private void detectarColisao() {
		//desenha minha colisão

		circuloPassaro.set(50 + passaro[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaro[0].getHeight() / 2, passaro[0].getWidth() / 2);
		retanguloCanoCima.set(posicaoCanoshorizontal, alturaDispositivo / 2 - canoTopo.getHeight() - espacoEntreCanos / 2 + posicaoCanosVertical, canoTopo.getWidth(), canoTopo.getHeight());
		retanguloCanoBaixo.set(posicaoCanoshorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanosVertical, canoBaixo.getWidth(), canoBaixo.getHeight());

		boolean colisaoCanoCima = Intersector.overlaps(circuloPassaro,retanguloCanoCima);
		boolean colisaoCanoBaixo = Intersector.overlaps(circuloPassaro,retanguloCanoBaixo);

		//Indentifica minha colição
		if (colisaoCanoBaixo || colisaoCanoCima)
		{
			Gdx.app.log("Log", "Bateu");
		}
	}

	private void validarPontos() {

		if(posicaoCanoshorizontal < 50 - passaro[0].getHeight()){
			if (!passouCano)
			{
				pontos++;
				passouCano = true;
			}

		}
	}

	private void verificarEstadoJogo() {

		//Realiza a movimentação dos canos horizontal
		posicaoCanoshorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if(posicaoCanoshorizontal < - canoBaixo.getWidth()){
			posicaoCanoshorizontal = larguraDispositivo;
			posicaoCanoshorizontal = random.nextInt(400) - 200;
			passouCano = false;
		}

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
		batch.draw(passaro[(int)variacao], 100, posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoshorizontal - 100, alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanosVertical);
		batch.draw(canoTopo, posicaoCanoshorizontal -100, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanosVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo /2, alturaDispositivo - 100);
		batch.end();
	}

	@Override
	public void dispose () {

	}
}
