package se.group9.gicCafe.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import se.group9.gicCafe.constants.CONSTANT;
import se.group9.gicCafe.model.Drink;
import se.group9.gicCafe.model.Food;
import se.group9.gicCafe.model.Order;
import se.group9.gicCafe.model.OrderDetail;
import se.group9.gicCafe.model.Tables;
import se.group9.gicCafe.model.User;
import se.group9.gicCafe.service.DrinkCategoryService;
import se.group9.gicCafe.service.DrinkService;
import se.group9.gicCafe.service.FoodCategoryService;
import se.group9.gicCafe.service.FoodService;
import se.group9.gicCafe.service.OrderDetailService;
import se.group9.gicCafe.service.OrderService;
import se.group9.gicCafe.service.TablesService;
import se.group9.gicCafe.service.UserService;

@Controller
@RequestMapping("/tables")
public class CashiersController {
    @Autowired
    private DrinkService drinkService;
    @Autowired
    private TablesService tableService;
    @Autowired
    private DrinkCategoryService drinkCategoryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private FoodCategoryService foodCategoryService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;

    // table selection
    @GetMapping("")
    public String viewAllTables(Model model) {
        List<Tables> tables = tableService.getAllTables();
        model.addAttribute("tableList", tables);
        model.addAttribute("tableCount", tables.size());
        // model.addAttribute("null", tables)
        return "TableS";
    }

    @PostMapping("/change-table")
    public String changeTable(@RequestParam int id, @RequestParam int toid) {
        Order order = tableService.getPendingOrderByTableID(id);

        Tables fromTable = tableService.getTableByID(id);
        fromTable.setStatus(CONSTANT.Table_Status_Free);
        tableService.saveTables(fromTable);

        Tables toTable = tableService.getTableByID(toid);
        toTable.setStatus(CONSTANT.Table_Status_Busy);
        tableService.saveTables(toTable);

        order.setTables(toTable);
        orderService.saveOrder(order);

        return "redirect:/tables";
    }

    @PostMapping("/clear-table")
    public String clearTableOrder(@RequestParam int id) {

        Order order = tableService.getPendingOrderByTableID(id);
        order.setStatus(CONSTANT.Order_Status_Finish);
        orderService.saveOrder(order);

        Tables table = tableService.getTableByID(id);
        table.setStatus(CONSTANT.Table_Status_Free);
        tableService.saveTables(table);

        return "redirect:/tables";
    }

    @GetMapping("/table")
    public String getTableInfo(@RequestParam int tid, Model model) {
        model.addAttribute("tableInfo", tableService.getTableByID(tid));

        Order order = tableService.getPendingOrderByTableID(tid);
        if (order != null) {
            model.addAttribute("orderInfo", order.getOrderDetail());
        }

        return "fragments/TableInfo :: table-order-info";
    }

    // ex:/tables/table1/drink-food-selection-order-info
    @GetMapping("/{table}/drink-food-selection-order-info")
    public String getDrinkSelectionView(@PathVariable("table") String table, Model model) {

        model.addAttribute("categoryList", drinkCategoryService.getAllDrinkCategories());
        model.addAttribute("drink_FoodList", drinkService.getAllDrinks());

        int tid = Integer.valueOf(table.replaceAll("table", ""));
        model.addAttribute("tid", tid);

        Order order = tableService.getPendingOrderByTableID(tid);

        if (order == null) {
            model.addAttribute("NoData", true);
            return "drinkSelection_orderInfo";
        }

        model.addAttribute("order_id", order.getId());
        List<OrderDetail> orderDetails = order.getOrderDetail();
        if (orderDetails.size() == 0) {
            model.addAttribute("NoData", true);
        } else {
            model.addAttribute("orderDetails", orderDetails);
        }

        return "drinkSelection_orderInfo";
    }

    // -------get category - default is drink category-------------
    @GetMapping("/{table}/drink-food-selection-order-info/get-category")
    public String getCategory(@RequestParam String ctg_name, Model model) {
        if (ctg_name.toLowerCase().equals("drink")) {
            model.addAttribute("categoryList", drinkCategoryService.getAllDrinkCategories());
        } else {
            model.addAttribute("categoryList", foodCategoryService.getAllFoodCategories());
        }
        return "fragments/CategoryData :: category_data";
    }

    // ------- get data about foods or drinks---------------------------
    @GetMapping("/{table}/drink-food-selection-order-info/get-foods")
    public String GetFoodsData(String table,
            Model model,
            @RequestParam(value = "id") int ctg_id) {

        if (ctg_id == 0) {
            model.addAttribute("drink_FoodList", foodService.getAllFoods());
        } else
            model.addAttribute("drink_FoodList",
                    foodCategoryService.getFoodCategoryByID(ctg_id).getFood());
        return "fragments/Drink_FoodData :: drink_food";
    }

    @GetMapping("/{table}/drink-food-selection-order-info/get-drinks")
    public String GetdrinkData(String table, Model model,
            @RequestParam(value = "id") int ctg_id) {

        if (ctg_id == 0) {
            model.addAttribute("drink_FoodList", drinkService.getAllDrinks());
        } else
            model.addAttribute("drink_FoodList",
                    drinkCategoryService.getDrinkCategoryByID(ctg_id).getDrink());

        return "fragments/Drink_FoodData :: drink_food";
    }

    // ---------- delete order detail-------------------
    @GetMapping("/{table}/drink-food-selection-order-info/delete-order")
    public String deleteOrderDetail(Model model, @PathVariable("table") String table, @RequestParam int id) {

        orderDetailService.deleteOrderDetailByID(id);

        int tid = Integer.valueOf(table.replaceAll("table", ""));
        Order order = tableService.getPendingOrderByTableID(tid);
        if (order == null) {
            model.addAttribute("NoData", true);

            return "fragments/OrderDetail :: orderDetail_of_table";
        }

        List<OrderDetail> orderDetails = order.getOrderDetail();
        if (orderDetails.size() == 0) {
            model.addAttribute("NoData", true);
        } else {
            model.addAttribute("orderDetails", orderDetails);
        }

        return "fragments/OrderDetail :: orderDetail_of_table";
    }

    // ----------- selecting order(food/drink) screen---------------
    @GetMapping("/{table}/drink-food-selection-order-info/add-drink")
    public String getAddDrinkView(Model model, @PathVariable String table) {

        return "fragments/AddDrink_View :: AddDrink";
    }

    // submit selected drink or food
    // use for both food and drink, inerting to order detail Table
    @PostMapping("/{table}/drink-food-selection-order-info/add-{type}")
    public String saveOrder(Model model,
            @PathVariable("table") String table, @PathVariable String type, @RequestParam int id,
            @RequestParam(name = "size", required = false, defaultValue = "R") String size,
            @RequestParam(name = "cream", required = false, defaultValue = "false") boolean cream,
            @RequestParam(name = "sugar", required = false, defaultValue = "0") int sugar,
            @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity) {

        int tid = Integer.valueOf(table.replaceAll("table", ""));
        Order order = tableService.getPendingOrderByTableID(tid);

        
        // why? as couldn't get the frist order detail after adding below, so check
        // if new order=first time order, insert it directly ....
        boolean newLyCreatedOrder = false;
        if (order == null) {
            newLyCreatedOrder = true;
            // get cashier who logging in
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            order = new Order(0, new java.util.Date(), 0, 0, 0, CONSTANT.Order_Status_Pending,
                    user, tableService.getTableByID(tid), new ArrayList<OrderDetail>());
            orderService.saveAndFlushOrder(order);

            Tables Tablee = tableService.getTableByID(tid);
            Tablee.setStatus(CONSTANT.Table_Status_Busy);
            tableService.saveTables(Tablee);

            model.addAttribute("order_id", order.getId());

        }


        if (type.equals("food")) {
            Food food = foodService.getFoodById(id);
            OrderDetail orderDetail = new OrderDetail(0, 0, false, quantity,
                    food.getPrice() * quantity, size, order, null, food);

            orderDetailService.saveAndFlushOrderDetail(orderDetail);
            if (newLyCreatedOrder)
                order.getOrderDetail().add(orderDetail);

            model.addAttribute("orderDetails", order.getOrderDetail());

            return "fragments/OrderDetail :: orderDetail_of_table";
        }

        Drink drink = drinkService.getDrinkById(id);
        double subTotal = drinkService.getDrinkPriceBySize(drink, size) * quantity;

        OrderDetail orderDetail = new OrderDetail(0, sugar, cream, quantity, subTotal, size, order, drink, null);
        orderDetailService.saveAndFlushOrderDetail(orderDetail);

        if (newLyCreatedOrder)
            order.getOrderDetail().add(orderDetail);

        model.addAttribute("orderDetails", order.getOrderDetail());

        return "fragments/OrderDetail :: orderDetail_of_table";
    }

    // edit selelected drink
    // edit selelected drink
    @GetMapping("/{table}/drink-food-selection-order-info/edit-drink")
    public String getEditDrinkView(Model model, @PathVariable String table, @RequestParam int id) {

        model.addAttribute("orderDetail", orderDetailService.getOrderDetailByID(id));
        return "fragments/EditDrink_View :: EditDrink";
    }

    @PostMapping("/{table}/drink-food-selection-order-info/edit-drink")
    public String EditOrder(Model model,
            @PathVariable("table") String table, @RequestParam int id,
            @RequestParam(name = "size", required = false, defaultValue = "R") String size,
            @RequestParam(name = "cream", required = false, defaultValue = "false") boolean cream,
            @RequestParam(name = "sugar", required = false, defaultValue = "0") int sugar,
            @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity) {

        OrderDetail orderDetail = orderDetailService.getOrderDetailByID(id);
        // get drink price
        double subTotal = drinkService.getDrinkPriceBySize(orderDetail.getDrink(), size);
        subTotal = subTotal * quantity;

        orderDetail.setCream(cream);
        orderDetail.setSize(size);
        orderDetail.setSugar(sugar);
        orderDetail.setQuantity(quantity);
        orderDetail.setSubtotal(subTotal);

        orderDetailService.saveOrderDetail(orderDetail);

        model.addAttribute("orderDetails", orderDetail.getOrder().getOrderDetail());

        return "fragments/OrderDetail :: orderDetail_of_table";
    }

    // edit drink/food qantity
    @PostMapping("/{table}/drink-food-selection-order-info/edit-quantity")
    public String editQuantityDtinkFood(@RequestParam int id, int quantity, Model model) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailByID(id);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (orderDetail.getDrink() != null) {
            double subTotal = drinkService.getDrinkPriceBySize(orderDetail.getDrink(), orderDetail.getSize())
                    * quantity;

            subTotal = Double.parseDouble(decimalFormat.format(subTotal));

            orderDetail.setSubtotal(subTotal);
            orderDetail.setQuantity(quantity);
            orderDetailService.saveOrderDetail(orderDetail);

            model.addAttribute("orderDetails", orderDetail.getOrder().getOrderDetail());
            return "fragments/OrderDetail :: orderDetail_of_table";
        }
        orderDetail.setQuantity(quantity);
        double subTotal = orderDetail.getFood().getPrice() * quantity;
        subTotal = Double.parseDouble(decimalFormat.format(subTotal));

        orderDetail.setSubtotal(subTotal);
        orderDetailService.saveOrderDetail(orderDetail);
        model.addAttribute("orderDetails", orderDetail.getOrder().getOrderDetail());
        return "fragments/OrderDetail :: orderDetail_of_table";

    }

    // checkout~~~~
    // checkout~~~~
    @GetMapping("/table{id}/drink-food-selection-order-info/order-price")
    @ResponseBody
    public Double getTotalCost(@PathVariable int id) {
        Order order = tableService.getPendingOrderByTableID(id);
        if (order == null || order.getOrderDetail().size() == 0) {
            // no order for drink or food
            return -1.0;
        }

        order.setTotal(orderService.getTotalPrice(order));
        orderService.saveOrder(order);
        return order.getTotal();
    }

    @PostMapping("/table{tid}/checkout-order")
    public String checkoutOrder(@PathVariable int tid, @RequestParam double cash_receive) {

        Order order = tableService.getPendingOrderByTableID(tid);
        order.setStatus(CONSTANT.Order_Status_Finish);
        order.setCash_received(cash_receive);
        order.setChanged(cash_receive - order.getTotal());
        orderService.saveOrder(order);

        Tables tables = tableService.getTableByID(tid);
        tables.setStatus(CONSTANT.Table_Status_Free);
        tableService.saveTables(tables);

        return "redirect:/tables/table" + tid + "/drink-food-selection-order-info/order-receipt?id=" + order.getId();
    }

    // ------print receipt ------------
    // ------print receipt ------------
    @GetMapping("/{table}/drink-food-selection-order-info/order-receipt")
    public String getReceiptView(@RequestParam int id, Model model) {
        Order order = orderService.getOrderByID(id);
        model.addAttribute("order", order);
        model.addAttribute("today", new Date(new java.util.Date().getTime()));

        int toReils = ((int) orderService.getOrderByID(id).getTotal() * 4100 / 1000) * 1000;
        model.addAttribute("toReils", toReils);

        return "printReceipt";
    }

}
