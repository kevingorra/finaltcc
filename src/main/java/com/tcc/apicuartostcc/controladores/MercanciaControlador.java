package com.tcc.apicuartostcc.controladores;


import com.tcc.apicuartostcc.entidades.Mercancia;
import com.tcc.apicuartostcc.entidades.Zona;
import com.tcc.apicuartostcc.servicios.implementaciones.MercanciasServicioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tcc/mercancias")
public class MercanciaControlador {
    @Autowired
    MercanciasServicioImp mercanciaservicio;



    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Mercancia mercancia){

        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaservicio.registrar(mercancia));
        }catch(Exception error){
            String mensaje="{\"error\":\"Error revise: "+error+"\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos(){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaservicio.buscarTodos());
        }catch(Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje: Datos no encontrados }");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaservicio.borrar(id));
        }catch(Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje: No se pudo actualizar }");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Mercancia mercancia){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaservicio.actualizar(id,mercancia));
        }catch(Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje: No se pudo actualizar }");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaservicio.buscarPorId(id));
        }catch(Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje: Datos no encontrados }");
        }
    }


}
