package br.com.fiap;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class Main {


    public static Connection getConnection() {

        String url = null;
        String pass = null;
        String user = null;
        String driver = null;
        String debugar = null;


        Properties prop = new Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream("src/main/resources/application.properties");
            prop.load(file);

            url = prop.getProperty("datasource.url");
            user = prop.getProperty("datasource.username");
            pass = prop.getProperty("datasource.password");
            driver = prop.getProperty("datasource.driver-class-name");
            debugar = prop.getProperty("datasource.debugar");

            file.close();

          return  DriverManager.getConnection(url, user, pass);


        } catch (FileNotFoundException e) {
            System.err.println("Não encontramos o arquivo de configurção: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Não foi possivel ler o arquivo de configuração: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Não foi possivel realizar a conexão com o bando de dados: " + e.getMessage());
        }
        return null;

    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog( null, "Vamos salvar um cliente no banco de dados" );
        persist();
        JOptionPane.showMessageDialog( null, "Listando todos os clientes" );
        findAll();
        JOptionPane.showMessageDialog( null, "Consultando cliente por ID" );
        findById();
        JOptionPane.showMessageDialog( null, "Listando todos os clientes pelo contúdo do nome" );
        findByName();
    }

    private static void findByName() {
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

