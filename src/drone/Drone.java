package br.com.drone;

import java.util.ArrayList;
import java.util.List;

public class Drone {
	
	private List <String> direc = new ArrayList<>();
	private short result [] [] =  new short[1][2] ;

	public short [] [] changePosition (String coord) {
		
		int [] rosa = {0 , 0, 0, 0};
		String num = null;
		int numAnt = 0;
		int indexAnt = 0;
		int indAnt = -1;

		//*
		//* Carregando a tabela da rosa dos ventos
		//*

		direc.add("N");
		direc.add("L");
		direc.add("S");
		direc.add("O");
		direc.add("X");
			
		//*
		//* Verificando se o parametro veio nulo
		//*
		if (coord == null) {
			result[0][0] = 999;
			result[0][1] = 999;
			return result;
		} else {
		
			//*
			//* Verificando se o parametro veio branco
			//*

			if (coord.isBlank()) {
				
				result[0][0] = 999;
				result[0][1] = 999;
				return result;
				
			} else {

				//*
				//* Varrendo o parametro caracter a caracter.
				//*

				for (int i=0; i < coord.length(); i++) {
					
					//*
					//* Buscando o �ndice do qual ir� somar na dire��o
					//*

					int index = buscaIndex(coord.charAt(i));
					
					//*
					//* caracter n�o consta na tabela da rosa dos ventos
					//*
					
					if (index < 0) {
						
						//*
						//* Verifica se o caracter � de 0 � 9, caso n�o seja � um erro
						//*

						if (coord.charAt(i) < 48 || coord.charAt(i) > 57 || i == 0 ) {
							result[0][0] = 999;
							result[0][1] = 999;
							return result; 
						} else {
							if (indAnt < 0) {
							//*
							//* Caso o caracter esteja dentro de 0 a 9, verifica se a informa��o anterio � um "X", caso seja erro
							//*

								indAnt = buscaIndex(coord.charAt(i-1));
								if (indAnt == 4) {
									result[0][0] = 999;
									result[0][1] = 999;
									return result;						
								}
							}
							//*
							//* Concatena os valores numericos em uma string
							//*
							if (num == null) {
							    num = Character.toString(coord.charAt(i));
							} else {
								num = num + Character.toString(coord.charAt(i));
							}
						}
					}
					
					if (index <= 4 && index >= 0) {
						//*
						//* Adiciona ao indice correspondente os valores concatenados na string no indicador correspondente
						//* se for maior que 2147483647, erro
						//*
						
						if (indAnt >= 0) {
							if (Integer.parseInt(num) > 2147483647) {
								result[0][0] = 999;
								result[0][1] = 999;
								return result;								
							}
							rosa[indAnt] = rosa[indAnt] + Integer.parseInt(num) - 1;
							//*
							//* Salva as informa��es concatenadas caso seja necess�rio desfazer
							//*
							numAnt = Integer.parseInt(num);
							indexAnt = indAnt;
							indAnt = -1;
							num = null;
						}
						if (index < 4) {
							//*
							//* Se for uma adi��o simples e o valor j� estiver em 2147483647, n�o deixa adicionar e d� erro
							//*
							if (rosa[index] == 2147483647) {
								result[0][0] = 999;
								result[0][1] = 999;
								return result;								
							}
							rosa[index]++;
						} else {
							//*
							//* Se o "X" vier na primeira posi��o
							//*
							if (i == 0) {
								result[0][0] = 999;
								result[0][1] = 999;
								return result;
							} else {
								
								if (index >= 0) {
									//*
									//* Se o segundo "X" vier na 3 posi��o
									//*
								
									index = buscaIndex(coord.charAt(i - 1));
									if (index == 4) {
										if (i - 3 == 0) {
											result[0][0] = 999;
											result[0][1] = 999;
											return result;
										}
										
										index = buscaIndex(coord.charAt(i - 3));
									} 
									//*
									//* Desfazendo a �ltima a��o
									//*
									
									if (index >= 0) {
										rosa[index]--;
									} else {
										rosa[indexAnt] = rosa[indexAnt] - numAnt;
									}
								}
							}
							
						}
					} 
				}
				
				if (indAnt >= 0 && indAnt < 4) {
					if (Integer.parseInt(num) > 2147483647) {
						result[0][0] = 999;
						result[0][1] = 999;
						return result;								
					}
					rosa[indAnt] = rosa[indAnt] + Integer.parseInt(num) - 1;
					indAnt = -1;
					num = null;
				}
				//*
				//* Calculando as coordenadas
				//*
								
				int coord2 = rosa[0] - rosa[2];
				int coord1 = rosa[1] - rosa[3];
				
				result[0][0] = (short) coord1;
				result[0][1] = (short) coord2;
				return result;
			}
		}
	}
	
	public Integer buscaIndex (char p) {
		
		String pos = Character.toString(p);
		return direc.indexOf(pos);
	}
	

}
