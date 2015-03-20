package taskalert.location.sm.locationalert;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAlarm extends Activity implements View.OnClickListener{
    TextView tTitle, tDesc, tAddress, tLatt, tLong;
    EditText eTitle, eDesc, eAddress, eLatt, eLong;
    String sTitle, sDesc, sAddress, sLatt, sLongg;
    //Long latt, longg;
    Button bAdd, bClearDelete, bRepeat;
    String className = "ListAlarm";
    TasksSQLiteOpenHelper myDB;
    int taskID,complete,tglOnOff,clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm1);

        //open database
        myDB = new TasksSQLiteOpenHelper(this);
        myDB.open();

        setupViews();
        Bundle callingBundle = getIntent().getExtras();
        String callingActivity = callingBundle.getString("CLASS_NAME");
        // String callingActivity = getCallingActivity().getClassName();
        if (callingActivity.equals(className)){
              displayAlarmData();
              complete = callingBundle.getInt("COMPLETE",0);
              tglOnOff = callingBundle.getInt("TGLONOFF",1);
              clear = 0;
        } else
        {
               displayBlankData();
                clear = 1;
            complete = 0;//callingBundle.getInt("COMPLETE",0);
            tglOnOff = 1;//callingBundle.getInt("TGLONOFF",1);
        }
        bAdd.setOnClickListener(this);
        bClearDelete.setOnClickListener(this);
        bRepeat.setOnClickListener(this);
    }

    protected void setupViews(){
        //association of layout text views
        tTitle = (TextView) findViewById(R.id.tvTitle);
        tDesc = (TextView) findViewById(R.id.tvDesc);
        tAddress = (TextView) findViewById(R.id.tvAddress);
        tLatt = (TextView) findViewById(R.id.tvLatt);
        tLong = (TextView) findViewById(R.id.tvLongg);

        //association of layout edit text views
        eTitle = (EditText)findViewById(R.id.etTitle);
        eDesc = (EditText)findViewById(R.id.etDesc);
        eAddress = (EditText)findViewById(R.id.etAddress);
        eLatt = (EditText)findViewById(R.id.etLatt);
        eLong = (EditText)findViewById(R.id.etLongg);

        //association of layout buttons
        bAdd = (Button)findViewById(R.id.bAdd);
        bClearDelete = (Button)findViewById(R.id.bClearDelete);
        bRepeat = (Button)findViewById(R.id.bRepeat);
    }

    protected void displayAlarmData(){
        //extract alarm id passed through ListAlarm activity
        Bundle alarmData = getIntent().getExtras();
        taskID = alarmData.getInt("TASK_ID");
        //extract details from database related to alarm id
        Cursor cAlarmdetails = myDB.getRow(taskID);

        //populate alarm details from database on the layout
        if(cAlarmdetails.moveToFirst()){
            String title = cAlarmdetails.getString(cAlarmdetails.getColumnIndex("TASK_NAME"));
            String desc =  cAlarmdetails.getString(cAlarmdetails.getColumnIndex("TASK_DESCRIPTION"));
            String address = cAlarmdetails.getString(cAlarmdetails.getColumnIndex("TASK_ADDRESS"));
            String latt = cAlarmdetails.getString(cAlarmdetails.getColumnIndex("TASK_LATITUDE"));
            String longt = cAlarmdetails.getString(cAlarmdetails.getColumnIndex("TASK_LONGITUDE"));

            eTitle.setText(title);
            eDesc.setText(desc);
            eAddress.setText(address);
            eLatt.setText(String.valueOf(latt));
            eLong.setText(String.valueOf(longt));
            bClearDelete.setText("Delete");
            bAdd.setText("Edit");
            eTitle.setEnabled(false);
            eDesc.setEnabled(false);
            eAddress.setEnabled(false);
            eLatt.setEnabled(false);
            eLong.setEnabled(false);
            }
    }

    protected void displayBlankData(){
        eTitle.setText(" ");
        eDesc.setText(" ");
        eAddress.setText(" ");
        eLatt.setText(" ");
        eLong.setText(" ");
    }

    protected void getAlarmDetails(){

        sTitle = eTitle.getText().toString();
        sDesc=eDesc.getText().toString();
        sAddress=eAddress.getText().toString();
        sLatt=eLatt.getText().toString();
        sLongg = eLong.getText().toString();
      }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bAdd:
                String temp1 = bAdd.getText().toString();

                switch (temp1){
                    case "Add Task":
                        getAlarmDetails();
                        myDB.insertRow(sTitle,sDesc,complete,tglOnOff,sAddress,sLatt,sLongg);
                        break;
                    case "Edit":
                        bAdd.setText("Update");
                        eTitle.setEnabled(true);
                        eDesc.setEnabled(true);
                        eAddress.setEnabled(true);
                        eLatt.setEnabled(true);
                        eLong.setEnabled(true);
                        break;
                    case "Update":
                        getAlarmDetails();
                        myDB.updateRow(taskID,sTitle,sDesc,complete,tglOnOff,sAddress,sLatt,sLongg);
                        break;
                }
                break;

            case R.id.bClearDelete:
                String temp2 = bClearDelete.getText().toString();

                  if (clear == 1)
                        displayBlankData();
                  else
                     myDB.deleteRow(taskID);           //call delete on myDB to delete the task

                break;

            case R.id.bRepeat:
                Toast.makeText(this,"Repeat to be implemented",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }
}