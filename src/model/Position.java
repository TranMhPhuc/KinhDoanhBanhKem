package Model;

import java.util.Objects;

public class Position {

    private String postCode;
    private String postName;
    private float salaryFactor = 1.0f;

    public Position() {

    }

    public Position(String postCode, String postName) {
        this.postCode = postCode;
        this.postName = postName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPostName() {
        return postName;
    }

    public float getSalaryFactor() {
        return salaryFactor;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setSalaryFactor(float salaryFactor) {
        this.salaryFactor = salaryFactor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.postCode);
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
        final Position other = (Position) obj;
        if (!Objects.equals(this.postCode, other.postCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position{" + "postCode=" + postCode + ", postName=" + postName + ", salaryFactor=" + salaryFactor + '}';
    }

}//end Position
