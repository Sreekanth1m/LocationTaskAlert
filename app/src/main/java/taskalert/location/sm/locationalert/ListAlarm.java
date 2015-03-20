package taskalert.location.sm.locationalert;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAlarm extends Activity {

    TasksSQLiteOpenHelper myDB;
    ListView lvAlarmList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alarm);

        //open database
        myDB = new TasksSQLiteOpenHelper(this);
        myDB.open();

        populateListViewFromDb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }

    public void populateListViewFromDb() {
        Cursor cursor = myDB.getAlarmRows();

        //set up mapping between database fields and layout
        String[] fromDBFields = new String[]{TasksSQLiteOpenHelper.TASK_NAME, TasksSQLiteOpenHelper.TASK_COMPLETE, TasksSQLiteOpenHelper.TASK_TGLONOFF};
        int[] toViewFields = new int[]{R.id.tvAlarmtext, R.id.checkedTextView, R.id.tglAlarmButton,};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.list_alarm_row, cursor, fromDBFields, toViewFields, 0);
        lvAlarmList = (ListView) findViewById(R.id.lvxAlarmList);
        lvAlarmList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvAlarmList.setAdapter(myCursorAdapter);
       // RelativeLayout rLayout = (RelativeLayout) view;
    }
}
/*            //lvAlarmList.setOnItemClickListener(this);
            lvAlarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ListView lView = (ListView) parent;

                    SimpleAdapter adapter = (SimpleAdapter) lView.getAdapter();

                    HashMap<String, Object> hm = (HashMap) adapter.getItem(position);*/

                    //The clicked Item in the ListView */
                    //RelativeLayout rLayout = (RelativeLayout) view;

                    /** Getting the toggle button corresponding to the clicked item */
                    /*ToggleButton tgl = (ToggleButton) rLayout.getChildAt(0);
                    TextView tvAlarmText = (TextView) rLayout.getChildAt(1);
                    CheckBox checkBox = (CheckBox)rLayout.getChildAt(2);
                    tvAlarmText.setClickable(true);
                    tvAlarmText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent addAlarm = new Intent(getApplicationContext(), AddAlarm.class);
                            addAlarm.putExtra("CLASS_NAME","ListAlarm");
                            startActivity(addAlarm);
                            Toast.makeText(ListAlarm.this,"Toast Message",Toast.LENGTH_LONG).show();
                            }
                    });
                    String strStatus = "";
                    if (tgl.isChecked()) {
                        tgl.setChecked(false);
                        strStatus = "Off";
                        status[position] = false;
                    } else {
                        tgl.setChecked(true);
                        strStatus = "On";
                        status[position] = true;
                    }
                    Toast.makeText(ListAlarm.this, (String) hm.get("txt") + " : " + strStatus, Toast.LENGTH_SHORT).show();
                    if(checkBox.isChecked())
                    {
                    checkBox.setChecked(false);
                    status1[position]=false;
                    } else{
                        checkBox.setChecked(true);
                        status1[position]= true;
                    }
                }
                });*/
            // Each row in the list stores country name and its status

            /*List<HashMap<String,Object>> aList = new ArrayList<>();

            for(int i=0;i<countries.length-1;i++) {
            // Each row in the list stores country name and its status
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("stat", status[i]);
                hm.put("txt", countries[i]);
                aList.add(hm);
        }
        // Keys used in Hashmap
        String[] from = {"stat","txt"};

        // Ids of views in listview_layout
        int[] to = {  R.id.tglAlarmButton,R.id.tvAlarmtext};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter1 = new SimpleAdapter(getBaseContext(), aList, R.layout.list_alarm_row, from, to);

        lvAlarmList.setAdapter(adapter1);

   }*/

//}






