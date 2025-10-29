package com.tamersarioglu.loadlogic.service

import com.tamersarioglu.loadlogic.dto.AvailableUserResponse
import com.tamersarioglu.loadlogic.dto.EquipmentResponse
import com.tamersarioglu.loadlogic.dto.MaterialResponse
import com.tamersarioglu.loadlogic.entity.Role
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for reference data operations.
 * Provides predefined lists of materials, equipment, and available users.
 */
@Service
@Transactional(readOnly = true)
class ReferenceDataService(
    private val userService: UserService,
    @Value("\${app.materials}") private val materials: List<String>,
    @Value("\${app.equipment}") private val equipment: List<String>
) {

    /**
     * Returns the list of available materials.
     * Used for job creation and validation.
     * 
     * @return List of MaterialResponse containing all available materials
     */
    fun getMaterials(): List<MaterialResponse> {
        return materials.map { MaterialResponse(name = it) }
    }

    /**
     * Returns the list of available equipment.
     * Used for job creation and validation.
     * 
     * @return List of EquipmentResponse containing all available equipment
     */
    fun getEquipment(): List<EquipmentResponse> {
        return equipment.map { EquipmentResponse(name = it) }
    }

    /**
     * Returns available users filtered by role.
     * Used for job assignment dropdowns.
     * 
     * @param role The role to filter users by (optional)
     * @return List of AvailableUserResponse containing users with the specified role
     */
    fun getAvailableUsers(role: Role? = null): List<AvailableUserResponse> {
        val users = if (role != null) {
            userService.getActiveUsersByRole(role)
        } else {
            userService.getAllActiveUsers()
        }

        return users.map { user ->
            AvailableUserResponse(
                username = user.username,
                fullName = user.fullName,
                role = user.role
            )
        }
    }

    /**
     * Returns available drivers for job assignment.
     * 
     * @return List of AvailableUserResponse containing all active drivers
     */
    fun getAvailableDrivers(): List<AvailableUserResponse> {
        return getAvailableUsers(Role.DRIVER)
    }

    /**
     * Returns available crew members for job assignment.
     * 
     * @return List of AvailableUserResponse containing all active crew members
     */
    fun getAvailableCrew(): List<AvailableUserResponse> {
        return getAvailableUsers(Role.CREW)
    }

    /**
     * Returns available chiefs in the system.
     * 
     * @return List of AvailableUserResponse containing all active chiefs
     */
    fun getAvailableChiefs(): List<AvailableUserResponse> {
        return getAvailableUsers(Role.CHIEF)
    }

    /**
     * Validates if a material name is in the predefined list.
     * 
     * @param materialName The material name to validate
     * @return true if material is valid, false otherwise
     */
    fun isValidMaterial(materialName: String): Boolean {
        return materials.contains(materialName)
    }

    /**
     * Validates if an equipment name is in the predefined list.
     * 
     * @param equipmentName The equipment name to validate
     * @return true if equipment is valid, false otherwise
     */
    fun isValidEquipment(equipmentName: String): Boolean {
        return equipment.contains(equipmentName)
    }

    /**
     * Returns the complete list of valid material names.
     * 
     * @return List of valid material names
     */
    fun getValidMaterialNames(): List<String> {
        return materials.toList()
    }

    /**
     * Returns the complete list of valid equipment names.
     * 
     * @return List of valid equipment names
     */
    fun getValidEquipmentNames(): List<String> {
        return equipment.toList()
    }
}