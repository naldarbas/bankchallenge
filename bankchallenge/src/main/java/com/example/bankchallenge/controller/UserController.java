package com.example.bankchallenge.controller;

import com.example.bankchallenge.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static java.lang.Double.parseDouble;

import java.util.ArrayList;

@RestController
public class UserController {

    private ArrayList<User> users = new ArrayList<>();
    double balanceBank = 4000;

    @GetMapping(path = "/users")
    public ArrayList<User> getUser() {
        return users;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<String> addUser(@RequestBody User user) {

        if (user.getId() == null || user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            return ResponseEntity.status(400).body("Enter all the field");
        }
        if (user.getPassword().length() < 6) {
            return ResponseEntity.status(400).body(" please Change your password To More than 6 number ");
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                return ResponseEntity.status(400).body("User id already exists ");
            }
        }
        users.add(user);
        return ResponseEntity.status(200).body("User Added");

    }

    @PostMapping("/deposit/{id}")
    private ResponseEntity deposit(@PathVariable String id, @RequestBody String balance) {

        for (int i = 0; i < users.size(); i++) {

            if (id.equals(users.get(i).getId())) {

                users.get(i).setBalance(parseDouble(balance) + users.get(i).getBalance());
                return ResponseEntity.status(200).body("deposit successfully");
            }
        }
        return ResponseEntity.status(400).body("user not found");
    }

    @PostMapping("/withdraw/{id}")
    private ResponseEntity withdraw(@PathVariable String id, @RequestBody String balance) {

        for (int i = 0; i < users.size(); i++) {

            if (id.equals(users.get(i).getId())) {
                users.get(i).setBalance(users.get(i).getBalance() - parseDouble(balance));
                return ResponseEntity.status(200).body("withdraw successfully");
            }
        }
        return ResponseEntity.status(400).body("user not found");
    }

    @PostMapping("/loanAmount/{id}")
    private ResponseEntity loanBank(@PathVariable String id, @RequestBody String loanAmount) {

        for (int i = 0; i < users.size(); i++) {
            User u=users.get(i);

            if (id.equals(users.get(i).getId()) && parseDouble(loanAmount) < balanceBank) {
                balanceBank = balanceBank - parseDouble(loanAmount);
                double oldLoan=u.getLoanAmount() ;
                u.setLoanAmount(u.getLoanAmount()+oldLoan);
                users.get(i).setLoanAmount(parseDouble(loanAmount) + users.get(i).getLoanAmount());
                return ResponseEntity.status(200).body("you can loan ");
            }
            return ResponseEntity.status(400).body("Either the loan balance is more than allowed to loan or the id not found");
        }
        return ResponseEntity.status(400).body("user not found");
    }

    @DeleteMapping(path = "users/{id}")
    public ResponseEntity<String> deleteUser(User user, @PathVariable("id") String id) {

        for (int i = 0; i < users.size(); i++) {

                if (id.equals(users.get(i).getId())) {
                    if(users.get(i).getLoanAmount()>0){
                        return ResponseEntity.status(400).body("you han loan");
                    }
                    users.remove(i);
                    break;
                }
            }
            return ResponseEntity.status(200).body("User NotFound or you have loan");
        }
    }
