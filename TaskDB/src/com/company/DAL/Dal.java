package com.company.DAL;

import com.company.Entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dal {
    private static Connection connect;

    public static boolean CreateConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void dropAllTables() throws SQLException {
        Statement statement = null;

        if (CreateConnection()) {

            statement = connect.createStatement();
            statement.execute("DROP table Clients");
            statement.execute("DROP table Orders");
            statement.execute("DROP table ProductAndOrder");
            statement.execute("DROP table Products");
        }
        statement.close();
    }
    public static void CloseConnection() throws SQLException{
            connect.close();
    }

    public static boolean CreateProductTable() {
        Statement statement = null;
        try {
            if (CreateConnection()) {

                String text = "CREATE TABLE if not exists Products (Title text primary key);";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();

                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean CreateClientTable(){
        Statement statement = null;
        try {
            if (CreateConnection())
            {

                String text = "CREATE TABLE if not exists Clients (ID INTEGER PRIMARY KEY, ClientName text);";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean CreateOrderTable(){
        Statement statement = null;
        try {
            if (CreateConnection()) {

                String text = "CREATE TABLE if not exists Orders (ID INTEGER PRIMARY KEY, ClientID INTEGER NOT NULL," +
                        " FOREIGN KEY(ClientID) REFERENCES Clients(ID) ON DELETE CASCADE);";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean CreateProductAndOrderTable(){
         Statement statement = null;
        try {
            if (CreateConnection()) {

                String text = "CREATE TABLE if not exists ProductAndOrder (ID INTEGER PRIMARY KEY," +
                        "ProductTitle TEXT NOT NULL," +
                        "OrderID INTEGER NOT NULL," +
                        " FOREIGN KEY(ProductTitle) REFERENCES Products(Title) ON DELETE CASCADE," +
                        " FOREIGN KEY(OrderID) REFERENCES Charters(ID) ON DELETE CASCADE);";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean AddNewProduct(String title){
        Statement statement = null;
        try{
            if (CreateConnection()) {

                String text = "INSERT into Products (Title) VALUES ( + '" + title + "');";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public static int AddNewClient(String name){
        Statement statement = null;
        Statement statement1 = null;
        try{
            if (CreateConnection()) {

                String text = "INSERT into Clients (ClientName) VALUES  ('" + name + "');";
                statement = connect.createStatement();
                statement.executeUpdate(text);
                statement1 = connect.createStatement();
                ResultSet resultSet = statement1.executeQuery("SELECT ID FROM Clients WHERE ID=last_insert_rowid()");
                int id = resultSet.getInt("ID");

                statement.close();
                statement1.close();
                resultSet.close();
                connect.close();
                return id;
            }
            else return -1;
        } catch (SQLException e) {
            return -1;
        }
    }
    public static int AddNewOrder(int idClient) {
        Order order = new Order();
        Statement statement = null;
        Statement statement1 = null;
        try {
            if (CreateConnection()) {

                String text = "INSERT into Orders (ClientID) VALUES (" + idClient + ");";
                statement = connect.createStatement();
                statement.executeUpdate(text);
                statement1 = connect.createStatement();
                ResultSet resultSet = statement1.executeQuery("SELECT ID FROM Orders WHERE ID=last_insert_rowid();");
                order.setOrderID(resultSet.getInt("ID"));

                statement.close();
                statement1.close();
                resultSet.close();
                connect.close();
                return order.getOrderID();
            }
            else return -1;
        } catch (SQLException e) {
            return -1;
        }
    }
    public static boolean AddNewProductAndOrder(String titleProduct, int idOrder){
        Statement statement = null;
        try{
            if (CreateConnection()) {
                String text = "INSERT into ProductAndOrder (ProductTitle, OrderID) VALUES ( '" + titleProduct + "', " + idOrder + ");";
                statement = connect.createStatement();
                statement.executeUpdate(text);

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static List<Product> GetProductsList() throws SQLException {
            List<Product> list = new ArrayList<>();
            Statement statement = null;
            if (CreateConnection()) {

                statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Products;");

                while (resultSet.next()) {
                    Product product = new Product(resultSet.getString("Title"));
                    list.add(product);
                }

                statement.close();
                resultSet.close();
                connect.close();
            }
               return list;

    }
    public static List<Client> GetClientsList() throws SQLException {
        List<Client> list = new ArrayList<>();
        Statement statement = null;
            if (CreateConnection()){
                statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Clients;");
            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("ID"),resultSet.getString("ClientName"));
                list.add(client);
            }
            statement.close();
            resultSet.close();
            connect.close();
        }
            return list;
    }
    public static List<Order> GetOrderList(int clientID) throws SQLException {
        List<Order> list = new ArrayList<>();
        Statement statement = null;
            if (CreateConnection()) {


                statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("Select ID from Orders where ClientID=" + clientID + ";");
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setOrderID(resultSet.getInt("ID"));
                    list.add(order);
                }
                statement.close();
                resultSet.close();
                connect.close();

            }
            return list;
    }
    public static List<Product> GetProductOfOrderList(int id) throws SQLException {
        List<Product> list = new ArrayList<>();
        Statement statement = null;
            if (CreateConnection()) {

                statement = connect.createStatement();
                String text = "Select ProductTitle from ProductAndOrder where OrderID=" + id + ";";
                ResultSet resultSet = statement.executeQuery(text);
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getString("ProductTitle"));
                    list.add(product);
                }

                statement.close();
                resultSet.close();
                connect.close();

            }
            return list;
    }
    public static List<Order> GetOrderOfProductList(String title) throws SQLException {
        List<Order> list = new ArrayList<>();
        Statement statement = null;
        if (CreateConnection()) {

            statement = connect.createStatement();
            String text = "Select OrderID from ProductAndOrder where ProductTitle= '" + title + "';";
            ResultSet resultSet = statement.executeQuery(text);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderID(resultSet.getInt("OrderID"));
                list.add(order);
            }

            statement.close();
            resultSet.close();
            connect.close();

        }
            return list;
    }

    public static boolean DeleteClientFromTable(int clientID){
        Statement statement = null;;
        try {
            if (CreateConnection()) {

                statement = connect.createStatement();
                List<Order> orderList = GetOrderList(clientID);
                for (Order orderId : orderList){
                    if (!DeleteOrderFromTable(orderId.getOrderID())) return false;
                }
                statement.executeUpdate("DELETE from Clients where ID=" + clientID + ";");

                statement.close();
                connect.close();
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
    public static boolean DeleteProductFromTable(String title){
        Statement statement = null;
        try {
            if (CreateConnection()) {

               statement = connect.createStatement();
                statement.executeUpdate("DELETE from ProductAndOrder where ProductTitle='" + title + "';");
                statement.executeUpdate("DELETE from Products where Title='" + title + "';");

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
    public static boolean DeleteOrderFromTable(int id){
        Statement statement = null;
        try {
            if (CreateConnection()) {

                statement = connect.createStatement();
                statement.executeUpdate("DELETE from ProductAndOrder where OrderID=" + id + ";");
                statement.executeUpdate("DELETE from Orders where ID=" + id + ";");

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        }catch (SQLException e) {
            return false;
        }
    }
    public static boolean DeleteProductFromOrder(String title, int id_order){
        Statement statement = null;
        try {
            if (CreateConnection()) {

                statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery("Select ID from ProductAndOrder limit 1");
                System.out.println(resultSet.getInt("ID"));
                statement.executeUpdate("DELETE from ProductAndOrder where OrderID=" + id_order + " and ProductTitle='" + title + "' and ID=" + resultSet.getInt("ID") + ";");

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        }catch (SQLException e) {
            return false;
        }
    }
    public static boolean UpdateClientInTable(String name, int id){
        Statement statement = null;
        try {
            if (CreateConnection()) {

                statement = connect.createStatement();
                statement.execute("UPDATE Clients set ClientName = '" + name + "' where ID=" + id + ";");

                statement.close();
                connect.close();
                return true;
            }
            else return false;
        }
        catch (Exception e){
            return false;
        }
    }
}
