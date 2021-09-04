package ru.veryprosto.controller;

import ru.veryprosto.db.Init;
import ru.veryprosto.db.dao.EquipmentDAO;
import ru.veryprosto.db.dao.WellDAO;
import ru.veryprosto.db.entity.Equipment;
import ru.veryprosto.db.entity.Well;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private final EquipmentDAO equipmentDAO = new EquipmentDAO(Init.getConnectionSource(), Equipment.class);
    private final WellDAO wellDAO = new WellDAO(Init.getConnectionSource(), Well.class);

    public MainController() throws SQLException {
    }

    public Well getWellByName(String name) {
        Well well = null;
        try {
            well = wellDAO.queryBuilder().where().eq("name", name).queryForFirst();
        } catch (SQLException throwables) {
            System.out.println("Ошибка при запросе в БД");
        }
        return well;
    }

    public Well createWell(String name) {
        Well well = new Well(name);
        try {
            wellDAO.create(well);
        } catch (SQLException throwables) {
            System.out.println("Ошибка при запросе в БД");
        }
        return well;
    }

    public List<Equipment> equipmentPerWell(Well well) {
        try {
            return equipmentDAO.getEquipmentListByWell(well);
        } catch (SQLException throwables) {
            System.out.println("Ошибка при запросе в БД");
        }
        return null;
    }

    public List<Well> getWellList() {
        List<Well> wells = new ArrayList<>();
        try {
            wells = wellDAO.queryForAll();
        } catch (SQLException e) {
            System.out.println("Ошибка при запросе в БД");
        }
        return wells;
    }

    public List<Equipment> getEquipmentList() throws SQLException {
        return equipmentDAO.queryForAll();
    }

    public Equipment addEquipment(Well well) throws SQLException {
        List<Equipment> equipmentList = getEquipmentList();
        String equipmentName = "EQ" + String.format("%04d", (equipmentList.size() + 1));

        Equipment equipment = new Equipment(equipmentName, well);
        equipmentDAO.create(equipment);
        return equipment;
    }
}