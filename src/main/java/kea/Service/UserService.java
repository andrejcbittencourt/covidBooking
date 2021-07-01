package kea.Service;

import kea.Model.User;
import kea.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  UserRepo userRepo;

  public Long getIdByName(String username) {
    Iterable<User> users = userRepo.findAll();
    for(User user : users) {
      if(user.getUsername().equals(username))
        return user.getUserID();
    }
    return (long) -1;
  }

  public String getFullNameById(Long id) {
    Optional<User> user = findById(id);
    return user.map(value -> value.getFirstName() + " " + value.getLastName()).orElse("");
  }

  public String getFullNameByUsername(String username) {
    Iterable<User> users = userRepo.findAll();
    for(User user : users) {
      if(user.getUsername().equals(username))
        return user.getFirstName()+" "+user.getLastName();
    }
    return "";
  }

  public Iterable<User> findByName(String name) {
    Iterable<User> allUsers = findAll();
    if(name.isEmpty())
      return allUsers;
    else {
      ArrayList<User> usersFound = new ArrayList<>();
      for (User user: allUsers) {
        if(user.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
            user.getLastName().toLowerCase().contains(name.toLowerCase()) ||
            user.getUsername().toLowerCase().contains(name.toLowerCase()))
          usersFound.add(user);
      }
      return usersFound;
    }
  }

  public Iterable<User> findAll() {
    return userRepo.findAll();
  }

  public Optional<User> findById(Long id) {
    return userRepo.findById(id);
  }
}
