package com.banking.internet_banking.enums;

import java.util.HashMap;
import java.util.Map;

public enum OperationTypes {
    TAKE(0, "Снятие со счета"),
    PUT(1, "Пополнение счета"),
    TRANSFER_TO(2, "Перевод другому клиенту"),
    TRANSFER_FROM(3, "Перевод от другого клиента");

    private final int operTypeIndex;
    private final String typeName;
    private static final Map<Integer, OperationTypes> mapValues = new HashMap<>();

    OperationTypes(int operTypeIndex, String typeName) {
        this.operTypeIndex = operTypeIndex;
        this.typeName = typeName;
    }

    public int getOperTypeIndex() {
        return operTypeIndex;
    }

    public String getTypeName() {
        return typeName;
    }

    public static OperationTypes valueOf(int operTypeIndex) {
        return mapValues.get(operTypeIndex);
    }

    static {
        for (OperationTypes type: values()) {
            mapValues.put(type.getOperTypeIndex(), type);
        }
    }
}
