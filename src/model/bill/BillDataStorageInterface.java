package model.bill;

import model.DatabaseUpdate;

public interface BillDataStorageInterface extends DatabaseUpdate {
 
    BillModelInterface getBill(String billIDText);
    
}
