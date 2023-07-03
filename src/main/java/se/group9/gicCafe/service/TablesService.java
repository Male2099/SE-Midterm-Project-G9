package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.Order;
import se.group9.gicCafe.model.Tables;

public interface TablesService {
    Tables saveTables(Tables tables);

    void deleteAllTables();

    public List<Tables> getAllTables();

    public Tables getTableByID(int id);

    public Tables saveAndFlushTable(Tables table);

    /**
     * a table can only have one order that has status "pender" mean not finish or pending
     * so get that order... only one order
     * 
     * @param tid
     * @return :Order that has status "pending"
     */
    public Order getPendingOrderByTableID(int tid);

}