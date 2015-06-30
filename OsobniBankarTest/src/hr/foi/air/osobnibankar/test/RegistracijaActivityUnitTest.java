package hr.foi.air.osobnibankar.test;

import hr.foi.air.osobnibankar.RegistracijaActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.robotium.solo.Solo;

public class RegistracijaActivityUnitTest extends
		ActivityInstrumentationTestCase2<RegistracijaActivity> {

	private Solo solo;
	public ImageButton registracija;
	public EditText password, passwordponovno, odgovor;
	public Spinner spinPitanje;
	public RadioGroup radiogrp;
	public RadioButton radioBtn;

	public RegistracijaActivityUnitTest() {
		super(RegistracijaActivity.class);
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
		password = (EditText) solo
				.getView(hr.foi.air.osobnibankar.R.id.etLozinka);
		passwordponovno = (EditText) solo
				.getView(hr.foi.air.osobnibankar.R.id.etPotvrda);
		spinPitanje = (Spinner) solo
				.getView(hr.foi.air.osobnibankar.R.id.spinPitanje);
		odgovor = (EditText) solo
				.getView(hr.foi.air.osobnibankar.R.id.etOdgovor);
		registracija = (ImageButton) solo
				.getView(hr.foi.air.osobnibankar.R.id.btnRegistracija);
		radiogrp = (RadioGroup) solo.getView(hr.foi.air.osobnibankar.R.id.radioGroup1);
		radioBtn = (RadioButton) solo.getView(hr.foi.air.osobnibankar.R.id.radioHR);
;

		solo.sleep(2000);
		solo.enterText(password, "proba");
		solo.sleep(2000);
		solo.enterText(passwordponovno, "proba");
		solo.sleep(2000);
		solo.clickOnView(spinPitanje);
		solo.sleep(2000);
		solo.clickOnMenuItem("Najdraža boja?");
		solo.sleep(2000);
		solo.enterText(odgovor, "plava");
		solo.sleep(2000);

	}

}
