package in.moneytransfer.ipay.home.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.manager.OptionsFragmentManager;
import in.moneytransfer.ipay.home.utils.Constants;

public class OnClickOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_options);

        Intent intent = getIntent();
        int num = intent.getIntExtra(Constants.optionsFragToAct,101);

        OptionsFragmentManager manager = new OptionsFragmentManager(this);
        manager.selectFragment(num);
    }
}
