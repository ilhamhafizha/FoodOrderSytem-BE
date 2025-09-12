package org.fos.foodordersystem.model.request;


import org.fos.foodordersystem.model.enums.Role;
import org.fos.foodordersystem.model.enums.Status;

public record UserRequestRecord(String id,
                                String nama,
                                String username,
                                String email,
                                String password,
                                Status status,
                                Role role) {
}