package taskalert.location.sm.locationalert;

import java.io.Serializable;
import android.location.Address;

public class TaskDetails implements Serializable {

    private static final long serialVersionUID = 5527258407135652423L;

    private int rowid;
    private String name;
    private String description;
    private boolean complete;
    private boolean tglonoff;
    private String address1;
    private String latitude1;
    private String longitude1;

    public TaskDetails(String taskName) {
        name = taskName;
    }

    public void setId(int rowid) {
        this.rowid = rowid;
    }

    public int getId() {
        return rowid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isTglonoff() {
        return tglonoff;
    }

    public void setTglonoff(boolean tglonoff) {
        this.tglonoff = tglonoff;
    }

    public void setAddress(String address1) {
        this.address1 = address1;
    }

    public String getAddress() {
        return address1;
    }

    public boolean hasAddress() {
        return null != address1;
    }

    public void setLatitude(String latitude) {
        this.latitude1 = latitude1;
    }

    public String getLatitude() {
        return latitude1;
    }

    public void setLongitude(String longitude) {
        this.longitude1 = longitude1;
    }

    public String getLongitude() {
        return longitude1;
    }

    public void toggleComplete() {
        complete = !complete;
    }
    public void toggletglOnOff() {
        tglonoff = !tglonoff;
    }

    public String toString() {
        return name;
    }

    public void setAddress(Address a) {
        if (null == a) {
            address1 = null;
            latitude1 = longitude1 = "0";
        } else {
            int maxAddressLine = a.getMaxAddressLineIndex();
            StringBuffer sb = new StringBuffer("");
            for (int i=0; i<maxAddressLine; i++) {
                sb.append(a.getAddressLine(i) + " ");
            }
            address1 = sb.toString();
            latitude1 = String.valueOf(a.getLatitude());
            longitude1 = String.valueOf(a.getLongitude());
        }
    }

    public boolean hasLocation() {
        return (latitude1 != "0" && longitude1 != "0");
    }

}

