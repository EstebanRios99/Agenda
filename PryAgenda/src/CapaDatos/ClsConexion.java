/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fundamentos DS
 */
public class ClsConexion {
       private Connection _base;
    private Statement _tabla;  
    private ResultSet _registros;
    
    private String nom;
    private String dir;
    private String desc;
    
    public ClsConexion (String dirbase){
        try{
        _base = DriverManager.getConnection("jdbc:ucanaccess://" + dirbase);
        _tabla = _base.createStatement(ResultSet.FETCH_UNKNOWN, ResultSet.TYPE_SCROLL_SENSITIVE);
        System.out.println("*** conexion exitosa ***");
        
        }catch (SQLException err){
              System.out.println("*** conexion errada ***" + err);
        }
    }

    public ResultSet registros() {
        return _registros;
    }
    
    public boolean consulta(String tabla) throws SQLException{
        boolean resp=false;
        String cadena= "SELECT * FROM " + tabla;
        _registros=null;
        _tabla.execute(cadena);
        _registros=_tabla.getResultSet();
        if(_registros!=null){
            resp=true;
        }
        return resp;
    }
        public void anterior()throws SQLException{
            try{
           if (!(_registros.previous())){
               _registros.last();
           }
       }catch(SQLException err){
             System.out.println("*** error recorrer ***" + err);
        }
    }
    public void siguiente()throws SQLException{
       try{
           if (!(_registros.next())){
               _registros.first();
           }
       }catch(SQLException err){
             System.out.println("*** error recorrer ***" + err);
        }        
    }
     public boolean insertar(String tabla, String nom, String ape, String telf) throws SQLException{
        String values = "'"+nom+"', '"+ape+"', '"+telf+"'";
        String comando = "INSERT INTO " + tabla + "(Nombre,Apellido,Telefono) VALUES("+values+")";
        int rowCount = _tabla.executeUpdate(comando);
       this.consulta("Agenda");
        return true;
    }
      public void actualizar(String tabla, String nom, String ape,  String telf,   int clave) throws SQLException{
      
        String comando ="UPDATE " + tabla + " SET "+"nombre='"+nom+"', apellido='"+ape+"', telefono='"+telf+"' WHERE IdAliente='"+clave+"'";
        
         _tabla.executeUpdate(comando);
        this.consulta("Agenda");
        
    }
      public void eliminar(String tabla, int clave) throws SQLException{
        String cadena= "DELETE FROM "+tabla+" WHERE IdAgenda="+clave;
        _tabla.executeUpdate(cadena);
        this.consulta("Agenda");
    }  
}
