package StepDef.BE;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.io.*;
import java.util.Random;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;


@Component
public class MainBaseMethod {
    private static JSONParser parser;
    private static Reader reader;
    private JSONObject readJson;
    private JSONArray readJsonArray;
    protected Response response;
    private String writeJson;


    public Response getResponse(){
        return response;
    }



    /**
     * http request query JSON path
     *
     * @param path query JSON path
     * @return returns httpRequest
     */
    protected JsonPath httpGetPath(String path) {
        return RestAssured.given()
                .when()
                .get(path)
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .extract().body().jsonPath();

    }

    /**
     * http request with parameter
     *
     * @param contentType body content type
     * @return returns httpRequest
     */
    private RequestSpecification httpRequestPost(String request, String contentType){
        return RestAssured
                .given()
                .with()
                .contentType(contentType)
                .with()
                .body(request);
    }

    private RequestSpecification httpRequestPost(Gson JsonRequest, String contentType) throws IOException, ParseException {
        return RestAssured
                .given()
                .with()
                .contentType(contentType)
                .with()
                .body(JsonRequest);
    }

    /**
     * http post request with parameter and bearer token
     *
     * @param contentType body content type
     * @return returns httpRequest
     */
    private RequestSpecification httpRequestPost(String request, String contentType, String accessToken) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .with()
                .contentType(contentType)
                .with()
                .body(request);
    }

    /**
     * http post request with parameter and bearer token
     *
     * @param contentType body content type
     * @return returns httpRequest
     */
    private RequestSpecification httpRequestPostArray(String request, String contentType, String accessToken) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .with()
                .contentType(contentType)
                .with()
                .body(request);
    }

    /**
     * http post request with parameter and bearer token
     *
     * @param contentType body content type
     * @return returns httpRequest
     */
    private RequestSpecification httpRequestPostArray( String contentType, String accessToken) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .with()
                .contentType(contentType);
    }

    /**
     * http request with parameter
     *
     * @return returns httpRequest
     */
    private RequestSpecification httpRequest() {
        return RestAssured
                .given()
                .with()
                .contentType(ContentType.JSON);
    }

    /**
     * http request with parameter and bearer token
     *
     * @param accessToken access token
     * @return returns httpRequest
     */
    private RequestSpecification httpRequest(String accessToken) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .with()
                .contentType(ContentType.JSON);
    }

    /**
     * http request with parameter and bearer token
     *
     * @param accessToken access token
     * @return returns httpRequest
     */
    private RequestSpecification httpRequest(String accessToken, String param, String keyword) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .with()
                .contentType(ContentType.JSON)
                .with()
                .param(param,keyword);
    }


    /**
     * Set base uri
     *
     * @param baseURI baseUri
     */
    public void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    /**
     * Set base path
     *
     * @param basePathTerm basepath
     */
    protected void setBasePath(String basePathTerm) {
        RestAssured.basePath = basePathTerm;
    }

    /**
     * reset base uri
     */
    protected void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    /**
     * Reset base path
     */
    protected void resetBasePath() {
        RestAssured.basePath = null;
    }

    /**
     * http post
     *
     * @param contentType body content type
     * @param path   endpoint
     * @return response
     */
    public void httpPost(String path, String request, String contentType ) {
        response = httpRequestPost(request,contentType).request(Method.POST, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: POST");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("Request body: ");
        System.out.println("\n"+prettyPrint(request));
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }


    /**
     * http post with access token
     *
     * @param contentType body content type
     * @param path   endpoint
     * @return response
     */
    public void httpPost(String path, String request, String contentType, String accessToken) {
        response = httpRequestPost(request,contentType,accessToken).request(Method.POST, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: POST");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("Request body: ");
        System.out.println("\n"+prettyPrint(request));
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }

    /**
     * http get
     *
     * @param path endpoint
     * @return response
     */
    protected void httpGet(String path) {
        response = httpRequest().request(Method.GET, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: GET");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("-----------------------------------------------");
        System.out.println("RESPONSE INFO");
    }

    /**
     * http get with access token
     *
     * @param path endpoint
     * @return response
     */
    public void httpGet(String path, String accessToken) {
        response = httpRequest(accessToken).request(Method.GET, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: GET");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("-----------------------------------------------");
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }

    /**
     * http get with access token
     *
     * @param path endpoint
     * @param accessToken access token
     * @param param query parameter
     * @return response
     */
    protected void httpGet(String path, String accessToken, String param, String keyword) {
        response = httpRequest(accessToken,param,keyword).request(Method.GET, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: GET");
        System.out.println("URI: " + RestAssured.baseURI + path + "?" + param + "=" + keyword);
        System.out.println("-----------------------------------------------");
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }

    /**
     * http get with headers
     *
     * @param path    endpoint
     * @param headers headers
     * @return response
     */
    protected Response httpGet(String path, Header headers) {
        return httpRequest()
                .with()
                .header(headers)
                .request(Method.GET, path);
    }



    /**
     * http delete
     *
     * @param path endpoint
     * @return response
     */
    protected Response httpDelete(String path) {
        return httpRequest().request(Method.DELETE, path);
    }

    /**
     * http put
     *
     * @param contentType body content type
     * @param path endpoint
     */
    public void httpPut(String path, String request, String contentType, String accessToken) {
        response = httpRequestPost(request,contentType,accessToken).request(Method.PUT, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: PUT");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("Request body: ");
        System.out.println("\n"+prettyPrint(request));
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }

    protected void httpDelete(String path, String request, String contentType, String accessToken) {
        response = httpRequestPost(request,contentType,accessToken).request(Method.DELETE, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: DELETE");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("Request body: ");
        System.out.println("\n"+prettyPrint(request));
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }


    /**
     * http put with array test body
     *
     * @param contentType body content type
     * @param path endpoint
     */
    public void httpPut(String path, String contentType, String accessToken){
        response = httpRequestPostArray(contentType,accessToken).request(Method.PUT, path);
        System.out.println("REQUEST INFO");
        System.out.println("Request type: PUT");
        System.out.println("URI: " + RestAssured.baseURI + path);
        System.out.println("RESPONSE INFO");
        System.out.println("\n"+response.getBody().asPrettyString());
    }

    /**
     * http patch
     *
     * @param JSONPath body test data file location
     * @param contentType body content type
     * @param path   endpoint
     * @return response
     */
    protected Response httpPatch(String JSONPath, String contentType, String path) throws IOException, ParseException {
        return httpRequestPost(JSONPath,contentType).request(Method.PATCH, path);
    }

    /**
     * Get Status code
     *
     * @param expectedStatusCode expected status code response
     * @return status code
     */

    public MainBaseMethod assertStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(),expectedStatusCode);
        if(response.getStatusCode()==expectedStatusCode) System.out.println("Status code is: " + response.getStatusCode() + " (PASS)");
        else System.out.println("Status code is: " + response.getStatusCode() + "(FAIL)");
        return this;
    }

    /**
     * check response body
     *
     * @param stringContain expected string contained in body
     * @return status code
     */


    protected void assertBodyContain(String stringContain) {
        Assert.assertTrue(response.getBody().asString().contains(stringContain),"fail");
        if(response.getBody().asString().contains(stringContain)) System.out.println("Response body contains " + stringContain + " (PASS)");
        else System.out.println("Response body doesn't contain " + stringContain + " (FAIL)");
    }
    /**
     * Get Content Type
     *
     * @param contentType response content type
     * @return contentType
     */
    public void assertContentType(String contentType) {
        Assert.assertEquals(response.getContentType(), contentType);
        if(response.getContentType().equals(contentType))  System.out.println("Content type is: "+ response.getContentType() + " (PASS)");
        else System.out.println("Content type is: "+ response.getContentType() + " (FAIL)");
    }

    /**
     * Get headers
     *
     * @param response response
     * @param header   header
     * @return header value
     */
    private String getHeaders(Response response, String header) {
        return response.getHeaders().getValue(header);
    }

    /**
     * Response Body
     *
     * @param response response
     * @return responseBody
     */
    private ResponseBody responseBody(Response response) {
        return response.getBody();
    }

    /**
     * Get Body
     *
     * @param response response
     * @return preety Print
     */
    protected String getBody(Response response) {
        return responseBody(response).prettyPrint();
    }

    /**
     * JsonPath evaluator
     *
     * @param response response
     * @return jsonPath
     */
    protected Object jsonPathEvaluator(Response response, String exp) {
        return response.jsonPath().get(exp);
    }

    /**
     * Read JSON file
     *
     * @param fileLocation JSON file location
     * @return jsonObject
     */
    public static JSONObject readJSONObject(String fileLocation) throws IOException, ParseException {
        parser = new JSONParser();
        reader = new FileReader(fileLocation);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        return jsonObject;
    }

    protected static JSONArray readJSONArray(String fileLocation) throws IOException, ParseException {
        parser = new JSONParser();
        reader = new FileReader(fileLocation);
        JSONArray jsonArray = (JSONArray) parser.parse(reader);
        return jsonArray;
    }





    protected ResponseBody getResponseBody(){
            return  response.getBody();
    }



    protected void writeJsonArray (String fileLocation, JSONArray jsonData){
        try(FileWriter file = new FileWriter(fileLocation)){
            file.write(prettyPrint(jsonData.toJSONString()));
        } catch(IOException e){
            e.printStackTrace();
        }
    }




    /**
     * Validate JSON schema
     *
     * @param responseBodyLocation location of response body file
     * @param responseSchemaLocation location of JSON schema file
     */
    protected void jsonSchemaCheck(String responseBodyLocation, String responseSchemaLocation) throws IOException, ParseException {
        try{
            assertThat(readJSONObject(responseBodyLocation).toJSONString(), matchesJsonSchemaInClasspath(responseSchemaLocation));
        }catch (Exception e){
            throw e;
        }
        System.out.println("Response body schema is correct");
    }

    protected void jsonSchemaCheck(JSONObject responseBody, String responseSchemaLocation) throws IOException, ParseException {
        try{
            assertThat(responseBody.toJSONString(), matchesJsonSchemaInClasspath(responseSchemaLocation));
        }catch (Exception e){
            throw e;
        }
        System.out.println("Response body schema is correct");
    }

    protected void jsonSchemaCheck(ResponseBody responseBody, String responseSchemaLocation) throws IOException, ParseException {
        try{
            assertThat(responseBody.asString(), matchesJsonSchemaInClasspath(responseSchemaLocation));
        }catch (Exception e){
            throw e;
        }
        System.out.println("Response body schema is correct");
    }

    protected void jsonSchemaCheck(String responseSchemaLocation) {
        try{
            assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath(responseSchemaLocation));
            System.out.println("Response body schema is correct (PASS)");
        }catch (Exception e){
            System.out.println("Response body schema is wrong (FAIL)");
            throw e;
        }
    }

    protected String prettyPrint(String uglyString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyString);
        return gson.toJson(je);
    }

    public static int random(int i){
        Random rand = new Random();
        return rand.nextInt(i);
    }





}
