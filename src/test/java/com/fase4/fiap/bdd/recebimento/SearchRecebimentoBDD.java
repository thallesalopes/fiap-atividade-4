package com.fase4.fiap.bdd.recebimento;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class SearchRecebimentoBDD {

    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que existem vÃ¡rios recebimento cadastrados - Search")
    public void que_existem_varios_CONFIRMACAO_DE_RECEBIMENTO_cadastrados() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        for (int i = 1; i <= 2; i++) {
            String apartamentoJson = String.format("""
                    {
                        "torre": "P",
                        "andar": "20",
                        "numero": "1%d"
                    }
                    """, i);

            String apartamentoId = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(apartamentoJson)
                    .post("/api/apartamentos")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");

            String recebimentoJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Test%d",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, i, dataEntrega);

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(recebimentoJson)
                    .post("/api/recebimento-encomendas")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }

    @Quando("os recebimento forem buscados - Search")
    public void os_CONFIRMACAO_DE_RECEBIMENTO_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/recebimento-encomendas");
    }

    @Então("os recebimento serão retornados no sistema - Search")
    public void os_CONFIRMACAO_DE_RECEBIMENTO_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}


