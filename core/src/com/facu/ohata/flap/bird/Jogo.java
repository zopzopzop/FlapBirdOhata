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
	private  Texture gameover;

	//movimentação

	private int movimentaX = 0;
	private  float variacao = 0;
	private  int gravidade = 0;
	private  int pontos = 0;
	private  int melhorponto = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private Random random;
	private int estadoJogo = 0;

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
	BitmapFont textoReiniciar;
	BitmapFont textoMelhorPontuaçao;
	private boolean passouCano = false;

	//cria meus componenstes iniciais
	@Override
	public void create () {
		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

		batch = new SpriteBatch();
		random = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanoshorizontal = larguraDispositivo;
		espacoEntreCanos = 350;

		//mostra minha pontuação
		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		textoPontuacao.getData().setScale(10);

		//mostra minha melhor pontuação
		textoMelhorPontuaçao = new BitmapFont();
		textoMelhorPontuaçao.setColor(com.badlogic.gdx.graphics.Color.GREEN);
		textoMelhorPontuaçao.getData().setScale(2);

		//mostra o reiniciar
		textoReiniciar = new BitmapFont();
		textoReiniciar.setColor(com.badlogic.gdx.graphics.Color.RED);
		textoReiniciar.getData().setScale(2);

		//inicializa minhas colisões
		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoCima = new Rectangle();
		retanguloCanoBaixo = new Rectangle();

	}

	private void inicializarTexturas() {

		//anima meu passaro
		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");

		//inicia o sprite do meu fundo
		fundo = new Texture("fundo.png");

		//inicia o sprite dos canos
		canoBaixo = new Texture("cano_baixo_maior.png");
		canoTopo = new Texture("cano_topo_maior.png");
		gameover = new Texture("game_over.png");

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
		//desenha minhas colisões

		circuloPassaro.set(50 + passaro[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaro[0].getHeight() / 2, passaro[0].getWidth() / 2);

		retanguloCanoBaixo.set(posicaoCanoshorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanosVertical, canoBaixo.getWidth(), canoBaixo.getHeight());
		retanguloCanoCima.set(posicaoCanoshorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanosVertical, canoTopo.getWidth(), canoTopo.getHeight());

		boolean colisaoCanoCima = Intersector.overlaps(circuloPassaro,retanguloCanoCima);
		boolean colisaoCanoBaixo = Intersector.overlaps(circuloPassaro,retanguloCanoBaixo);

		//Indentifica minha colição
		if (colisaoCanoBaixo || colisaoCanoCima)
		{
			Gdx.app.log("Log", "Bateu");
			estadoJogo = 2;
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
		//anima meu passaro
		variacao += Gdx.graphics.getDeltaTime() * 10;

		if (variacao > 3)
			variacao = 0;

	}

	private void verificarEstadoJogo() {

		boolean toqueTela = Gdx.input.justTouched();

		if (estadoJogo == 0)
		{
			//joga o passaro pra cima e muda meu estado

			if (Gdx.input.justTouched())
			{
				gravidade = -25;
				estadoJogo = 1;
			}
		} else if (estadoJogo == 1){

			//identifica o toque do meu jogador
			if (Gdx.input.justTouched()) {
				gravidade = -20;

			}
			//Realiza a movimentação dos canos horizontal
			posicaoCanoshorizontal -= Gdx.graphics.getDeltaTime() * 200;
			if(posicaoCanoshorizontal < - canoBaixo.getWidth()){
				posicaoCanoshorizontal = larguraDispositivo;
				posicaoCanoshorizontal = random.nextInt(400) - 200;
				passouCano = false;
			}

			//ativa a gravidade no meu passaro
			if (posicaoInicialVerticalPassaro > 0 || toqueTela)
				posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

			gravidade++;
		}else  if (estadoJogo == 2){


		}


	}

	private void desenharTexturas() {
		batch.begin();

		//posiciona meu fundo
		batch.draw(fundo, 0,0,larguraDispositivo,alturaDispositivo);

		//posiciona meus sprites
		batch.draw(passaro[(int)variacao], 50, posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoshorizontal, alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanosVertical);
		batch.draw(canoTopo, posicaoCanoshorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanosVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo /2, alturaDispositivo - 100);

		//TELA de gameover
		if (estadoJogo == 2)
		{
			//seta minha melhor pontuação
			if(melhorponto < pontos)
			{
				melhorponto = pontos;
			}

			batch.draw(gameover, larguraDispositivo / 2 - gameover.getWidth() / 2, alturaDispositivo / 2);
			textoReiniciar.draw(batch, "TOQUE NA TELA PARA REINICIAR!", larguraDispositivo / 2 - 250, alturaDispositivo / 2 - gameover.getWidth() / 2);
				textoMelhorPontuaçao.draw(batch, "SUA MELHOR PONTUAÇÃO É:" + String.valueOf(melhorponto) + " PONTOS", larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameover.getWidth() * 2);
		}

		batch.end();
	}

	@Override
	public void dispose () {

	}
}
