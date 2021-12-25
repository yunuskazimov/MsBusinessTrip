package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.model.BTDto;
import az.xazar.msbusinesstrip.model.FileNameDto;
import az.xazar.msbusinesstrip.service.BTService;
import az.xazar.msbusinesstrip.service.BTServiceInt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/int/businesstrip")
public class BTControllerInt {
    private final BTServiceInt service;

    public BTControllerInt(BTServiceInt service) {
        this.service = service;
    }


    @PostMapping()
    public ResponseEntity<BTDto> createFileName(@RequestBody FileNameDto nameDto) {
        return new ResponseEntity<>(
                service.edit(nameDto), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteFileName(@PathVariable Long id) {
        service.delete(id);
    }
}
