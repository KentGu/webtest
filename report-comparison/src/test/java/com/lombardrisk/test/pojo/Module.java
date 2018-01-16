package com.lombardrisk.test.pojo;

import java.util.List;

public class Module {
    private String name;
    private List<String> reports;

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
