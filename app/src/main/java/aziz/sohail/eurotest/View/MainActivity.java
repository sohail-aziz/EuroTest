package aziz.sohail.eurotest.View;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import aziz.sohail.eurotest.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,  MainFragment.newInstance())
                    .commit();
        }
    }


}
