package com.company.BLL;

import com.company.DAL.Dal;
import com.company.Entity.*;
import java.sql.SQLException;
import java.util.List;

public class Logic {

    public static boolean CreateConnection() {
        return Dal.CreateConnection()&
        Dal.CreateClientTable() &
        Dal.CreateProductTable()&
        Dal.CreateOrderTable()&
        Dal.CreateProductAndOrderTable();
    }
    public static boolean CloseConnection() throws SQLException {
            Dal.dropAllTables();
            Dal.CloseConnection();
            return true;
    }

    public static int AddNewClient(String name){
        return  Dal.AddNewClient(name);
    }
    public static boolean AddNewProduct(String title){
        return Dal.AddNewProduct(title);
    }
    public static int AddNewOrder(int idClient){
        return Dal.AddNewOrder(idClient);
    }
    public static boolean AddNewProductAndOrder(String titleProduct, int idOrder){
        return Dal.AddNewProductAndOrder(titleProduct, idOrder);
    }


    public static List<Client> PrintListOfClients() throws SQLException{
            return Dal.GetClientsList();
    }
    public static List<Product> PrintListOfProducts() throws SQLException{
            return Dal.GetProductsList();
    }
    public static List<Order> PrintListOrdersOfClient(int ClientID) throws SQLException{
            return Dal.GetOrderList(ClientID);
    }
    public static List<Product> PrintListProductsOfOrder(int ID) throws SQLException{
            return Dal.GetProductOfOrderList(ID);
    }
    public static List<Order> PrintListOrdersOfProduct(String title) throws SQLException{
            return Dal.GetOrderOfProductList(title);
    }


    public static boolean DeleteClientFromTable(int ClientId){
        return Dal.DeleteClientFromTable(ClientId);
    }
    public static boolean DeleteProductFromTable(String title){
        return Dal.DeleteProductFromTable(title);
    }
    public static boolean DeleteOrderFromTable(int ID){
        return  Dal.DeleteOrderFromTable(ID);
    }
    public static boolean DeleteProductFromOrder(String title, int id_order){
        return Dal.DeleteProductFromOrder(title, id_order);
    }

    public static boolean ChangeNameOfClient(String name, int id){
        return Dal.UpdateClientInTable(name,id);
    }
}
