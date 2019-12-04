package ui;

import java.io.IOException;
import java.util.Scanner;

import i18n.Messages;
import utilidades.Utilidades;
import warnings.WarningDB;

public class UI extends Thread {





	public static void main(String[] args) throws IOException {
		Utilidades.writeFiles();

	}

	
	@Override
	public void run() {
		WarningDB wdb = null;
		try {
			wdb = new WarningDB();
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.printStackTrace());
		}
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();

		while( !input.equals("q") ) {

			System.out.println(Messages.WELCOME);
			System.out.println(Messages.OPT1);
			System.out.println(Messages.OPT2);
			System.out.println(Messages.OPT3);
			System.out.println(Messages.OPT4);
			System.out.println(Messages.OPT5);
			System.out.println(Messages.OPT6);
			System.out.println(Messages.OPT7);
			input = sc.nextLine();
			
			if(Integer.parseInt(input) == 1) { // Criar aviso
				Scanner sc1 = new Scanner(System.in);
				
				
			} else if (Integer.parseInt(input) == 2) {
				
			} else if (Integer.parseInt(input) == 3) {
				
			} else if (Integer.parseInt(input) == 4) {
				
			} else if (Integer.parseInt(input) == 5) {
				
			} else if (Integer.parseInt(input) == 6) {
				
			}

		}


	}




	
}



