package hr.foi.air.osobnibankar.test;

import hr.foi.air.osobnibankar.PrihodiRashodiActivity;
import android.preference.Preference;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.robotium.solo.Solo;

public class PrihodiRashodiActivityUnitTest extends ActivityInstrumentationTestCase2<PrihodiRashodiActivity> {

	private Solo solo;
	public Preference pref;
	private EditText naziv;
	private EditText opis;
	private EditText iznos;
	public Button spremi;
	public Spinner spin;

	public PrihodiRashodiActivityUnitTest() {
		super(PrihodiRashodiActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());

	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void test() {
		naziv = (EditText) solo.getView(hr.foi.air.osobnibankar.R.id.etNaziv);
		opis = (EditText) solo.getView(hr.foi.air.osobnibankar.R.id.etOpis);
		iznos = (EditText) solo.getView(hr.foi.air.osobnibankar.R.id.etIznos);
		spin = (Spinner) solo
				.getView(hr.foi.air.osobnibankar.R.id.spinKategorija);
		spremi = (Button) solo.getView(hr.foi.air.osobnibankar.R.id.btnSpremi);
;
		solo.sleep(2000);
		solo.clickOnMenuItem("Novi unos");
		solo.sleep(2000);
		solo.enterText(naziv, "Placa");
		solo.sleep(2000);
		solo.enterText(opis, "Lipanj");
		solo.sleep(2000);
		solo.enterText(iznos, "1000");
		solo.sleep(2000);
		solo.clickOnButton(hr.foi.air.osobnibankar.R.id.btnSpremi);
		solo.sleep(2000);

	}

}
