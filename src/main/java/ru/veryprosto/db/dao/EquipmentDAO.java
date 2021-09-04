package ru.veryprosto.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import ru.veryprosto.db.entity.Equipment;
import ru.veryprosto.db.entity.Well;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentDAO extends BaseDaoImpl<Equipment, Integer> {

    public EquipmentDAO(ConnectionSource connectionSource, Class<Equipment> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    /**
     * Получить список оборудования определённой скважины
     *
     * @param well категория актива
     * @return {@link List<Equipment>}
     */
    public List<Equipment> getEquipmentListByWell(Well well) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("well_id", well.getId());
        return this.queryForFieldValues(map);
    }
}
