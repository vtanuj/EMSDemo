/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ems.model;

import java.util.HashMap;
import java.util.Map;
import javafx.util.StringConverter;

/**
 *
 * @author tanujv
 */
public class MyModelConverter extends StringConverter<MyModel> {

    private final Map<String, MyModel> mapMyModels = new HashMap<>();

    @Override
    public String toString(MyModel myModel) {
        mapMyModels.put(myModel.getObj1(), myModel);
        return myModel.getObj2();
    }

    @Override
    public MyModel fromString(String id) {
        return mapMyModels.get(id);
    }
}
