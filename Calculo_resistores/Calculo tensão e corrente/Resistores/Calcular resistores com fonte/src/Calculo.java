import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Calculo {

	static ArrayList<Float> listaResistores = new ArrayList<Float>();
	static ArrayList<Float> listaTensao = new ArrayList<Float>();
	static ArrayList<Float> listaCorrente = new ArrayList<Float>();
	static ArrayList<Float> listaResistParalelo = new ArrayList<Float>();
	static ArrayList<Float> listaCalculoParaleloMisto = new ArrayList<Float>();

	
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		int acao;
		
		int tipoDeResistor;
		
		do {
			tipoDeResistor = Integer.parseInt(JOptionPane.showInputDialog("Qual o tipo de resistor:\n1 - Série\n2 - Paralelo\n3 - Misto"));
			
			switch(tipoDeResistor) {
			case 1:
				calcularSerie();
				break;
			case 2:
				calcularParalelo();
				break;
			case 3:
				calcularMisto();
				break;
			}
			
			
			acao = Integer.parseInt(JOptionPane.showInputDialog("Deseja fazer mais algum cálculo?\n1 - Sim\n2 - Não"));
		} while(acao != 2);
		entrada.close();
	}

	private static void calcularSerie() {
		int quantResist;
		float resistores, fonte;
		
		float resistenciaTotal = 0, corrente, tensao = 0;
		
		quantResist = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de resistores"));
		fonte = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da fonte"));
		
		//resistencia total
		for(int i = 1; i <= quantResist; i++) {
			resistores = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do Resistor " + i));
			listaResistores.add(resistores);
			resistenciaTotal = resistenciaTotal + resistores;
		}
		
		//corrente, corrente total e tensão
		corrente = fonte / resistenciaTotal;
		for(int i = 0; i < listaResistores.size(); i++) {
			listaCorrente.add(corrente);
			tensao = corrente * listaResistores.get(i);
			listaTensao.add(tensao);
		}
		for(int i = 0; i < listaResistores.size(); i++) {
			System.out.println("Corrente resistor " + (i + 1) + ": " + listaCorrente.get(i) );
			System.out.println("Tensão do resistor " + (i + 1) + ": " + listaTensao.get(i) + "\n");
		}
		System.out.println("Corrente total: " + corrente);
	}
	
	private static  void calcularParalelo() {

		int quantResist;
		float resistores, fonte;
		
		float correnteTotal = 0;
		
		quantResist = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de resistores"));
		fonte = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da fonte"));
		
		//tensão
		for(int i = 0; i < quantResist; i++) {
			resistores = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do Resistor " + (i + 1)));
			listaResistParalelo.add(resistores);
			listaTensao.add(fonte);
		}
		
		//corrente
		for(int i = 0; i < quantResist; i++) {
			listaCorrente.add((fonte / listaResistParalelo.get(i)));
		}

		//corrente total
		for(int i = 0; i < listaResistParalelo.size(); i++) {
			correnteTotal = correnteTotal + listaCorrente.get(i);
		}
		
		for(int i = 0; i < quantResist; i++) {
			System.out.println("Tensão: " + listaTensao.get(i));
			System.out.println("Corrente: " + listaCorrente.get(i) + "\n");
		}
		System.out.println("Corrente total: " + correnteTotal);
	}
	
	public static void calcularMisto() {
		int quantResist, paraleloConfirm, quantParalelo;
		float resistores, fonte, resistParalelo, calculoParalelo = 0;
		
		float resistenciaTotal = 0, corrente;//, tensao = 0;
		
		quantResist = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de resistores"));
		fonte = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da fonte"));
		
		
		//pedir ultimos resistores em série e fazer o paralelo, e repetir esse ciclo
		do {
			quantParalelo = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de resistores em paralelo do circuito"));
			
			for(int i = 0; i < quantParalelo; i++) {
				resistParalelo = Float.parseFloat(JOptionPane.showInputDialog("Digite os primeiros resistores em paralelo"));
				listaResistParalelo.add(resistParalelo);
			}
			for(int i = 0; i < listaResistParalelo.size(); i++) {
				calculoParalelo = calculoParalelo + (1 / listaResistParalelo.get(i));
				listaCalculoParaleloMisto.add(calculoParalelo);
			}
			listaResistores.add(calculoParalelo);
			
			paraleloConfirm = Integer.parseInt(JOptionPane.showInputDialog("Há mais algum resistor em paralelo?\n1 - Sim\n2 - Não"));
			
		} while(paraleloConfirm != 2);
		
		//outros resistores
		for(int i = 1; i <= quantResist - quantParalelo; i++) {
			resistores = Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do Resistor " + (i)));
			listaResistores.add(resistores);
		}
		
		for(int i = 0; i < listaResistores.size(); i++) {
			resistenciaTotal = resistenciaTotal + listaResistores.get(i);
		}
		//corrente, corrente total e tensão
		corrente = fonte / resistenciaTotal;
	
		//resultados
		for(int i = 0; i < listaResistores.size(); i++) {
			//System.out.println("Corrente resistor " + (i + 1) + ": " + listaCorrente.get(i) );
		//	System.out.println("Tensão do resistor " + (i + 1) + ": " + listaTensao.get(i) + "\n");
		}
		System.out.println("Corrente total: " + corrente);
	}
}
