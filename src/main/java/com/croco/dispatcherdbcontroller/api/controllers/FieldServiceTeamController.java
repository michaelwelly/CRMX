package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.dto.FieldServiceTeamDto;
import com.croco.dispatcherdbcontroller.api.clients.FieldServiceTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/field-service-teams")
public class FieldServiceTeamController {
    private final FieldServiceTeamService fieldServiceTeamService;

    @Autowired
    public FieldServiceTeamController(FieldServiceTeamService fieldServiceTeamService) {
        this.fieldServiceTeamService = fieldServiceTeamService;
    }

    @GetMapping
    public ResponseEntity<List<FieldServiceTeamDto>> getFieldServiceTeams() {
        List<FieldServiceTeamDto> fieldServiceTeamDtos = fieldServiceTeamService.getList();
        return ResponseEntity.ok(fieldServiceTeamDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldServiceTeamDto> getFieldServiceTeam(@PathVariable Long id) {
        FieldServiceTeamDto fieldServiceTeamDto = fieldServiceTeamService.getOne(id);
        return ResponseEntity.ok(fieldServiceTeamDto);
    }

    @PostMapping
    public ResponseEntity<FieldServiceTeamDto> create(@RequestBody @Validated FieldServiceTeamDto fieldServiceTeamDto) {
        FieldServiceTeamDto createdFieldServiceTeam = fieldServiceTeamService.create(fieldServiceTeamDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFieldServiceTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldServiceTeamDto> update(@PathVariable Long id, @RequestBody @Validated FieldServiceTeamDto fieldServiceTeamDto) {
        FieldServiceTeamDto updatedFieldServiceTeam = fieldServiceTeamService.update(id, fieldServiceTeamDto);
        return ResponseEntity.ok(updatedFieldServiceTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FieldServiceTeamDto> delete(@PathVariable Long id) {
        FieldServiceTeamDto deletedFieldServiceTeam = fieldServiceTeamService.delete(id);
        return ResponseEntity.ok(deletedFieldServiceTeam);
    }
}