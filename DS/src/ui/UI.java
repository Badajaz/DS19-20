package ui;

import java.io.IOException;
import java.util.Scanner;

import i18n.Messages;
import utilidades.Utilidades;
import warnings.WarningDB;

public class UI extends Thread {

	public static void main(String[] args) throws IOException {
		UI ui = new UI();
		Utilidades.writeFiles();

		ui.start();

	}

	@Override
	public void run() {
		WarningDB wdb = null;
		
		Scanner sc = new Scanner(System.in);
		String input;

		do {
			System.out.println(Messages.WELCOME);
			System.out.println(Messages.OPT1);
			System.out.println(Messages.OPT2);
			System.out.println(Messages.OPT3);
			System.out.println(Messages.OPT4);
			System.out.println(Messages.OPT5);
			System.out.println(Messages.OPT6);
			input = sc.nextLine();
			
			if(Integer.parseInt(input) == 1) { // Criar aviso
				Scanner sc1 = new Scanner(System.in);
				String mensagem, dataInicio, dataFim, periodicidade;
				
				System.out.println(Messages.MENSAGEM_AVISO);
				mensagem = sc1.nextLine();
				System.out.println(Messages.DATA_INICIO_AVISO);
				dataInicio = sc1.nextLine();
				System.out.println(Messages.DATA_FIM_AVISO);
				dataFim = sc1.nextLine();
				System.out.println(Messages.PERIODICIDADE_AVISO);
				periodicidade = sc1.nextLine();
				
				String horaInicio = dataInicio.substring(11); // inicio da hora na string dataInicio
				String horaFim = dataFim.substring(11); // inicio da hora na string dataInicio
				
				try {
					Utilidades.writeWarning(mensagem, dataInicio, dataFim, periodicidade);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else if (Integer.parseInt(input) == 2) { // Apagar aviso
				Scanner sc2 = new Scanner (System.in);
				String mensagem;
				System.out.println(Messages.MENSAGEM_AVISO);
				mensagem = sc2.nextLine();
				
				try {
					Utilidades.deleteWarning(mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (Integer.parseInt(input) == 3) { // Criar novo contacto
				Scanner sc3 = new Scanner (System.in);
				String nomeContacto, numContacto;
				
				System.out.println(Messages.NOME_CONTACTO);
				nomeContacto = sc3.nextLine();
				System.out.println(Messages.TEL_CONTACTO);
				numContacto = sc3.nextLine();
				
				try {
					Utilidades.writeContacto(nomeContacto, numContacto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (Integer.parseInt(input) == 4) { // Criar padrão de inatividade
				Scanner sc4 = new Scanner(System.in);
			} else if (Integer.parseInt(input) == 5) { // Criar padrão de atividade
				Scanner sc5 = new Scanner(System.in);
			} else if (Integer.parseInt(input) == 6) { // Simular evento de inatividade
				Scanner sc6 = new Scanner(System.in);
			}

		} while( !sc.nextLine().equals("q"));
	}
}