package org.silo.modelos.servicios;

import java.util.List;
import org.silo.modelos.bo.Cliente;
import org.silo.modelos.bo.CopiaPelicula;
import org.silo.modelos.bo.Empleado;
import org.silo.modelos.bo.Genero;
import org.silo.modelos.bo.Pelicula;
import org.silo.modelos.dao.ClienteDAO;
import org.silo.modelos.dao.CopiaPeliculaDAO;
import org.silo.modelos.dao.EmpleadoDAO;
import org.silo.modelos.dao.GeneroDAO;
import org.silo.modelos.dao.PeliculaDAO;

public class ServiciosSILO {

    private static final ServiciosSILO servicios = new ServiciosSILO();

    public static ServiciosSILO getServicios() {
        return servicios;
    }

    private PeliculaDAO peliculaDAO;
    private GeneroDAO generoDAO;
    private EmpleadoDAO empleadoDAO;
    private ClienteDAO clienteDAO;
    private CopiaPeliculaDAO copiaDAO;
//    private VentaDAO ventaDAO;

    public ServiciosSILO() {
        peliculaDAO = new PeliculaDAO();
        generoDAO = new GeneroDAO();
        empleadoDAO = new EmpleadoDAO();
        clienteDAO = new ClienteDAO();
        copiaDAO = new CopiaPeliculaDAO();
//        ventaDAO = new VentaDAO();
    }

    public List<Pelicula> catalogoPeliculas() {
        return peliculaDAO.buscarTodosCommit();
    }

    public List<Genero> catalogoGeneros() {
        return generoDAO.buscarTodosCommit();
    }

    public List<Empleado> catalogoEmpleados() {
        return empleadoDAO.buscarTodosCommit();
    }

    public List<Cliente> catalogoClientes() {
        return clienteDAO.buscarTodosCommit();
    }

    public List<CopiaPelicula> catalogoCopias() {
        return copiaDAO.buscarTodosCommit();
    }

//    public List<Venta> catalogoVentas() {
//        return ventaDAO.buscarTodosCommit();
//    }
//
    public boolean altaEmpleado(Empleado e) {
        return empleadoDAO.persistirCommit(e);
    }

    public boolean altaCliente(Cliente e) {
        return clienteDAO.persistirCommit(e);
    }

    public boolean altaGenero(Genero e) {
        return generoDAO.persistirCommit(e);
    }

    public boolean altaPelicula(Pelicula e) {
        return peliculaDAO.persistirCommit(e);
    }

    public boolean altaCopiaPelicula(CopiaPelicula e) {
        return copiaDAO.persistirCommit(e);
    }

//    public boolean altaVenta(Venta e) {
//        return ventaDAO.persistirCommit(e);
//    }
//
    public void actualizaEmpleado(Empleado e) {
        empleadoDAO.actualizarCommit(e);
    }

    public void actualizaCliente(Cliente e) {
        clienteDAO.actualizarCommit(e);
    }

    public void actualizaGenero(Genero e) {
        generoDAO.actualizarCommit(e);
    }

    public void actualizaPelicula(Pelicula e) {
        peliculaDAO.actualizarCommit(e);
    }

    public void actualizaCopiaPelicula(CopiaPelicula e) {
        copiaDAO.actualizarCommit(e);
    }

//    public void actualizaVenta(Venta e) {
//        ventaDAO.actualizarCommit(e);
//    }
    public boolean eliminaCliente(Cliente e) {
        return clienteDAO.eliminarCommit(e);
    }

    public boolean eliminaEmpleado(Empleado e) {
        return empleadoDAO.eliminarCommit(e);
    }

    public boolean eliminaGenero(Genero e) {
        return generoDAO.eliminarCommit(e);
    }

    public boolean eliminaPelicula(Pelicula e) {
        return peliculaDAO.eliminarCommit(e);
    }

    public boolean eliminaCopia(CopiaPelicula e) {
        return copiaDAO.eliminarCommit(e);
    }

    public Cliente buscaClientePorID(String id) {
        return clienteDAO.buscarPorIdCommit(id);
    }

    public CopiaPelicula buscaCopiaPorID(Long id) {
        return copiaDAO.buscarPorIdCommit(id);
    }
}
