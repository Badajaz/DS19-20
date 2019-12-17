package warnings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import i18n.I18N;
import i18n.Messages;
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

		if (!parameter.isEmpty()) {
			String data = parameter.get(0).replace('-', '/') + " " + parameter.get(1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {

				Date date = sdf.parse(data);
				if (Utilidades.validateBeginning(date)) {
					TimerTask repeatedTask = new TimerTask() {
						public void run() {
							int HorasInicio = warningText.indexOf("de");
							int ate = warningText.indexOf("ate");
							System.err.println(warningText.substring(HorasInicio + 14, ate - 1) + " - "
									+ warningText.substring(0, HorasInicio));
							//TODO: atualizar warning com hora correta.
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
			System.out.println(I18N.getString(Messages.CANCEL_WARNING));
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
}
