package by.zhigarev.dao.impl;

import by.zhigarev.dao.AppliaceDAO;
import by.zhigarev.entity.Appliance;
import by.zhigarev.entity.Factory;
import by.zhigarev.entity.criteria.Criteria;
import by.zhigarev.entity.enums.TypesOfDevice;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppliaceDAOImpl implements AppliaceDAO {
    private final String filePath = "appliances_db.txt";
    private BufferedReader reader;
    private Factory factory;

    public AppliaceDAOImpl() {
        factory = new Factory();
    }

    @Override
    public List<Appliance> find(Criteria criteria) throws IOException {
        List<Appliance> devices = new ArrayList<>();
        List<HashMap<String, Object>> listOfElectroDevicesParams = getListOfElectroDevices(criteria);
        for(HashMap<String, Object> params : listOfElectroDevicesParams){
            Appliance device = factory.getElectroDevice((TypesOfDevice) params.get("type"),params);
            devices.add(device);
        }

        return devices;
    }

    private List<HashMap<String, Object>> getListOfElectroDevices(Criteria criteria) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
        HashMap<String, Object> paramsMap;
        List<HashMap<String, Object>> resultList = new ArrayList<>();

        String line = reader.readLine();
        while (line != null) {
            if (checkLine(line, criteria)) {
                paramsMap = splitLineToParams(line);
                resultList.add(paramsMap);
            }
            line = reader.readLine();
        }
        return resultList;
    }

    private boolean checkLine(String line, Criteria criteria) {
        boolean result = false;
        HashMap<String, Object> params;

        if (line.contains(criteria.getGroupSearchName())) {
            params = splitLineToParams(line);
            for (String paramToSearch : criteria.getKeys()) {
                if (params.get(paramToSearch).equals(criteria.get(paramToSearch))) {
                    result = true;
                } else {
                    return false;
                }
            }
        }
        return result;

    }

    private HashMap<String, Object> splitLineToParams(String line) {
        HashMap<String ,Object> resultMap = new HashMap<String ,Object>();

        String[] params = line.split(":");
        TypesOfDevice type = TypesOfDevice.valueOf(params[0].trim());

        resultMap.put("type",type);

        for (String param : params[1].split(",")) {
            String[] result = param.split("=");
            resultMap.put(result[0].trim(), result[1].trim());
        }
        return resultMap;
    }

}
