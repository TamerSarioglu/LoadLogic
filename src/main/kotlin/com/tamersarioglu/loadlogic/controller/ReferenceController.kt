package com.tamersarioglu.loadlogic.controller

import com.tamersarioglu.loadlogic.dto.AvailableUserResponse
import com.tamersarioglu.loadlogic.dto.EquipmentResponse
import com.tamersarioglu.loadlogic.dto.MaterialResponse
import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.service.ReferenceDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for reference data operations.
 * Provides endpoints for materials, equipment, and available users.
 */
@RestController
@RequestMapping("/api/reference")
@CrossOrigin(origins = ["*"])
class ReferenceController(
    private val referenceDataService: ReferenceDataService
) {

    /**
     * Retrieves the list of available materials.
     * Accessible by all authenticated users.
     * 
     * @return ResponseEntity with list of MaterialResponse objects
     */
    @GetMapping("/materials")
    fun getMaterials(): ResponseEntity<List<MaterialResponse>> {
        return try {
            val materials = referenceDataService.getMaterials()
            ResponseEntity.ok(materials)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * Retrieves the list of available equipment.
     * Accessible by all authenticated users.
     * 
     * @return ResponseEntity with list of EquipmentResponse objects
     */
    @GetMapping("/equipment")
    fun getEquipment(): ResponseEntity<List<EquipmentResponse>> {
        return try {
            val equipment = referenceDataService.getEquipment()
            ResponseEntity.ok(equipment)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}

/**
 * REST controller for user-related reference data operations.
 * Provides endpoints for available users by role.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserReferenceController(
    private val referenceDataService: ReferenceDataService
) {

    /**
     * Retrieves available users, optionally filtered by role.
     * Accessible by all authenticated users.
     * 
     * @param role Optional role parameter to filter users (CHIEF, DRIVER, CREW)
     * @return ResponseEntity with list of AvailableUserResponse objects
     */
    @GetMapping("/available")
    fun getAvailableUsers(@RequestParam(required = false) role: Role?): ResponseEntity<List<AvailableUserResponse>> {
        return try {
            val users = referenceDataService.getAvailableUsers(role)
            ResponseEntity.ok(users)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}