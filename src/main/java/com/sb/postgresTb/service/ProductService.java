package com.sb.postgresTb.service;

import java.util.List;

public interface ProductService {

    public List<String> getTableNames();

    public void ExcelGeneratorDB();
}
