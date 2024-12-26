package com.richlife.topic.RichLife_Pharma.constants;

public enum TopicStatus {
    ACTIVE("Hoạt động"),
    /**
     * Inactive account status.
     */
    INACTIVE("Không hoạt động");

    /**
     * -- GETTER --
     *  Gets name.
     *
     * @return the name
     */
    public final String name;

    TopicStatus(String name) {
        this.name = name;
    }
}
