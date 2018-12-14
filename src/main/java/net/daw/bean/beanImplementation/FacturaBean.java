/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean.beanImplementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import net.daw.bean.genericBeanImplementation.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.dao.specificDaoImplementation_1.LineaDao;
import net.daw.dao.specificDaoImplementation_1.UsuarioDao_1;
import net.daw.factory.DaoFactory;
import net.daw.helper.EncodingHelper;

/**
 *
 * @author a044531896d
 */
public class FacturaBean extends GenericBeanImplementation implements BeanInterface {

    private Date fecha;
    private double iva;
    private int id_usuario;
    private UsuarioBean obj_Usuario;
    private int link_linea;

    public UsuarioBean getObj_Usuario() {
        return obj_Usuario;
    }

    public void setObj_Usuario(UsuarioBean obj_Usuario) {
        this.obj_Usuario = obj_Usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getLink_linea() {
        return link_linea;
    }

    public void setLink_linea(int link_linea) {
        this.link_linea = link_linea;
    }

    @Override
    public FacturaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand, UsuarioBean oUsuarioBeanSession) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        //Timestamp LUL = oResultSet.getTimestamp("fecha");
        //this.setFecha(LUL);
        this.setFecha(oResultSet.getDate("fecha"));
        this.setIva(oResultSet.getDouble("iva"));

        if (expand > 0) {
            DaoInterface oUsuarioDao = DaoFactory.getDao(oConnection, "usuario", oUsuarioBeanSession);//¿¿¿???
            this.setObj_Usuario((UsuarioBean) oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand));
        }
        LineaDao oLineaDao = new LineaDao(oConnection, "linea", oUsuarioBeanSession);
        this.setLink_linea(oLineaDao.getcountxlinea(this.getId()));
        return this;
    }

    @Override
    public String getPairs() {

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Instant instant = fecha.toInstant();

        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        //System.out.println("Local Date is: " + localDate);

        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "fecha=" + EncodingHelper.quotate(localDate.toString()) + ",";
        strPairs += "iva=" + iva + ",";
        strPairs += "id_usuario=" + getObj_Usuario().getId();
        strPairs += " WHERE id=" + id;
        return strPairs;

    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "fecha,";
        strColumns += "iva,";
        strColumns += "id_usuario";
        return strColumns;
    }

    @Override
    public String getValues() {

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Instant instant = fecha.toInstant();

        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        System.out.println("Local Date is: " + localDate);
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDate.toString()) + ",";
        strColumns += iva + ",";
        if (getObj_Usuario() != null) {
            strColumns += this.getObj_Usuario().getId();
        } else {
            strColumns += this.getId_usuario();
        }
        return strColumns;
    }
}
