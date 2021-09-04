package ru.veryprosto.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ru.veryprosto.db.entity.Equipment;
import ru.veryprosto.db.entity.Well;

import java.sql.SQLException;


public class Init {

    private static ConnectionSource connectionSource;

    static {
        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:test.db");
        } catch (SQLException throwables) {
            System.out.println("Ошибка при создании БД");
        }
    }

    public static void init() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Well.class);
            TableUtils.createTableIfNotExists(connectionSource, Equipment.class);
        } catch (SQLException throwables) {
            System.out.println("Ошибка при создании таблиц");
        }
    }

    public static ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
