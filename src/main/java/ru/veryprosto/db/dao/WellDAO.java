package ru.veryprosto.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import ru.veryprosto.db.entity.Well;

import java.sql.SQLException;

public class WellDAO extends BaseDaoImpl<Well, Integer> {

    public WellDAO(ConnectionSource connectionSource, Class<Well> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
