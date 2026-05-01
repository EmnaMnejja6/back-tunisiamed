package com.example.projet.services;

import com.example.projet.entities.Admin;
import com.example.projet.entities.ClinicAdmin;
import com.example.projet.entities.User;
import java.util.List;

public interface IUserService {
    public Admin createAdmin(Admin admin);
    public ClinicAdmin createClinicAdmin(ClinicAdmin clinicAdmin);
    public User getUserById(Long id);
    public User getUserByEmail(String email);
    public List<User> getUsers();
    public User update(Long id, User user);
    public void delete(Long id);
}
