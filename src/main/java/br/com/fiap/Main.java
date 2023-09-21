package br.com.fiap;

import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.repository.ClienteRepository;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {
    public static Connection getConnection() {
        Properties prop = new Properties();
        FileInputStream file = null;
        String url = null;
        String pass = null;
        String user = null;
        String driver = null;
        String debugar = null;
        try {

            file = new FileInputStream( "src/main/resources/application.properties" );
            prop.load( file );

            url = prop.getProperty( "datasource.url" );
            user = prop.getProperty( "datasource.username" );
            pass = prop.getProperty( "datasource.password" );
            driver = prop.getProperty( "datasource.driver-class-name" );
            debugar = prop.getProperty( "datasource.debugar" );
            file.close();

            return DriverManager.getConnection( url, user, pass );

        } catch (FileNotFoundException e) {
            System.err.println( "Não encontramos o arquivo de configuração: " + e.getMessage() );
        } catch (IOException e) {
            System.err.println( "Não foi possível ler o arquivo de configuração: " + e.getMessage() );
        } catch (SQLException e) {
            System.err.println( "Não foi possível realizar a conexão com o banco de dados: " + e.getMessage() );
        }
        return null;
    }

    public static void main(String[] args) {
        ClienteRepository repo = new ClienteRepository();
        List<Cliente> clientes = repo.findAll();
        clientes.forEach( System.out::println );
    }

}

