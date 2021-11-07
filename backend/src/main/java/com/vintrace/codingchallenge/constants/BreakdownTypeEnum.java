package com.vintrace.codingchallenge.constants;

public enum BreakdownTypeEnum {
    YEAR("year"),
    VARIETY("variety"),
    REGION("region"),
    YEAR_VARIETY("year-variety");

    private String type;

    BreakdownTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static BreakdownTypeEnum fromType(String type) {
        for (BreakdownTypeEnum e : BreakdownTypeEnum.values()) {
            if (e.type.equalsIgnoreCase(type)) {
                return e;
            }
        }
        return null;
    }
}
