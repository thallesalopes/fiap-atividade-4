package com.fase4.fiap.bdd.recebimento;

import java.time.OffsetDateTime;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class CreateRecebimentoBDD {

    private String apartamentoId;
    private String recebimentoJson;
    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que foi criado um recebimento cadastrado - Criar")
    public void que_foi_criado_um_CONFIRMACAO_DE_RECEBIMENTO_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "M",
                    "andar": "20",
                    "numero": "1"
                }
                """;

        Response responseApartamento = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(apartamentoJson)
                .post("/api/apartamentos");

        responseApartamento.then().statusCode(HttpStatus.CREATED.value());
        apartamentoId = responseApartamento.jsonPath().getString("id");
    }

    @E("que foi construÃ­do um apartamento com recebimento - Criar")
    public void que_foi_construido_um_CONFIRMACAO_DE_RECEBIMENTO_com_CONFIRMACAO_DE_RECEBIMENTO() {
        recebimentoJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);
    }

    @Quando("o recebimento for cadastrado - Criar")
    public void o_CONFIRMACAO_DE_RECEBIMENTO_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoJson)
                .when()
                .post("/api/recebimento-encomendas");
    }

    @Então("o recebimento será salvo no sistema - Criar")
    public void o_CONFIRMACAO_DE_RECEBIMENTO_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o recebimento deve estar no formato esperado - Criar")
    public void o_CONFIRMACAO_DE_RECEBIMENTO_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/recebimento-encomenda.schema.json"));
    }

}


