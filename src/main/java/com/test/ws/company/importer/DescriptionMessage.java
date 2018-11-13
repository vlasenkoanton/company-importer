package com.test.ws.company.importer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DescriptionMessage {
    private int id;
    private String name;
    private List<Data> data;

    Optional<Data> getMostRecentData() {
        return data.stream().max(Comparator.comparing(Data::getDate));
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DescriptionMessage{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", data=" + data
                + '}';
    }

    public static class Data {
        private boolean delete;
        private LocalDate date;
        private String description;

        public void setDelete(boolean delete) {
            this.delete = delete;
        }

        public boolean getDelete() {
            return delete;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Data{"
                    + "delete=" + delete
                    + ", date=" + date
                    + ", description='" + description + '\''
                    + '}';
        }
    }
}
