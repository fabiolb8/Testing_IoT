package entity;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "TestSuite")
@XmlAccessorType (XmlAccessType.FIELD)
public class TestSuite {

	@XmlElement(name = "TestCase")
	private List<TestCase> listaTestCase;
	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "descrizione")
	private String descrizione;
	private Date dataEsecuzione;
	private int numTestOk;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	
	public TestSuite() {
		
		listaTestCase = new ArrayList<TestCase>();
	}
	
	
	public void run() throws ConnectionException {
		
		//imposta data di esecuzione e avvio i test case 
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		sdf.format(timestamp);
		setDataEsecuzione(timestamp);
		for (int i=0; i<listaTestCase.size(); i++) {
			listaTestCase.get(i).run();
		}
		calcolaNumTestOk();
	}

	private void calcolaNumTestOk() {
		int count = 0;
		for (int i=0; i<listaTestCase.size(); i++) {
			if (listaTestCase.get(i).getEsito()==Esito.POSITIVO)
				count++;
		}
		setNumTestOk(count);
	}
	
	public List<TestCase> getListaTestCase() {
		return listaTestCase;
	}
	
	public void setListaTestCase(List<TestCase> listaTestCase) {
		this.listaTestCase = listaTestCase;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public int getNumTestOk() {
		return numTestOk;
	}

	public void setNumTestOk(int numTestOk) {
		this.numTestOk = numTestOk;
	}
}