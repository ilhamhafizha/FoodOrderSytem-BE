package org.fos.foodordersystem.model.filter;


import org.fos.foodordersystem.model.enums.Role;
import org.fos.foodordersystem.model.enums.Status;

public record UserFilterRecord(String nama,
                               String email,
                               String username,
                               Status status,
                               Role role) {
}