package com.tcc.apicuartostcc.servicios.implementaciones;


import com.tcc.apicuartostcc.entidades.Mercancia;
import com.tcc.apicuartostcc.entidades.Zona;
import com.tcc.apicuartostcc.repositorios.Mercanciarepositorio;
import com.tcc.apicuartostcc.repositorios.Zonarepositorio;
import com.tcc.apicuartostcc.servicios.ServicioGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MercanciasServicioImp  implements ServicioGenerico<Mercancia> {

    @Autowired
    Mercanciarepositorio mercanciarepositorio;
    @Autowired
    Zonarepositorio zonarepositorio;


    @Override
    public List<Mercancia> buscarTodos() throws Exception {

        try{

            List<Mercancia> mercancias = mercanciarepositorio.findAll();
            return mercancias;

        }catch(Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia buscarPorId(Integer id) throws Exception {

        try{

            Optional<Mercancia> mercancia= mercanciarepositorio.findById(id);
            return mercancia.get();

        }catch(Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia registrar(Mercancia tabla ) throws Exception {

        try{

            Optional<Zona> zonaAsociadaMercancia=zonarepositorio.findById(tabla.getZona().getId());
            Double capacidadDisponibleZona=zonaAsociadaMercancia.get().getDisponible();
            Double capacidadOcupadaMercancia= tabla.getVolumen();
            Double capacidadRestante=capacidadDisponibleZona-capacidadOcupadaMercancia;

            if (capacidadRestante>=0){
                zonaAsociadaMercancia.get().setDisponible(capacidadRestante);
                zonarepositorio.save(zonaAsociadaMercancia.get());
                tabla=mercanciarepositorio.save(tabla);
                return tabla;
            }else {
                throw new Exception("No tenemos Capacidad Para esta Mercancia");
            }


            //tabla=mercanciarepositorio.save(tabla);

        }catch(Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia actualizar(Integer id, Mercancia tabla) throws Exception {

        try{

            Optional<Mercancia> mercanciaBuscada=mercanciarepositorio.findById(id);
            Mercancia mercancia=mercanciaBuscada.get();
            mercancia=mercanciarepositorio.save(tabla);

            return mercancia;

        }catch(Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Boolean borrar(Integer id) throws Exception {

        try{

            if(mercanciarepositorio.existsById(id)){

                Optional<Mercancia>mercanciaARetirar=mercanciarepositorio.findById(id);
                Optional<Zona>zonaAsociada=zonarepositorio.findById(mercanciaARetirar.get().getZona().getId());
                Double capaciadadOcupada=mercanciaARetirar.get().getVolumen();
                Double capacidadDisponible=zonaAsociada.get().getDisponible();
                Double capacidadLiberada=capacidadDisponible+capaciadadOcupada;
                zonaAsociada.get().setDisponible(capacidadLiberada);
                zonarepositorio.save(zonaAsociada.get());
                mercanciarepositorio.deleteById(id);
                return true;

            }else{

                throw new Exception();

            }

        }catch(Exception error){
            throw new Exception(error.getMessage());
        }

    }
}
