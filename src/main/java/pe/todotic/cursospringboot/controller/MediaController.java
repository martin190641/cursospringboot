package pe.todotic.cursospringboot.controlador;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.todotic.cursospringboot.service.FileSystemStorageService;

import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Controller
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("/{nombreArchivo}")
    ResponseEntity<Resource> getMedia(@PathVariable String nombreArchivo) throws IOException {
        Resource recurso = fileSystemStorageService.loadAsResource(nombreArchivo);
        String contentType = Files.probeContentType(recurso.getFile().toPath());

//        log.info("El tipo de contenido del archivo {} es {}", nombreArchivo, contentType);

        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .body(recurso);
    }
}
