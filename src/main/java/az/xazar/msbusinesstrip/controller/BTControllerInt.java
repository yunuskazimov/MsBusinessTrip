package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.model.BTDto;
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


    @PatchMapping("/{id}")
    public ResponseEntity<BTDto> edit(@PathVariable String fileName,
                                      @PathVariable Long id) {
        return new ResponseEntity<>(
                service.edit(fileName,id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
