package ru.veryprosto;

import ru.veryprosto.controller.MainController;
import ru.veryprosto.db.Init;
import ru.veryprosto.db.entity.Equipment;
import ru.veryprosto.db.entity.Well;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Launcher {


    public static void main(String[] args) throws IOException, SQLException {
        Init.init();
        MainController controller = new MainController();
        String userСhoiсe = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Выберите действие: \n" +
                    "1 - Создание N кол-ва оборудования на скважине.\n" +
                    "2 - Вывод общей информации об оборудовании на скважинах.\n" +
                    "3 - Экспорт всех данных в xml файл.\n" +
                    "4 - Выход из программы.\n" +
                    "введите число от 1 до 4");

            userСhoiсe = reader.readLine();

            switch (userСhoiсe) {
                case ("1"):
                    System.out.println("Вы выбрали - Создание N кол-ва оборудования на скважине.");

                    System.out.println("Введите имя скважины");
                    String wellName = reader.readLine();
                    Well well = controller.getWellByName(wellName);
                    if (well == null) {
                        well = controller.createWell(wellName);
                    }

                    System.out.println("Введите количество оборудования - положительное целое число");
                    int numberOfEquipment;

                    try {
                        numberOfEquipment = Integer.parseInt(reader.readLine());
                        if (numberOfEquipment < 1) {
                            throw new NumberFormatException();
                        } else {
                            for (int i = 1; i <= numberOfEquipment; i++) {
                                controller.addEquipment(well);
                            }
                            System.out.println(numberOfEquipment + " единиц оборудования на скважине под названием \"" + wellName + "\" добавлено в базу данных");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Неправильный ввод, необходимо ввести положительное целое число.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("2"):
                    System.out.println("Вывод общей информации об оборудовании на скважинах.");
                    System.out.println("Введите имена скважин, в качестве разделителей введите пробел или запятую");
                    String stringWells = reader.readLine();
                    stringWells = stringWells.replace(", ", ",").replace(" ,", ",").replace(' ', ',');
                    List<String> wellNameList = Arrays.asList(stringWells.split(","));
                    wellNameList.forEach(name -> {
                        Well well1 = controller.getWellByName(name);
                        if (well1 != null) {
                            int numberOfEquipmentPerWell = controller.equipmentPerWell(well1).size();
                            System.out.format("%32s - %10d\n", well1.getName(), numberOfEquipmentPerWell);
                        }
                    });
                    break;
                case ("3"):
                    Map<Well, List<Equipment>> wellEquipmentMap = new HashMap<>();
                    List<Well> wellList = controller.getWellList();
                    wellList.forEach(w -> {
                        List<Equipment> equipmentList = controller.equipmentPerWell(w);
                        wellEquipmentMap.put(w, equipmentList);
                    });
                    System.out.println("Введите имя файла");
                    String fileName = reader.readLine();
                    export(wellEquipmentMap, fileName);
                    break;
                case ("4"):
                    System.out.println("Завершение программы");
                    break;
                default:
                    System.out.println("Что то пошло не так, попробуйте повторить");
            }
        } while (!userСhoiсe.equals("4"));
        reader.close();
    }

    private static void export(Map<Well, List<Equipment>> wellEquipmentMap, String filename) {
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(filename));

            writer.writeStartDocument("1.0");
            writer.writeStartElement("dbinfo");

            for (Map.Entry<Well, List<Equipment>> entry : wellEquipmentMap.entrySet()) {
                Well well = entry.getKey();
                List<Equipment> equipmentList = entry.getValue();

                writer.writeStartElement("well");
                writer.writeAttribute("name", well.getName());
                writer.writeAttribute("id", "" + well.getId());

                for (Equipment equipment : equipmentList) {
                    writer.writeEmptyElement("equipment");
                    writer.writeAttribute("name", equipment.getName());
                    writer.writeAttribute("id", "" + equipment.getId());
                }
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
