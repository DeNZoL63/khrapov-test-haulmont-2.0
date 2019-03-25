package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadWriteDB {

    private static HashMap<String, Object> result = new HashMap<>();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //Работа с таблицей клиентов
    public static boolean create(Client client){

        final String SQL = String.format("INSERT INTO clients(FIRST_NAME, SECOND_NAME, PATRONYMIC, PHONE_NUMBER) VALUES('%s', '%s', '%s', '%s')",
                client.getFirstName(),
                client.getSecondName(),
                client.getPatronymic(),
                client.getPhoneNumber());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");
    }

    public static boolean update(Client client) {

        final String SQL = String.format("UPDATE clients SET FIRST_NAME='%s', SECOND_NAME='%s', PATRONYMIC='%s', PHONE_NUMBER='%s' WHERE id='%s'",
                client.getFirstName(),
                client.getSecondName(),
                client.getPatronymic(),
                client.getPhoneNumber(),
                client.getId());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");

    }

    public static Client readOneClient(Long id){

        Client client = new Client();
        final String SQL = String.format("SELECT * FROM Clients WHERE id='%s'", id);
        HashMap<String, Object> res = doQuery(SQL);

        if ((boolean) res.get("Complete")
                && res.get("Data") != null) {

            ResultSet rs = (ResultSet) res.get("Data");
            try{
                while (rs.next()) {
                    //обработка результата
                    fillAbstractFields(rs, client);
                    client.setPhoneNumber(rs.getString("phone_number"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return client;
    }

    //Работа с таблицей механиков
    public static boolean create(Mechanic mechanic){

        final String SQL = String.format("INSERT INTO mechanics(FIRST_NAME, SECOND_NAME, PATRONYMIC, RATE) VALUES('%s', '%s', '%s', '%s')",
                mechanic.getFirstName(),
                mechanic.getSecondName(),
                mechanic.getPatronymic(),
                mechanic.getRate());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");
    }

    public static boolean update(Mechanic mechanic) {

        final String SQL = String.format("UPDATE mechanics SET FIRST_NAME='%s', SECOND_NAME='%s', PATRONYMIC='%s', RATE='%s' WHERE id='%s'",
                mechanic.getFirstName(),
                mechanic.getSecondName(),
                mechanic.getPatronymic(),
                mechanic.getRate(),
                mechanic.getId());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");

    }

    public static Mechanic readOneMechanic(Long id){

        Mechanic mechanic = new Mechanic();
        final String SQL = String.format("SELECT * FROM Mechanics WHERE id='%s'", id);
        HashMap<String, Object> res = doQuery(SQL);

        if ((boolean) res.get("Complete")
                && res.get("Data") != null) {

            ResultSet rs = (ResultSet) res.get("Data");
            try{
                while (rs.next()) {
                    //обработка результата
                    fillAbstractFields(rs, mechanic);
                    mechanic.setRate(Double.parseDouble(rs.getString("rate")));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mechanic;
    }

    //работа с таблицей заказов
    public static boolean create(Order order){

        final String SQL = String.format("INSERT INTO orders(DETAIL, CLIENT_ID, MECHANIC_ID, CREATE_DATE, FINISH_DATE, PRICE, STATUS) " +
                        "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                order.getDetail(),
                order.getClient(),
                order.getMechanic(),
                dateFormat.format(order.getCreateDate()),
                dateFormat.format(order.getFinishDate()),
                order.getPrice(),
                order.getOrderStatus());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");
    }

    public static boolean update(Order order) {

        final String SQL = String.format("UPDATE orders SET " +
                        "DETAIL='%s', CLIENT_ID='%s', MECHANIC_ID='%s', CREATE_DATE='%s'" +
                        ", FINISH_DATE='%s', PRICE='%s', STATUS='%s' WHERE id='%s'",
                order.getDetail(),
                order.getClient(),
                order.getMechanic(),
                dateFormat.format(order.getCreateDate()),
                dateFormat.format(order.getFinishDate()),
                order.getPrice(),
                order.getOrderStatus(),
                order.getId());

        result = doQuery(SQL);
        return (boolean)result.get("Complete");
    }

    public static Order readOneOrder(Long id){

        Order order = new Order();
        final String SQL = String.format("SELECT * FROM Orders WHERE id='%s'", id);
        HashMap<String, Object> res = doQuery(SQL);

        if ((boolean) res.get("Complete")
                && res.get("Data") != null) {

            ResultSet rs = (ResultSet) res.get("Data");
            try{
                while (rs.next()) {
                    //обработка результата
                    order.setOrderStatus(OrderStatus_Constant.valueOf(rs.getString("status")));
                    order.setFinishDate(rs.getDate("finish_date"));
                    order.setDetail(rs.getString("detail"));
                    order.setClient(rs.getLong("client_id"));
                    order.setMechanic(rs.getLong("mechanic_id"));
                    order.setCreateDate(rs.getDate("create_date"));
                    order.setPrice(rs.getDouble("price"));
                    order.setId(rs.getLong("id"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return order;
    }

    //Общие методы
    public static <T> List<T> readAll(TableNames tableName) {

        List<T> localResult = new ArrayList<>();
        final String SQL = "SELECT * FROM " + tableName;
        HashMap<String, Object> res = doQuery(SQL);

        if ((boolean) res.get("Complete")
                && res.get("Data") != null) {

            ResultSet rs = (ResultSet) res.get("Data");
            try{
                while (rs.next()) {
                    //обработка результата
                    switch (tableName){
                        case Orders:{
                            Order order = new Order();
                            fillOrderFields(order, rs);

                            localResult.add((T) order);
                            break;
                        }
                        case Orders_With_FIO:{
                            OrderWithFIO order = new OrderWithFIO();
                            fillOrderFields(order, rs);
                            order.setClientFio(rs.getString("client_fio"));
                            order.setMechanicFio(rs.getString("mechanic_fio"));

                            localResult.add((T) order);
                            break;
                        }
                        case Clients:{
                            Client client = new Client();
                            fillAbstractFields(rs, client);
                            client.setPhoneNumber(rs.getString("phone_number"));
                            client.setViewForChoose();

                            localResult.add((T) client);
                            break;
                        }
                        case Mechanics_Statistic:{
                            MechanicsStatistic mechanic = new MechanicsStatistic();
                            fillAbstractFields(rs, mechanic);
                            mechanic.setRate(Double.parseDouble(rs.getString("rate")));
                            mechanic.setOrders_new(rs.getInt("orders_new"));
                            mechanic.setOrders_confirm(rs.getInt("orders_confirm"));
                            mechanic.setOrders_finish(rs.getInt("orders_finish"));
                            mechanic.setViewForChoose(rs.getString("MECHANIC_FIO"));

                            localResult.add((T) mechanic);
                            break;
                        }
                        case Mechanics:{
                            Mechanic mechanic = new Mechanic();
                            fillAbstractFields(rs, mechanic);
                            mechanic.generateViewForChoose();

                            localResult.add((T) mechanic);
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return localResult;
    }

    public static boolean delete(TableNames tableName, Long id){

        final String SQL = String.format("DELETE FROM "+ tableName +" WHERE id='%s'", id);
        result = doQuery(SQL);

        return (boolean)result.get("Complete");
    }

    private static HashMap<String, Object> doQuery(String SQLQuery){

        result.clear();

        boolean flag = false;
        ResultSet rs = null;

        try(Connection connection = InitDB.connectToDB();
            Statement statement = connection.createStatement()) {

            rs = statement.executeQuery(SQLQuery);
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.put("Complete", flag);
        result.put("Data", rs);
        return result;
    }

    private static void fillAbstractFields(ResultSet resultSet, Person_Abstract entity){

        try {
            entity.setId(resultSet.getLong("id"));
            entity.setFirstName(resultSet.getString("first_name"));
            entity.setSecondName(resultSet.getString("second_name"));
            entity.setPatronymic(resultSet.getString("patronymic"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void fillOrderFields(Order order, ResultSet rs){
        try{
            order.setId(rs.getLong("id"));
            order.setDetail(rs.getString("detail"));
            order.setPrice(Double.parseDouble(rs.getString("price")));
            order.setClient(rs.getLong("client_id"));
            order.setMechanic(rs.getLong("mechanic_id"));
            order.setCreateDate(rs.getDate("create_date"));
            order.setFinishDate(rs.getDate("finish_date"));
            order.setOrderStatus(OrderStatus_Constant.valueOf(rs.getString("status")));
            order.setId(rs.getLong("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
