package inatividade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import i18n.I18N;
import i18n.Messages;
import utilidades.Utilidades;

public class Inatividade {

	private String duracao, periodo;
	private Timer t;
	private int minuteCounter;
	private Bezirk b;

	public Inatividade(String duracao, String periodo) {
		this.duracao = duracao;
		this.periodo = periodo;
		t = new Timer();
		minuteCounter = 0;
		BezirkMiddleware.initialize();
		b = BezirkMiddleware.registerZirk("inatividade");
	}

	public void setTimerInactivity() {

		Date horaActual = new Date();
		SimpleDateFormat actual = new SimpleDateFormat("HH:mm");
		if (Utilidades.CheckActivityPeriod(actual.format(horaActual), periodo)) {
			TimerTask repeatedTask = new TimerTask() {
				public void run() {

					if (minuteCounter == Integer.parseInt(duracao)) {
						System.out.println("enviar send activated inactivity");
						sendActivatedInactivity();
						minuteCounter = 0;
					}
					System.out.println("minute counter : " + minuteCounter);
					minuteCounter++;

					try {
						cancelTimerInactivity();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			};
			t = new Timer("Timer");
			long delay = 0;
			long period = Long.parseLong(duracao);
			t.scheduleAtFixedRate(repeatedTask, delay, period);
		}
	}

	public void cancelTimerInactivity() throws ParseException {
		Date horaActual = new Date();
		SimpleDateFormat actual = new SimpleDateFormat("HH:mm");
		if (!Utilidades.CheckActivityPeriod(actual.format(horaActual), periodo)) {
			t.cancel();
		}
	}

	public String getDuracao() {
		return duracao;
	}

	public int getMinuteCounter() {
		return minuteCounter;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void sendInactivityEvent() {
		InatividadeEvento ie = new InatividadeEvento(duracao, periodo);
		b.sendEvent(ie);
		System.out.println(I18N.getString(Messages.INACTIVIDADE_EVENT));
	}

	public void sendBeginingInactivity() {
		BeginningInativityEvento ie = new BeginningInativityEvento();
		b.sendEvent(ie);
		System.out.println(I18N.getString(Messages.BEGIN_INACTIVIDADE_EVENT));
	}

	public void sendActivatedInactivity() {
		ActivatedInativityEvento ie = new ActivatedInativityEvento();
		b.sendEvent(ie);
		System.out.println(I18N.getString(Messages.BEGIN_INACTIVIDADE_EVENT));
	}
}
