/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean.beanImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import net.daw.bean.genericBeanImplementation.GenericBeanImplementation;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.dao.specificDaoImplementation.ProductoDao;

public class LineaBean extends GenericBeanImplementation implements BeanInterface {

    @Expose
    private int cantidad;
    @Expose(serialize = false)
    private int id_producto;
    @Expose
    private int id_factura;
    @Expose(deserialize = false)
    private ProductoBean obj_Producto;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public ProductoBean getObj_Producto() {
        return obj_Producto;
    }

    public void setObj_Producto(ProductoBean obj_Producto) {
        this.obj_Producto = obj_Producto;
    }

    @Override
    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand, UsuarioBean oUsuarioBeanSession) throws Exception {

        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        this.setId_factura(oResultSet.getInt("id_factura"));
        if (expand > 0) {
            ProductoDao oproductoDao = new ProductoDao(oConnection, "producto", oUsuarioBeanSession);
            this.setObj_Producto((ProductoBean) oproductoDao.get(oResultSet.getInt("id_producto"), expand - 1));
        } else {
            this.setId_producto(oResultSet.getInt("id_producto"));
        }
        return this;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_factura=" + id_factura + ",";
        strPairs += "id_producto=" + id_producto;
        strPairs += " WHERE id=" + id;
        return strPairs;

    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";
        strColumns += "id_factura,";
        strColumns += "id_producto";
        return strColumns;
    }

    @Override
    public String getValues() {

        String strColumns = "";
        strColumns += "null,";
        strColumns += this.getCantidad() + ",";
        strColumns += this.getId_factura() + ",";
        strColumns += this.getId_producto();

        return strColumns;
    }

}
