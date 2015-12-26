package com.company.PL;

import com.company.BLL.Logic;
import com.company.Entity.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Pl {
    static Scanner in = new Scanner(System.in);
    public static Logic lg;

    enum Menu {
        Menu, AddClient, AddProduct, AddOrder,AddProductOfOrder,
        PrintClients, PrintProducts, PrintOrdersOfClient,
        PrintProductsOfOrder, PrintOrdersOfProduct, DeleteClient,
        DeleteProduct, DeleteOrder,DeleteProductFromOrder,
        ChangeClientName,Close
    }

    public static void main( String args[] ) throws SQLException {
            printMenu();
            lg = new Logic();
            Menu [] menu = Menu.values();
            boolean end = false;
            System.out.println("Show menu - 0");
            System.out.println("Press a key: ");
            if (lg.CreateConnection()) {
                while (true) {
                    int idx = Integer.parseInt(in.next());

                    switch (menu[idx].name()) {
                        case "Menu":
                            printMenu();
                            break;
                        case "AddClient":
                            AddNewClient();
                            break;
                        case "AddProduct":
                            AddNewProduct();
                            break;
                        case "AddOrder":
                            AddNewOrder();
                            break;
                        case "AddProductOfOrder":
                            AddNewProductOfOrder();
                            break;
                        case "PrintClients":
                            PrintListOfClients();
                            break;
                        case "PrintProducts":
                            PrintListOfProducts();
                            break;
                        case "PrintOrdersOfClient":
                            PrintListOrdersOfClient();
                            break;
                        case "PrintProductsOfOrder":
                            PrintListProductsOfOrder();
                            break;
                        case "PrintOrdersOfProduct":
                            PrintListOrdersOfProduct();
                            break;
                        case "DeleteClient":
                            DeleteClientFromTable();
                            break;
                        case "DeleteProduct":
                            DeleteProductFromTable();
                            break;
                        case "DeleteOrder":
                            DeleteOrderFromTable();
                            break;
                        case "DeleteProductFromOrder":
                            DeleteProductFromOrder();
                            break;
                        case "ChangeClientName":
                            ChangeNameOfClient();
                            break;
                        case "Close":
                            CloseConnection();
                            end = true;
                            break;
                        default:
                            System.out.println("Wrong key!");
                    }
                    if (end) break;
                }
            }
            else {
                System.out.println("Problems with open Connection");
            }
    }

    private static void printMenu(){
        System.out.println("Addition: Client - 1, Product - 2, Order - 3, Product to Order - 4");
        System.out.println("Get All: Clients - 5, Products - 6, Orders of Client - 7, Products of Order - 8, Orders of Product - 9");
        System.out.println("Delete: Client - 10, Product - 11, Order - 12, Product from Order - 13");
        System.out.println("Change a name of client - 14");
        System.out.println("Close - 15");
    }

    public static void AddNewClient(){
        Client client = new Client();
        try {
            System.out.println("Enter a name of client: ");
            client.setName(in.next());
            client.setID(lg.AddNewClient(client.getName()));
            if (client.getID() == -1) {
                System.out.println("Error! Client not add.");
            } else {
                System.out.println("Success. Client Id: " + client.getID());
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }

    }
    public static void AddNewProduct(){
        Product product = new Product();
        try {
            System.out.println("Enter a title of product: ");
            product.setTitle(in.next());
            if (!lg.AddNewProduct(product.getTitle())) {
                System.out.println("Error!");
            } else {
                System.out.println("Success.");
            }
        }
        catch (Exception e) {
            System.out.println("Error!");
        }
    }
    public static void AddNewOrder(){
        try {
            System.out.println("Enter ID of client: ");
            int ID = in.nextInt();
            int OrderId = lg.AddNewOrder(ID);
            if (OrderId == -1) {
                System.out.println("Error!");
            } else {
                System.out.println("Success. Order ID: " + OrderId);
            }
        }
        catch(Exception e){
            System.out.println("Error!");
        }
    }
    public static void AddNewProductOfOrder(){
        try {
            System.out.println("Enter ID of order: ");
            int ID = in.nextInt();
            System.out.println("Enter name of product: ");
            String title = in.next();
            if (!lg.AddNewProductAndOrder(title, ID)) {
                System.out.println("Error!");
            } else {
                System.out.println("Link was added.");
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }

    public static void PrintListOfClients(){
        try {
            System.out.println("List of clients:");
            List<Client> clients = lg.PrintListOfClients();
            for (Client client : clients){
                System.out.println(client.getName() + " " + client.getID());
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }
    public static void PrintListOfProducts(){
        try {
            System.out.println("List of products:");
            List<Product> products = lg.PrintListOfProducts();
            for (Product product : products){
                System.out.println(product.getTitle());
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }
    public static void PrintListOrdersOfClient() {
        try {
            System.out.println("Enter ID of client:");
            int id = in.nextInt();
            System.out.println("List of orders of client:");
            List<Order> orders = lg.PrintListOrdersOfClient(id);
            for (Order order: orders){
                System.out.println(order.getOrderID());
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }

    }
    public static void PrintListProductsOfOrder() {
        try {
            System.out.println("Enter ID of order:");
            int id = in.nextInt();
            System.out.println("List of products:");
            List<Product> products = lg.PrintListProductsOfOrder(id);
            for (Product product : products) {
                System.out.println(product.getTitle());

            }
        }
            catch(Exception e){
                System.out.println("Error!");
            }
        }
    public static void PrintListOrdersOfProduct(){
        Product product = new Product();
        try {
            System.out.println("Enter title of product:");
            product.setTitle(in.next());
            System.out.println("List of orders:");
            List<Order> orderOfProductList = lg.PrintListOrdersOfProduct(product.getTitle());
            for (Order order: orderOfProductList){
                System.out.println("Id_order:" + order.getOrderID());
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }

    public static void DeleteClientFromTable(){
        Client client = new Client();
        try {
            System.out.println("Enter a ID of client: ");
            client.setID(in.nextInt());
            if (!lg.DeleteClientFromTable(client.getID())) {
                System.out.println("Error!");
            } else {
                System.out.println("Client was deleted.");
            }
        }
        catch (Exception e){
            System.out.print("Error!");
        }
    }
    public static void DeleteProductFromTable(){
        Product product = new Product();
        try {
            System.out.println("Enter a title of product: ");
            product.setTitle(in.next());
            if (!lg.DeleteProductFromTable(product.getTitle())) {
                System.out.println("Error!");
            } else {
                System.out.println("Product was deleted.");
            }
        }
        catch (Exception e){
            System.out.print("Error!");
        }
    }
    public static void DeleteOrderFromTable(){
        try {
            System.out.println("Enter ID of order: ");
            int ID = in.nextInt();
            if (!lg.DeleteOrderFromTable(ID)) {
                System.out.println("Error!");
            } else {
                System.out.println("Order was deleted.");
            }
        }
        catch (Exception e){
            System.out.print("Error!");
        }
    }
    public static void DeleteProductFromOrder(){
        Product product = new Product();
        try {
            System.out.println("Enter ID of order: ");
            int ID = in.nextInt();
            System.out.println("Enter title of product: ");
            product.setTitle(in.next());
            if (!lg.DeleteProductFromOrder(product.getTitle(), ID)) {
                System.out.println("Error!");
            } else {
                System.out.println("Product was deleted from order.");
            }
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }

    public static void ChangeNameOfClient(){
        try {
            System.out.println("Enter ID of client:");
            int ID = in.nextInt();
            System.out.println("Enter new name:");
            String name = in.next();
            lg.ChangeNameOfClient(name, ID);
            System.out.println("Success. Name was changed");
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }

    public static void CloseConnection() {
        try{
            if(lg.CloseConnection()) {
                System.out.println("Success. Connection is close");
            }
            else {System.out.println("Failed. ");}
        }
        catch (Exception e) { System.out.println("Error!! Connection not closed");}
    }
}

