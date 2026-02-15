package com.fase4.fiap.bdd.coletaEncomenda;

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

public class GetColetaEncomendaBDD {

    private Response response;
    private String coletaEncomendaId;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que existe um coletaEncomendaId cadastrado - Get")
    public void que_existe_um_coleta_encomenda_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "E",
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

        String recebimentoId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoJson)
                .post("/api/recebimento-encomendas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        String coletaEncomendaJson = String.format("""
                {
                    "recebimentoId": "%s",
                    "cpfMoradorColeta": "12345678919",
                    "nomeMoradorColeta": "Teste",
                    "dataColeta": "%s"
                }
                """, recebimentoId, dataEntrega);

        coletaEncomendaId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(coletaEncomendaJson)
                .post("/api/coleta-encomendas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("um coletaEncomendaId for buscado pelo ID - Get")
    public void um_coleta_encomenda_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/coleta-encomendas/" + coletaEncomendaId);
    }

    @Então("o coletaEncomendaId será retornado no sistema - Get")
    public void o_coleta_encomenda_id_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}

