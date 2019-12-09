package warnings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
	
	
	public void cancelTimer() throws ParseException {
		
		String dateStr = parameter.get(2)+" "+parameter.get(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = sdf.parse(dateStr);
		
		//if (Utilidades.validateDeadline(date)) {
			t.cancel();
		//}
		

	}
	

	private ArrayList<String> getWarningParameters(){


		String [] warningParameters = warningText.split(" ");
		List<String> params = Arrays.asList(warningParameters);
		ArrayList<String> params2 = new ArrayList<String>();
		int dataInicio = params.indexOf("de")+1;
		System.out.println("dataaaaa:"+dataInicio);
		int HorasInicio = params.indexOf("de")+2;
		System.out.println("horassss:"+HorasInicio);
		
		int dataFim = params.indexOf("ate")+1;
		System.out.println("dattta2:"+dataFim);
		
		int HorasFim = params.indexOf("ate")+2;
		System.out.println("dattta2:"+HorasFim);
		
		int periodicidade = params.size()-2;
		System.out.println("periodicidade:"+periodicidade);

		params2.add(params.get(dataInicio));
		params2.add(params.get(HorasInicio));
		params2.add(params.get(dataFim));
		params2.add(params.get(HorasFim));
		params2.add(params.get(periodicidade));

	

		return params2;

	}

	
}
