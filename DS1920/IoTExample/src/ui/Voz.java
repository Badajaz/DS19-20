package ui;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Voz {

	private VoiceManager voiceM;
	private Voice voice;

	public Voz() {
		System.setProperty("mbrola.base", "mbrola");
		voiceM = VoiceManager.getInstance();

		voice = voiceM.getVoice("mbrola_us1");

		voice.allocate();
	}
	
	public void speak(String msg) {
		voice.speak(msg);
	}
	
	public static void main(String[] args) {
		Voz voz = new Voz();
		voz.speak("English");
	}

}
