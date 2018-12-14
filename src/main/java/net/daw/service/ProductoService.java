package net.daw.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.beanImplementation.ReplyBean;
import net.daw.bean.beanImplementation.ProductoBean;
import net.daw.bean.beanImplementation.UsuarioBean;
import net.daw.bean.publicBeanInterface.BeanInterface;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.dao.publicDaoInterface.DaoInterface;
import net.daw.dao.specificDaoImplementation_1.ProductoDao;
import net.daw.factory.ConnectionFactory;
import net.daw.factory.DaoFactory;
import net.daw.helper.EncodingHelper;
import net.daw.helper.ParameterCook;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProductoService {

    HttpServletRequest oRequest;
    String ob = null;
    UsuarioBean oUsuarioBean;

    public ProductoService(HttpServletRequest oRequest) {
        super();
        this.oRequest = oRequest;
        ob = oRequest.getParameter("ob");
    }

    protected Boolean checkPermission(String strMethodName) {
        oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            return true;
        } else {
            return false;
        }
    }

    public ReplyBean get() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("get")) {
            try {
               Gson oGson = new Gson();
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
//                TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
                
                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);

                BeanInterface oBean  = oDao.get(id, 1);
               
                oReplyBean = new ReplyBean(200, oGson.toJson(oBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }

        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("remove")) {
            try {
                Integer id = Integer.parseInt(oRequest.getParameter("id"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                //TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);
                int iRes = oDao.remove(id);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: remove method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

    public ReplyBean getcount() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("getcount")) {
            try {
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();

                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);

                //TipoproductoDao oTipoproductoDao = new TipoproductoDao(oConnection, ob);
                int registros = oDao.getcount();

                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson(registros));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: getcount method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }

        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;

    }

    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        if (this.checkPermission("create")) {
            try {
                 String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
//                TipoproductoBean oTipoproductoBean = new TipoproductoBean();
//                oTipoproductoBean = oGson.fromJson(strJsonFromClient, TipoproductoBean.class);
                BeanInterface oBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();

                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);
                oBean = oDao.create(oBean);
                oReplyBean = new ReplyBean(200, oGson.toJson(oBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean update() throws Exception {
        int iRes = 0;
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        // if (this.checkPermission("update")) {
        if (this.checkPermission("update")) {
            try {
                   String strJsonFromClient = oRequest.getParameter("json");
                Gson oGson = new Gson();
                BeanInterface oBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);
                iRes = oDao.update(oBean);
                oReplyBean = new ReplyBean(200, Integer.toString(iRes));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500,
                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            } finally {
                oConnectionPool.disposeConnection();
            }
            //  } else {
            // oReplyBean = new ReplyBean(401, "Unauthorized");
            // }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        // if (this.checkPermission("getpage")) {
        if (this.checkPermission("getpage")) {
            try {
                 Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
                Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
                HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                DaoInterface oDao = DaoFactory.getDao(oConnection, ob,oUsuarioBean);
                ArrayList<BeanInterface> alBean = oDao.getpage(iRpp, iPage, hmOrder, 1);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
            } finally {
                oConnectionPool.disposeConnection();
            }

            // } else {
            //     oReplyBean = new ReplyBean(401, "Unauthorized");
            // }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean loaddata() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        ArrayList<ProductoBean> productos = new ArrayList<>();
        RellenarService oRellenarService = new RellenarService();
        if (this.checkPermission("loaddata")) {
            try {
                Integer number = Integer.parseInt(oRequest.getParameter("number"));
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                ProductoDao oProductoDao = new ProductoDao(oConnection, ob,oUsuarioBean);
                productos = oRellenarService.RellenarProducto(number);
                for (ProductoBean producto : productos) {
                    oProductoDao.create(producto);
                }
                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson("Productos creados: " + number));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500,
                        "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean loadimage() throws Exception {
        ReplyBean oReplyBean = null;
        String name = "";
        String strMessage = "";
        HashMap<String, String> hash = new HashMap<>();
        if (ServletFileUpload.isMultipartContent(oRequest)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        item.write(new File(".//..//webapps//imagenes//" + name));
                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
                strMessage = "File upload";
                Gson oGson = new Gson();
                oReplyBean = new ReplyBean(200, oGson.toJson(strMessage));
            } catch (Exception ex) {
                strMessage = "Failed to upload file";
                oReplyBean = new ReplyBean(500, "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())) + strMessage);
            }
        } else {
            strMessage = "File no upload";
            oReplyBean = new ReplyBean(500, "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(strMessage)));
        }

        return oReplyBean;

    }

}
