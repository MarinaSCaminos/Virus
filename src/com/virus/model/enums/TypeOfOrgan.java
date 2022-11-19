package com.virus.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum TypeOfOrgan {

    HEART,
    STOMACH,
    BRAIN,
    BONE,
    MULTICOLOR;

    public static String getNameType(TypeOfOrgan type) {
        if (type.equals(HEART)) {
            return "Corazón";
        }else if(type.equals(STOMACH)) {
            return "Estómago";
        }else if(type.equals(BRAIN)) {
            return "Cerebro";
        }else if(type.equals(BONE)) {
            return "Hueso";
        }else{
            return "Multicolor";
        }
    }

    public static List<TypeOfOrgan> getListType() {
        List<TypeOfOrgan> list = new ArrayList<>();
        list.add(HEART);
        list.add(STOMACH);
        list.add(BRAIN);
        list.add(BONE);
        list.add(MULTICOLOR);
        return list;
    }

}