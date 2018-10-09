package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Step")
@XmlAccessorType (XmlAccessType.FIELD)
public class Step {

	@XmlElement(name = "numero")
	private int numero;
	@XmlElement(name = "inputFumo")
	private int inputFumo;
	@XmlElement(name = "inputTemperatura_zona1")
	private int inputTemperatura_zona1;
	@XmlElement(name = "inputTemperatura_zona2")
	private int inputTemperatura_zona2;
	@XmlElement(name = "inputTemperatura_zona3")
	private int inputTemperatura_zona3;
	@XmlElement(name = "outputAllarmeAtteso")
	private int outputAllarmeAtteso;
	private int outputAllarmeRilevato;
	@XmlElement(name = "outputVentilazioneAtteso")
	private int outputVentilazioneAtteso;
	private int outputventilazioneRilevato;
	
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getInputFumo() {
		return inputFumo;
	}

	public void setInputFumo(int inputFumo) {
		this.inputFumo = inputFumo;
	}

	public int getInputTemperatura_zona1() {
		return inputTemperatura_zona1;
	}

	public void setInputTemperatura_zona1(int inputTemperatura_zona1) {
		this.inputTemperatura_zona1 = inputTemperatura_zona1;
	}

	public int getInputTemperatura_zona2() {
		return inputTemperatura_zona2;
	}

	public void setInputTemperatura_zona2(int inputTemperatura_zona2) {
		this.inputTemperatura_zona2 = inputTemperatura_zona2;
	}

	public int getInputTemperatura_zona3() {
		return inputTemperatura_zona3;
	}

	public void setInputTemperatura_zona3(int inputTemperatura_zona3) {
		this.inputTemperatura_zona3 = inputTemperatura_zona3;
	}

	public int getOutputAllarmeAtteso() {
		return outputAllarmeAtteso;
	}

	public void setOutputAllarmeAtteso(int outputAllarmeAtteso) {
		this.outputAllarmeAtteso = outputAllarmeAtteso;
	}

	public int getOutputAllarmeRilevato() {
		return outputAllarmeRilevato;
	}

	public void setOutputAllarmeRilevato(int outputAllarmeRilevato) {
		this.outputAllarmeRilevato = outputAllarmeRilevato;
	}

	public int getOutputVentilazioneAtteso() {
		return outputVentilazioneAtteso;
	}

	public void setOutputVentilazioneAtteso(int outputVentilazioneAtteso) {
		this.outputVentilazioneAtteso = outputVentilazioneAtteso;
	}

	public int getOutputventilazioneRilevato() {
		return outputventilazioneRilevato;
	}

	public void setOutputventilazioneRilevato(int outputventilazioneRilevato) {
		this.outputventilazioneRilevato = outputventilazioneRilevato;
	}


	public void inviaInput() throws Exception {
		
		SimulatoreContesto sim = SimulatoreContesto.getInstance();
		
		sim.setFumo(inputFumo);
		sim.setTemperature(inputTemperatura_zona1, inputTemperatura_zona2, inputTemperatura_zona3);
		sim.avviaSimulazione();
		
	}

	public void leggiOutput() throws Exception {

		SimulatoreContesto sim = SimulatoreContesto.getInstance();

		int allarme = sim.leggiAllarme();
		setOutputAllarmeRilevato(allarme);

		int ventilazione = sim.leggiVentilazione();		
		setOutputventilazioneRilevato(ventilazione);
		
	}

}