package taskalert.location.sm.locationalert;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Help extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        TextView textview= (TextView) findViewById(R.id.tvHelp);
        textview.setMovementMethod(new ScrollingMovementMethod());
    }
}
