package com.fase4.fiap.bdd.recebimento;

import java.time.OffsetDateTime;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class GetRecebimentoBDD {

    private Response response;
    private String recebimentoId;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que existe um recebimento cadastrado - Get")
    public void que_existe_um_CONFIRMACAO_DE_RECEBIMENTO_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "N",
                    "andar": "20",
                    "numero": "1"
                }
                """;
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
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);

        recebimentoId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoJson)
                .post("/api/recebimento-encomendas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("um recebimento for buscado pelo ID - Get")
    public void um_CONFIRMACAO_DE_RECEBIMENTO_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/recebimento-encomendas/" + recebimentoId);
    }

    @Então("o recebimento será retornado no sistema - Get")
    public void o_CONFIRMACAO_DE_RECEBIMENTO_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}


