package kz.Aseke.Again.controller;


import kz.Aseke.Again.beans.TestA;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping(value = "test-a")
    public String testA(){
        TestA testA = new TestA();
        return "test ";
    }
}
