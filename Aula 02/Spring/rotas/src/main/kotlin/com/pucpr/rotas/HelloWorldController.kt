package com.pucpr.rotas

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@Controller
class HelloWorldController {
    @GetMapping("/hello")
    fun hello(): String = "hello";

    @GetMapping("/helloname")
    fun helloName(@RequestParam(name="name",
        required=false, defaultValue="World") name : String,
          @RequestParam(name="city",
          required=false, defaultValue="ny") city : String
    ) : String {
        return "hello: $name from $city";
    }
    @GetMapping("/redirect")
    fun helloName() : RedirectView {
        return RedirectView("/hello");
    }

    @RequestMapping("/html")
    fun byeText(model: Model): String{
        model.addAttribute("name", "Mark")
        return "bye"
    }

    @RequestMapping("/html2")
    fun helloText(model: Model): String{
        model.addAttribute("name",
            "Hello Mark")
        return "hello"
    }


    @GetMapping("/var/{text}")
    @ResponseBody
    fun helloText(@PathVariable text: String): String{
        return "Text:$text"
    }

    class Customer(
        var name : String? = null,
        var age :Int? = null,
        var address: String? = null
    ){}

    @GetMapping("/form")
    fun customerForm(model: Model): String{
        model.addAttribute("customer", Customer())
        return "form"
    }
    private val log = LoggerFactory.getLogger(HelloWorldController::class.java)
    
    @PostMapping("/form")
    fun customerSubmit(@ModelAttribute("customer") customer: Customer, model: Model): String{
        model.addAttribute("customer", customer)
        val info = String.format("Customer Submission: name = %s, age = %d, address = %s",
            customer.name, customer.age, customer.address,)
        log.debug(info)
        return "formResult"
    }


}