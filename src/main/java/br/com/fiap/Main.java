package br.com.fiap;

import java.sql.*;

public class Main {


    public static Connection getConnection() {
        var url = "jdbc:oracle:thin:@localhost:1521:XE";
        var url_fiap = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "BENEZINHO";
        String password = "root";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection( url, user, password );
        } catch (SQLException e) {
            System.err.println( "Não foi possivel conectar com o banco de dados!" + e.getMessage() );
        }
        return connection;
    }

    public static void main(String[] args) {

      //  findAll();







    }

    private static void findAll() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT * FROM cliente" );

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong( "ID_CLIENTE" );
                    String nome = resultSet.getString( "NM_CLIENTE" );
                    System.out.println( "CLIENTE: " + id + " - " + nome );
                }
            }
            System.out.println( "Conexão realizada com sucesso!!!" );
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage() );
        }
    }
}

