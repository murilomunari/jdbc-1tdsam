package br.com.fiap;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

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
        var sql = "SELECT * FROM cliente where UPPER(NM_CLIENTE) like ?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            String nome = JOptionPane.showInputDialog( "Nome do cliente" );
            nome = Objects.nonNull( nome ) ? nome.toUpperCase() : "";
            preparedStatement.setString( 1, "%" + nome + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    System.out.println("CLiente: " + resultSet.getLong( "ID_CLIENTE" )
                            + "- " + resultSet.getString( "NM_CLIENTE" )
                    );
                }
            } else {
                System.out.println( "Cliente não encontrado com o nome = " + nome );
            }
            resultSet.close();  preparedStatement.close(); connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
    }

    private static void findById() {
        var sql = "SELECT * FROM cliente where ID_CLIENTE=?";
        Long id = Long.valueOf( JOptionPane.showInputDialog( "ID do Cliente" ) );
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    System.out.println(
                            "CLiente: " + resultSet.getLong( "ID_CLIENTE" )
                                    + "- " + resultSet.getString( "NM_CLIENTE" )
                    );
                }
            } else {
                System.out.println( "Cliente não encontrado com o id = " + id );
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println( "Não foi possível executar a consulta: \n" + e.getMessage() );
        }
    }

    private static void persist() {
        var sql = "INSERT INTO cliente (ID_CLIENTE, NM_CLIENTE) VALUES (?,?)";
        Long id = Long.valueOf( JOptionPane.showInputDialog( "ID" ) );
        String nome = JOptionPane.showInputDialog( "NOME" );
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            preparedStatement.setString( 2, nome );
            preparedStatement.execute();
            System.out.println( "Cliente salvo com sucesso!!" );
            preparedStatement.close();

            connection.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println( "Não foi possível executar o comando!\n" + e.getMessage() );
        }
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

