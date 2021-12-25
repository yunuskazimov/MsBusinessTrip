package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.model.BTDto;
import az.xazar.msbusinesstrip.service.BTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesstrip")
public class BTController {
    private final BTService service;

    public BTController(BTService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BTDto> create(@RequestBody BTDto dto) {
        return new ResponseEntity<>(
                service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BTDto> edit(@RequestBody BTDto dto,
                                      @PathVariable Long id) {
        dto.setId(id);
        return new ResponseEntity<>(
                service.edit(dto), HttpStatus.OK);
    }

    @GetMapping()
    public List<BTDto> getBTs() {
        return service.getList();
    }

    @GetMapping("id/{id}")
    public BTDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/uid/{uId}")
    public List<BTDto> getByUserId(@PathVariable Long uId) {
        return service.getByUserId(uId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
