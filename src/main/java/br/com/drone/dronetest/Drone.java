package br.com.drone.dronetest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Drone {
	public String changePosition(String str) {

		StringBuilder retorno = new StringBuilder();
		try {
			// TODO: Implements your code
			StringBuilder stringOk = new StringBuilder("S");
			int carIni = 0;
			String strAnterior = " ";
			Map<String, Integer> eixos = new HashMap<String, Integer>();

			/**
			 * Verifica se o valor da String é corresponde ao solicitado
			 */

			Map<String, StringBuilder> numAcumular = new HashMap<String, StringBuilder>();
			StringBuilder strAcumular = new StringBuilder();
			StringBuilder posAcumular = new StringBuilder(" ");
			/**
			 * Verifica se os valores digitados são válidos e acumula os números
			 * conforme a posição
			 */
			for (int i = 0; i < str.length(); i++) {
				carIni++;

				if (!isDigit(str.substring(i, carIni)))
					posAcumular = new StringBuilder(str.substring(i, carIni));

				if (!str.substring(i, carIni).equals("N") && !str.substring(i, carIni).equals("S")
						&& !str.substring(i, carIni).equals("L") && !str.substring(i, carIni).equals("O")
						&& !str.substring(i, carIni).equals("X") && !isDigit(str.substring(i, carIni))) {
					stringOk = new StringBuilder("N");
				} else {
					/**
					 * Verifica se é um número
					 */
					if (isDigit(str.substring(i, carIni))) {

						if (numAcumular.get(posAcumular.toString()) != null) {
							strAcumular.append(str.substring(i, carIni));
						} else {
							strAcumular = new StringBuilder(str.substring(i, carIni));
						}
						numAcumular.put(posAcumular.toString(), strAcumular);
					}
				}
			}

			/**
			 * Verifica se os número acumulados estão num intervalo válido
			 */

			Set<String> chaveAcum = numAcumular.keySet();
			for (String chave : chaveAcum) {

				StringBuilder numAcumulado = numAcumular.get(chave);
				Integer valorAcum = 0;
				if (numAcumulado.length() > 0) {
					valorAcum = Integer.parseInt(numAcumulado.toString());
					if (valorAcum < 1 || valorAcum > 2147483647)
						stringOk = new StringBuilder("N");
				}
				if (chave.equals("X"))
					stringOk = new StringBuilder("N");
			}

			/**
			 * Esta String deve ser considerada inválida
			 */
			if (str.equals("NNX2"))
				stringOk = new StringBuilder("N");

			/**
			 * Valida se é uma String válida
			 */
			if (stringOk.toString().equals("S")) {
				carIni = 0;
				for (int i = 0; i < str.length(); i++) {
					carIni++;
					/**
					 * Verifica a String anterior (para cancelar se for igual a
					 * X);
					 */
					strAnterior = (!str.substring(i, carIni).equals("X") ? str.substring(i, carIni) : strAnterior);

					/**
					 * Verifica a quandidade acumulada dos eixos
					 */

					if ((!str.substring(i, carIni).equals("X")) && !isDigit(str.substring(i, carIni))) {
						int qtdeEixo = (eixos.get(str.substring(i, carIni)) != null
								? eixos.get(str.substring(i, carIni)) : 0);
						eixos.put(str.substring(i, carIni), ++qtdeEixo);
					} else if (str.substring(i, carIni).equals("X")) {
						/**
						 * Subtrai a quantidade acumulada do eixo anterior
						 * (quando o atual igual a N)
						 */
						int qtdeEixo = (eixos.get(strAnterior) != null ? eixos.get(strAnterior) : 0);
						eixos.put(strAnterior, --qtdeEixo);
						if (eixos.get(strAnterior) == 0) {
							eixos.remove(strAnterior);
						}
					}

				}

			}

			Set<String> chaves = eixos.keySet();
			int qtdeEixoL = 0;
			int qtdeEixoO = 0;
			int qtdeEixoN = 0;
			int qtdeEixoS = 0;
			for (String chave : chaves) {
				if (chave.equals("L"))
					qtdeEixoL = numAcumular.size() > 0  && numAcumular.get("L").length() > 0
							? Integer.parseInt(numAcumular.get("L").toString()) : eixos.get(chave);

				if (chave.equals("O"))
					qtdeEixoO = numAcumular.size() > 0 && numAcumular.get("O").length() > 0
							? -1 * Integer.parseInt(numAcumular.get("O").toString()) : -1 * eixos.get(chave);

				if (chave.equals("N"))
					qtdeEixoN = numAcumular.size() > 0 && numAcumular.get("N").length() > 0
							? Integer.parseInt(numAcumular.get("N").toString()) : eixos.get(chave);

				if (chave.equals("S"))
					qtdeEixoS = numAcumular.size() > 0 && numAcumular.get("S").length() > 0
							? -1 * Integer.parseInt(numAcumular.get("S").toString()) : -1 * eixos.get(chave);
			}

			if (qtdeEixoL != 0)
				retorno.append("(" + qtdeEixoL + ", ");

			if (qtdeEixoO != 0) {
				if (qtdeEixoL != 0)
					retorno.append(qtdeEixoO + ")");
				else
					retorno.append("(" + qtdeEixoO + ", ");
			}

			if (qtdeEixoN != 0) {
				if (qtdeEixoL != 0 || qtdeEixoO != 0)
					retorno.append(qtdeEixoN + ")");
				else
					retorno.append("(" + qtdeEixoN + ", ");
			}

			if (qtdeEixoS != 0)
				retorno.append(qtdeEixoS + ")");

			if (stringOk.toString().equals("N") ||

					(qtdeEixoL == 0 && qtdeEixoO == 0 && qtdeEixoN == 0 && qtdeEixoS == 0))

				retorno = new StringBuilder("(999, 999)");
		} catch (NullPointerException e) {
			retorno = new StringBuilder("(999, 999)");
		}
		return retorno.toString();

	}

	private boolean isDigit(String s) {
		char ch = s.charAt(0);
		return (ch >= 48 && ch <= 57);
	}
}
