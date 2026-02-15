package com.fase4.fiap.bdd.coletaEncomenda;

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

public class CreateColetaEncomendaBDD {

    private String apartamentoId;
    private String recebimentoId;
    private String coletaEncomendaJson;
    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que foi criado um coletaEncomenda cadastrado - Criar")
    public void que_foi_criado_um_coleta_encomenda_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "D",
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

    @E("que foi construÃ­do um apartamento com recebimento para coletaEncomenda - Criar")
    public void que_foi_construido_um_apartamento_com_recebimento_encomenda_para_coleta_encomenda() {
        String recebimentoJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);

        Response responserecebimento = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoJson)
                .post("/api/recebimento-encomendas");

        responserecebimento.then().statusCode(HttpStatus.CREATED.value());
        recebimentoId = responserecebimento.jsonPath().getString("id");

    }

    @E("que foi construÃ­do coletaEncomenda - Criar")
    public void que_foi_construido_um_coleta_encomenda() {
        coletaEncomendaJson = String.format("""
                {
                    "recebimentoId": "%s",
                    "cpfMoradorColeta": "12345678919",
                    "nomeMoradorColeta": "Teste",
                    "dataColeta": "%s"
                }
                """, recebimentoId, dataEntrega);
    }

    @Quando("o coletaEncomenda for cadastrado - Criar")
    public void o_coleta_encomenda_for_cadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(coletaEncomendaJson)
                .when()
                .post("/api/coleta-encomendas");
    }

    @Então("o coletaEncomenda será salvo no sistema - Criar")
    public void o_coleta_encomenda_sera_salvo_no_sistema() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @E("o coletaEncomenda deve estar no formato esperado - Criar")
    public void o_coleta_encomenda_deve_estar_no_formato_esperado() {
        response.then().body(matchesJsonSchemaInClasspath("schemas/coleta-encomenda.schema.json"));
    }

}
