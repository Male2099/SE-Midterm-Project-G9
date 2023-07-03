package se.group9.gicCafe.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.constants.CONSTANT;
import se.group9.gicCafe.model.Order;
import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.Tables;
import se.group9.gicCafe.repository.TablesRepo;
import se.group9.gicCafe.service.TablesService;

@Service
public class TablesServiceImp implements TablesService{
    private TablesRepo tablesRepo;
    public TablesServiceImp(TablesRepo tableRepo){
        super();
        this.tablesRepo=tableRepo;
    }

    @Override
    public List<Tables> getAllTables() {
        return tablesRepo.findAll();
    }

    @Override
    public Tables getTableByID(int id) {
        return tablesRepo.findById(id).get();
    }

    @Override
    public Tables saveAndFlushTable(Tables table) {
        return tablesRepo.saveAndFlush(table);
    }

    @Override
    public Order getPendingOrderByTableID(int tid) {
        List<Order> orderList=tablesRepo.getReferenceById(tid).getOrder();
        for(Order order : orderList){
            if(order.getStatus().equals(CONSTANT.Order_Status_Pending)) return order;
        }

        return null;//this will heppen if the table is freeeeeeeeee so any orders belong to this table has already been finished
    }
    @Override
    public Tables saveTables(Tables tables) {
        return tablesRepo.save(tables);
    }

	@Override
	public void deleteAllTables() {
		tablesRepo.deleteAll();	
	}

}
