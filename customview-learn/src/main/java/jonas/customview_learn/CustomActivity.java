package jonas.customview_learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import jonas.customview_learn.view.JProgView;
import jonas.customview_learn.view.LinearGradientView;
import jonas.customview_learn.view.PlayPause;

public class CustomActivity extends AppCompatActivity {

    private JProgView mJProgView;
    private LinearGradientView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        mJProgView = (JProgView)findViewById(R.id.jpv);
        mLv = (LinearGradientView)findViewById(R.id.lv);

        PlayPause playPause = (PlayPause)findViewById(R.id.pp);
        playPause.setPauseFirst().setOnStateChangeListener(new PlayPause.OnStateChangeListener() {
            @Override
            public void stateChanged(boolean play){
                System.out.println(play+"----------");
            }
        });
    }

    public void progress(View v){
        mJProgView.setProgress(mJProgView.getProg()+0.1f);
        mLv.setMove(mJProgView.getProg()+0.1f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
