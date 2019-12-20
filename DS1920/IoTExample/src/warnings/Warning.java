package warnings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import utilidades.Utilidades;

public class Warning {

	private Timer t;
	private String warningText;
	private ArrayList<String> parameter;

	private Bezirk b;

	public Warning(String warningText) {
		this.warningText = warningText;
		t = new Timer();
		parameter = getWarningParameters();

		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("warning");
	}

	public void setTimerWarning() {

		if (!parameter.isEmpty()) {
			String dataInicioWarning = parameter.get(0).replace('-', '/') + " " + parameter.get(1);
			String dataFimWarning = parameter.get(2).replace('-', '/') + " " + parameter.get(3);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				Date dateInicioWarning = sdf.parse(dataInicioWarning);
				Date dateFimWarning = sdf.parse(dataFimWarning);
				if (Utilidades.validateBeginning(dateInicioWarning, dateFimWarning)) {
					TimerTask repeatedTask = new TimerTask() {
						public void run() {
							int HorasInicio = warningText.indexOf("de"); // index da hora inicial
							Date horaActual = new Date();
							SimpleDateFormat actual = new SimpleDateFormat("HH:mm");
							System.err
									.println(actual.format(horaActual) + " - " + warningText.substring(0, HorasInicio));

							try { // verifica se ja passou a data maxima para cancelar a repeticao do warning
								cancelTimer();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					};
					t = new Timer("Timer");
					long delay = 0;
					long period = Long.parseLong(parameter.get(parameter.size() - 1));
					t.scheduleAtFixedRate(repeatedTask, delay, period);
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		}

	}

	/**
	 * Cancela o timer do Warning, se a data atual for superior a data fim do
	 * warning
	 * 
	 * @throws ParseException
	 */
	public void cancelTimer() throws ParseException {

		String dateStr = parameter.get(2).replace('-', '/') + " " + parameter.get(3);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = sdf.parse(dateStr);

		if (Utilidades.validateDeadline(date)) {
			t.cancel();
		}
	}

	private ArrayList<String> getWarningParameters() {
		String[] warningParameters = warningText.split(" ");
		List<String> params = Arrays.asList(warningParameters);
		ArrayList<String> params2 = new ArrayList<String>();
		int dataInicio = params.indexOf("de") + 1;
		int HorasInicio = params.indexOf("de") + 2;

		int dataFim = params.indexOf("ate") + 1;

		int HorasFim = params.indexOf("ate") + 2;

		int periodicidade = params.size() - 2;

		params2.add(params.get(dataInicio));
		params2.add(params.get(HorasInicio));
		params2.add(params.get(dataFim));
		params2.add(params.get(HorasFim));
		params2.add(params.get(periodicidade));

		return params2;
	}

	public void sendWarningEvent() {
		WarningEvento we = new WarningEvento();
		b.sendEvent(we);
	}

	public void addWarningEvent(String mensagem, String dataInicio, String dataFim, String periodicidade) {
		AdicionarWarningEvento awe = new AdicionarWarningEvento(mensagem, dataInicio, dataFim, periodicidade);
		b.sendEvent(awe);
	}

	public void deleteWarningEvent(String mensagem, String dataInicio, String dataFim, String periodicidade) {
		DeleteWarningEvento dwe = new DeleteWarningEvento(mensagem, dataInicio, dataFim, periodicidade);
		b.sendEvent(dwe);
	}
}
