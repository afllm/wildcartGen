/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean.beanImplementation;

import java.sql.Connection;
import java.sql.ResultSet;

import com.google.gson.annotations.Expose;
import net.daw.bean.genericBeanImplementation.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.specificDaoImplementation_1.FacturaDao;

import net.daw.helper.EncodingHelper;
import net.daw.dao.specificDaoImplementation_1.TipousuarioDao;

/**
 *
 * @author jesus
 */
public class UsuarioBean extends GenericBeanImplementation implements BeanInterface {

    @Expose
    private String dni;
    @Expose
    private String nombre;
    @Expose
    private String ape1;
    @Expose
    private String ape2;
    @Expose
    private String login;
    @Expose(serialize = false)
    private String pass;
    @Expose(serialize = false)
    private int id_tipoUsuario;
    @Expose(deserialize = false)
    private TipousuarioBean obj_tipoUsuario;
    @Expose(deserialize = false)
    private int link_factura;

    public TipousuarioBean getObj_tipoUsuario() {
        return obj_tipoUsuario;
    }

    public void setObj_tipoUsuario(TipousuarioBean obj_tipoUsuario) {
        this.obj_tipoUsuario = obj_tipoUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }

    public int getLink_factura() {
        return link_factura;
    }

    public void setLink_factura(int link_factura) {
        this.link_factura = link_factura;
    }

    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand, UsuarioBean oUsuarioBeanSession) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApe1(oResultSet.getString("ape1"));
        this.setApe2(oResultSet.getString("ape2"));
        this.setLogin(oResultSet.getString("login"));
        this.setPass(oResultSet.getString("pass"));
        //FacturaService oFacturaService
        FacturaDao oFacturaDao = new FacturaDao(oConnection, "factura", oUsuarioBeanSession);
        this.setLink_factura(oFacturaDao.getcountFacturaUser(this.id));
        if (expand > 0) {
            TipousuarioDao otipousuarioDao = new TipousuarioDao(oConnection, "tipousuario", oUsuarioBeanSession);
            this.setObj_tipoUsuario((TipousuarioBean) otipousuarioDao.get(oResultSet.getInt("id_tipoUsuario"), expand - 1));
        } else {
            this.setId_tipoUsuario(oResultSet.getInt("id_tipoUsuario"));
        }

        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "dni,";
        strColumns += "nombre,";
        strColumns += "ape1,";
        strColumns += "ape2,";
        strColumns += "login,";
        strColumns += "pass,";
        strColumns += "id_tipoUsuario";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(dni) + ",";
        strColumns += EncodingHelper.quotate(nombre) + ",";
        strColumns += EncodingHelper.quotate(ape1) + ",";
        strColumns += EncodingHelper.quotate(ape2) + ",";
        strColumns += EncodingHelper.quotate(login) + ",";
        strColumns += EncodingHelper.quotate("DA8AB09AB4889C6208116A675CAD0B13E335943BD7FC418782D054B32FDFBA04") + ",";
        strColumns += id_tipoUsuario;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "dni=" + EncodingHelper.quotate(dni) + ",";
        strPairs += "nombre=" + EncodingHelper.quotate(nombre) + ",";
        strPairs += "ape1=" + EncodingHelper.quotate(ape1) + ",";
        strPairs += "ape2=" + EncodingHelper.quotate(ape2) + ",";
        strPairs += "login=" + EncodingHelper.quotate(login) + ",";
        strPairs += "id_tipoUsuario=" + id_tipoUsuario;
        strPairs += " WHERE id=" + id;
        return strPairs;

    }

}
