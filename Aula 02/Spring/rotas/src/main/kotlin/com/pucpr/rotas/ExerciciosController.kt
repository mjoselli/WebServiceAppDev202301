package com.pucpr.rotas

import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@RestController
class ExerciciosController {
    /**
    Faça um form/serviço onde o usuário possa colocar duas datas (data inicio e data fim) e o servidor retorne;
    A diferença de dias entre as datas;
    A diferença de semana entre as datas;
    A diferença de messes entre as datas;
     */
    @GetMapping("/exercicio1")
    fun exercicio1(@RequestParam(name="dataInicio", required=true) dataInicio : String,
                   @RequestParam(name="dataFim", required=true) dataFim : String
    ) : String {
        val dataInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("ddMMyyyy"))
        val dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("ddMMyyyy"))
        val diferencaDias = ChronoUnit.DAYS.between(dataInicio, dataFim)
        val diferencaSemanas = ChronoUnit.WEEKS.between(dataInicio, dataFim)
        val diferencaMeses = ChronoUnit.MONTHS.between(dataInicio, dataFim)
        val json = JSONObject()
        json.put("diferencaDias", diferencaDias)
        json.put("diferencaSemanas", diferencaSemanas)
        json.put("diferencaMeses", diferencaMeses)

        return json.toString()
    }
    /**
     * Faça um form/serviço onde o usuário possa entrar com números separados por ';' e o servidor retorne;
     * Os números em ordem crescente;
     * Os números em ordem decrescente;
     * Os números pares;
     *
     */
    @GetMapping("/exercicio2")
    fun exercicio2(@RequestParam(name="numeros", required=true) numeros : String
    ) : String {
        val numeros = numeros.split(";").map { it.toInt() }
        val numerosCrescente = numeros.sorted()
        val numerosDecrescente = numeros.sortedDescending()
        val numerosPares = numeros.filter { it % 2 == 0 }
        val json = JSONObject()
        json.put("numerosCrescente", numerosCrescente)
        json.put("numerosDecrescente", numerosDecrescente)
        json.put("numerosPares", numerosPares)

        return json.toString()
    }
    /**
     * Crie um form/serviço que dado um texto transforme para mimimi
     */
    @GetMapping("/exercicio3")
    fun exercicio3(@RequestParam(name="texto", required=true) texto : String
    ) : String {
        val texto = texto.replace("[aeiou]".toRegex(), "i")
        val json = JSONObject()
        json.put("texto", texto)

        return json.toString()
    }
}