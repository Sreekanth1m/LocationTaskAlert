package taskalert.location.sm.locationalert;

import taskalert.location.sm.locationalert.TaskDetails;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TaskListItem extends RelativeLayout{

    private TaskDetails task;
    private ToggleButton toggleButton;
    private CheckedTextView checkBox;
    private TextView addressText;

    public TaskListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        toggleButton = (ToggleButton) findViewById(R.id.tglAlarmButton);
        addressText = (TextView)findViewById(R.id.tvAlarmtext);
        checkBox = (CheckedTextView)findViewById(R.id.checkedTextView);
    }

    public void setTask(TaskDetails task) {
        this.task = task;
        toggleButton.setChecked(task.isTglonoff());
        checkBox.setText(task.getName());
        checkBox.setChecked(task.isComplete());
        if (task.hasAddress()) {
            addressText.setText(task.getAddress());
            addressText.setVisibility(View.VISIBLE);
        } else {
            addressText.setVisibility(View.GONE);
        }
    }

    public TaskDetails getTask() {
        return task;
    }

}
