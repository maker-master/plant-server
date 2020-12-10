package io.pcf.plantserver;

import java.util.Objects;

public class Plant {
    private Long id;
    private String name;
    private String description;
    private float height;

    public Plant() {}

    public Plant(Long id, String name, String description, float height) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.height = height;
    }

    public Plant(String name, String description, float height) {
        this.name = name;
        this.description = description;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Float.compare(plant.getHeight(), getHeight()) == 0 &&
                Objects.equals(getId(), plant.getId()) &&
                Objects.equals(getName(), plant.getName()) &&
                Objects.equals(getDescription(), plant.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getHeight());
    }
}
