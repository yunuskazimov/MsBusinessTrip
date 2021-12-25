package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesstrip")
public class BTController {
    private final BusinessTripService service;

    public BTController(BusinessTripService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<BusinessTripDto> create(@RequestBody BusinessTripDto dto) {
        return new ResponseEntity<>(
                service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessTripDto> edit(@RequestBody BusinessTripDto dto,
                                                @PathVariable Long id) {
        dto.setId(id);
        return new ResponseEntity<>(
                service.edit(dto), HttpStatus.OK);
    }

    @GetMapping()
    public List<BusinessTripDto> getBTs() {
        return service.getList();
    }

    @GetMapping("id/{id}")
    public BusinessTripDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/uid/{uId}")
    public List<BusinessTripDto> getByUserId(@PathVariable Long uId) {
        return service.getByUserId(uId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
