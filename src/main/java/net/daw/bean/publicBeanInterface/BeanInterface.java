package net.daw.bean.publicBeanInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import net.daw.bean.beanImplementation.UsuarioBean;

public interface BeanInterface {

    public int getId();

    public void setId(int id);

    public BeanInterface fill(ResultSet oResultSet, Connection oConnection, Integer expand, UsuarioBean oUsuarioBeanSession) throws Exception;

    public String getColumns();

    public String getValues();

    public String getPairs();

}
