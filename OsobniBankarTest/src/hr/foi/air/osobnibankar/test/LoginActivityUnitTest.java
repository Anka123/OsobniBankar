package hr.foi.air.osobnibankar.test;
import hr.foi.air.osobnibankar.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

public class LoginActivityUnitTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	private Solo solo;
	public ImageButton login, registracija;
	public EditText password;

	public LoginActivityUnitTest() {
		super(LoginActivity.class);
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
				.getView(hr.foi.air.osobnibankar.R.id.etLozinka1);
		login = (ImageButton) solo
				.getView(hr.foi.air.osobnibankar.R.id.imgBtnPrijava);
		registracija = (ImageButton) solo
				.getView(hr.foi.air.osobnibankar.R.id.imgBtnRegistracija);
		solo.sleep(2000);
		solo.enterText(password, "proba");
		solo.sleep(2000);
		solo.clickOnView(login);
		solo.sleep(2000);
		solo.enterText(password, "");
		solo.clickOnView(registracija);
		solo.sleep(2000);

	}

}
