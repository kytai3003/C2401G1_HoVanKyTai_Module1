package resort_management.services;

import resort_management.common.FacilityReadAndWriteFile;
import resort_management.models.*;
import resort_management.regex.FacilityInformationRegex;
import resort_management.services.interfaces.FacilityService;

import java.io.File;
import java.util.*;

public class FacilityServiceImpl implements FacilityService<Facility> {
    static Scanner sc = new Scanner(System.in);
    private static final LinkedHashMap<Facility, Integer> facilityMap = new LinkedHashMap<>();
    private static final String FILE_PATH_VILLA = "D:\\C2401G1_HoVanKyTai\\module_02\\src\\resort_management\\data\\villa.csv";
    private static final String FILE_PATH_HOUSE = "D:\\C2401G1_HoVanKyTai\\module_02\\src\\resort_management\\data\\house.csv";
    private static final String FILE_PATH_ROOM = "D:\\C2401G1_HoVanKyTai\\module_02\\src\\resort_management\\data\\room.csv";
    private static final FacilityInformationRegex fir = new FacilityInformationRegex();
    static FacilityReadAndWriteFile facilityReadAndWriteFile = new FacilityReadAndWriteFile();
    private static Map<Facility, Integer> villaMap = new LinkedHashMap<>();
    private static Map<Facility, Integer> houseMap = new LinkedHashMap<>();
    private static Map<Facility, Integer> roomMap = new LinkedHashMap<>();
    static {
            villaMap = facilityReadAndWriteFile.readFile(FILE_PATH_VILLA);
            houseMap = facilityReadAndWriteFile.readFile(FILE_PATH_HOUSE);
            roomMap = facilityReadAndWriteFile.readFile(FILE_PATH_ROOM);
            facilityMap.putAll(villaMap);
            facilityMap.putAll(houseMap);
            facilityMap.putAll(roomMap);
    }

    private static boolean checkFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    @Override
    public void addNew() {
                while (true) {
                    int addNewChoice = 0;
                    try {
                        addNewChoice = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println("Input number only");
                    }
                    switch (addNewChoice) {
                        case 1:
                            addNewVilla();
                            break;

                        case 2:
                            addNewHouse();
                            break;

                        case 3:
                            addNewRoom();
                            break;

                        case 4:
                            return;

                        default:
                            System.err.println("False input. Please retry.");
                    }
                }
        }

    @Override
    public void displayList() {
        if (facilityMap.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Map.Entry<Facility, Integer> element : facilityMap.entrySet()) {
                System.out.println("Service" + element.getKey() + "\n" + "Used times: " + element.getValue());
            }
        }
    }

    @Override
    public void displayListMaintenance() {
        boolean noNeedToMaintain = false;
        for (Map.Entry<Facility, Integer> entry : facilityMap.entrySet()) {
            if (entry.getValue() >= 5) {
                System.out.println(entry.getKey().getNameOfService() + ", used times: " + entry.getValue());
            } else {
                noNeedToMaintain = true;
            }
        }
        if (noNeedToMaintain) {
            System.out.println("All facilities is ok!");
        }
    }

    public void addRegularProperties(Facility f) {
        System.out.println("Input using area (Bigger than 30): ");
        String areaLiving = fir.legalLivingAndPoolArea();
        f.setUsingArea(areaLiving);
        System.out.println("Input price: ");
        String price = fir.legalPriceAndLevel();
        f.setPrice(price);
        System.out.println("Input capacity: (Min: 1 - Max: 20)");
        String capacity = fir.legalCapacity();
        f.setCapacity(capacity);
        System.out.println("Input type of hiring: (Starting with first letter uppercased)"); // Dùng Regular Expression
        String type = fir.legalServiceBasicInfo();
        f.setTypeOfHiring(type);
    }

    public LinkedHashMap<Facility, Integer> getList() {
        return facilityMap;
    }

    public void addNewVilla() {
        if (checkFile(FILE_PATH_VILLA)) {
            facilityReadAndWriteFile.readFile(FILE_PATH_VILLA);
        }
        System.out.println("You chose Add new villa.");
        Villa newVilla = new Villa();
        System.out.println("Input code of service."); // Dùng Regular Expression
        String codeVilla = fir.codeVilla();
        newVilla.setNameOfService(codeVilla);
        addRegularProperties(newVilla);
        System.out.println("Input room standard: (Starting with first letter uppercased)"); // Dùng Regular Expression
        String standardVilla = fir.legalServiceBasicInfo();
        newVilla.setRoomStandard(standardVilla);
        System.out.println("Input pool area (Bigger than 30): ");
        String areaPool = fir.legalLivingAndPoolArea();
        newVilla.setPoolArea(areaPool);
        System.out.println("Input number of level(s): ");
        String levelVilla = fir.legalPriceAndLevel();
        newVilla.setNumberOfLevel(levelVilla);
        villaMap.put(newVilla, 0);
        facilityMap.put(newVilla, 0);
        facilityReadAndWriteFile.writeFile(FILE_PATH_VILLA, villaMap);
        System.out.println("Success.");
    }

    public void addNewHouse() {
        if (checkFile(FILE_PATH_HOUSE)) {
            facilityReadAndWriteFile.readFile(FILE_PATH_HOUSE);
        }
        System.out.println("You chose Add new house.");
        House newHouse = new House();
        System.out.println("Input code of service."); // Dùng Regular Expression
        String codeHouse = fir.codeHouse();
        newHouse.setNameOfService(codeHouse);
        addRegularProperties(newHouse);
        System.out.println("Input room standard: (Starting with first letter uppercased)"); // Dùng Regular Expression
        String standardHouse = fir.legalServiceBasicInfo();
        newHouse.setRoomStandard(standardHouse);
        System.out.println("Input number of level(s): ");
        String levelHouse = fir.legalPriceAndLevel();
        newHouse.setNumberOfLevel(levelHouse);
        houseMap.put(newHouse,0);
        facilityMap.put(newHouse, 0);
        facilityReadAndWriteFile.writeFile(FILE_PATH_HOUSE, houseMap);
        System.out.println("Success.");
    }

    public void addNewRoom() {
        if (checkFile(FILE_PATH_ROOM)) {
            facilityReadAndWriteFile.readFile(FILE_PATH_ROOM);
        }
        System.out.println("You chose Add new room.");
        Room newRoom = new Room();
        System.out.println("Input code of service."); // Dùng Regular Expression
        String codeHouse = fir.codeRoom();
        newRoom.setNameOfService(codeHouse);
        addRegularProperties(newRoom);
        System.out.println("Input free service(s): (Starting with first letter uppercased)"); // Dùng Regular Expression
        String freeService = fir.legalServiceBasicInfo();
        newRoom.setFreeServices(freeService);
        roomMap.put(newRoom, 0);
        facilityMap.put(newRoom, 0);
        facilityReadAndWriteFile.writeFile(FILE_PATH_ROOM, roomMap);
        System.out.println("Success.");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

