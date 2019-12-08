package warnings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import utilidades.Utilidades;

public class Warning {

	private Timer t;
	private String warningText;
	private ArrayList<String> parameter;

	public Warning(String warningText) {
		this.warningText = warningText;
		t = new Timer();
		parameter = getWarningParameters();
	}


	public void setTimerWarning() {

		
		//TODO verificar se a data corrente é o início do warning
		
		
		
		if (!parameter.isEmpty()) {
			
			TimerTask repeatedTask = new TimerTask() {
				public void run() {
					System.out.println(warningText);
				}
			};
			t = new Timer("Timer");

			long delay  = 0;
			long period = Long.parseLong(parameter.get(parameter.size()-1));
			t.scheduleAtFixedRate(repeatedTask, delay, period);

		}


	}
	
	
	public void cancelTimer() {
		
		
		if (Utilidades.validateDeadline(other)) {
			t.cancel();
		}
		

	}
	

	private ArrayList<String> getWarningParameters(){


		String [] warningParameters = warningText.split(" ");

		for(String s: warningParameters) {

			if(Utilidades.isDate(s) || Utilidades.checkHour(s)|| Utilidades.validatePeriodicity(s))
			{
				parameter.add(s);
			}


		}

		return parameter;

	}




}
