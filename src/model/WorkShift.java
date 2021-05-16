package Model;

import java.time.LocalTime;
import java.util.Objects;

public class WorkShift {

    private String shiftCode;
    private String shiftName;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public WorkShift() {

    }

    public WorkShift(String shiftCode, String shiftName, LocalTime timeStart, LocalTime timeEnd) {
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.shiftCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkShift other = (WorkShift) obj;
        if (!Objects.equals(this.shiftCode, other.shiftCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WorkShift{" + "shiftCode=" + shiftCode + ", shiftName=" + shiftName + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + '}';
    }

}//end WorkShift
