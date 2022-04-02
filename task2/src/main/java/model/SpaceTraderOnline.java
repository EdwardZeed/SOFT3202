package model;

import com.google.gson.*;
import controller.Controller;
import controller.SearchPageController;
import javafx.scene.Scene;
import model.ConcretClasses.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class SpaceTraderOnline {

    public static boolean isOnline() {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/game/status"))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);
            String status = body.get("status").getAsString();

            if (status.equals("spacetraders is currently online and available to play")) {
                return true;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User signUp(String username, Controller controller){
        try{
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/users/"+username+"/claim"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            String token = body.get("token").getAsString();
            User user = gson.fromJson(body.get("user"), User.class);
            user.setToken(token);

            return user;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User loginToken(String token, Controller controller){
        try{
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/account?token=" + token))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            String check = response.body().split("\"")[1].trim();
//
//            if (check.equals("error")){
//                return null;
//            }

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);
            JsonObject userInfo = body.getAsJsonObject("user");


            User user = gson.fromJson(userInfo, User.class);
            if (user == null){
                controller.setErrMessage("Invalid token");
            }
            user.setToken(token);

            return user;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Loan> listLoans(User user, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/types/loans?token=" + user.getToken()))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ArrayList<Loan> loans = new ArrayList<>();
            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonArray loanInfo = body.getAsJsonArray("loans");
            for (JsonElement loan : loanInfo){
                loans.add(gson.fromJson(loan.getAsJsonObject(), Loan.class));
            }

            return loans;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Loan obtainLoan(User user, String type, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/loans?token=" + user.getToken() + "&type=" + type))
                    .POST(HttpRequest.BodyPublishers.ofString("obtaining loan"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonObject loanInfo = body.getAsJsonObject("loan");
            Loan loan = gson.fromJson(loanInfo, Loan.class);

            int credits = body.get("credits").getAsInt();
            user.setCredits(credits);

            return loan;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Ship> getAvailableShips(User user, String type, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/types/ships?token=" + user.getToken() + "&class=" + type))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ArrayList<Ship> ships = new ArrayList<>();
            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonArray shipInfo = body.getAsJsonArray("ships");
            for (JsonElement ship : shipInfo){
//                ArrayList<Location> locations = new ArrayList<>();
//                JsonArray locationInfo = ship.getAsJsonObject().getAsJsonArray("purchaseLocations");
//                for (JsonElement location : locationInfo){
//                    locations.add(gson.fromJson(location.getAsJsonObject(), Location.class));
//                }

                Ship cursor = gson.fromJson(ship.getAsJsonObject(), Ship.class);
                cursor.setClassName(ship.getAsJsonObject().get("class").getAsString());
                ships.add(cursor);
            }

            return ships;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Ship purchaseShip(User user, String location, String type, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/ships?token=" + user.getToken() + "&location=" + location + "&type=" + type))
                    .POST(HttpRequest.BodyPublishers.ofString("purchasing ship"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonObject shipInfo = body.getAsJsonObject("ship");
            Ship ship = gson.fromJson(shipInfo, Ship.class);
            ship.setClassName(shipInfo.get("class").getAsString());

            int credits = body.get("credits").getAsInt();
            user.setCredits(credits);
            user.addShip(ship);

            return ship;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Order purchaseOrder(User user, String shipId, String good, int quantity, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/purchase-orders?token=" + user.getToken() + "&shipId=" + shipId + "&good=" + good + "&quantity=" + quantity))
                    .POST(HttpRequest.BodyPublishers.ofString("purchasing order"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            Order order = gson.fromJson(body.getAsJsonObject("order"), Order.class);

            user.setCredits(body.get("credits").getAsInt());
            user.setOrder(order);

            return order;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<Ship> listUserShips(User user){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/ships?token=" + user.getToken()))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);
            JsonArray ships = body.getAsJsonArray("ships");

            ArrayList<Ship> shipList = new ArrayList<>();
            for(JsonElement ship : ships) {
                Ship cursor = gson.fromJson(ship.getAsJsonObject(), Ship.class);
                cursor.setClassName(ship.getAsJsonObject().get("class").getAsString());
                shipList.add(cursor);
            }
            user.setShips(shipList);

            return shipList;


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Goods> listMarketplace(User user, String location, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/locations/"+ location +"/marketplace?token=" + user.getToken()))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonArray goods = body.getAsJsonArray("marketplace");
            ArrayList<Goods> goodList = new ArrayList<>();
            for(JsonElement good : goods) {
                Goods cursor = gson.fromJson(good.getAsJsonObject(), Goods.class);
                goodList.add(cursor);
            }

            return goodList;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Location> listNearbyLocations(User user, String type, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/systems/OE/locations?token=" + user.getToken() + "&type=" + type))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            JsonArray locations = body.getAsJsonArray("locations");

            ArrayList<Location> locationList = new ArrayList<>();
            for(JsonElement location : locations) {
                Location cursor = gson.fromJson(location.getAsJsonObject(), Location.class);
                cursor.setLocation(location.getAsJsonObject().get("symbol").getAsString());
                locationList.add(cursor);
            }

            return locationList;

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FlightPlan submitFlightPlan(User user, String shipId, String destination, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/flight-plans?token=" + user.getToken() + "&shipId=" + shipId+ "&destination=" + destination))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            FlightPlan flightPlan = gson.fromJson(body.getAsJsonObject("flightPlan"), FlightPlan.class);

            ArrayList<Ship> ships = listUserShips(user);
            user.setShips(ships);
            user.getShip(shipId).setFlightPlan(flightPlan);

            return flightPlan;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Order sellGoods(User user, String shipId, String goods, int quantity, Controller controller){
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/sell-orders?token=" + user.getToken() + "&shipId=" + shipId + "&good=" + goods + "&quantity=" + quantity + "&type=sell"))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject body = gson.fromJson(response.body(), JsonObject.class);

            if (checkErr(body, controller)){
                return null;
            }

            Order order = gson.fromJson(body.getAsJsonObject("order"), Order.class);

            user.setCredits(body.get("credits").getAsInt());

            return order;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkErr(JsonObject body, Controller controller){
        if(body.get("error") != null){
            controller.setErrMessage(body.get("error").getAsJsonObject().get("message").getAsString());
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        User user =new User("edwardhimself", 20000, "2020-2-01", 0, 1);
        user.setToken("5cae2057-bbbf-43be-9e47-6d9d804a831a");
        obtainLoan(user, "5cae2057-bbbf-43be-9e47-6d9d804a831a", new SearchPageController());
//        purchaseOrder(user, "cl1g0xo7a153969215s6ucsde6r8", "FUEL", 2);
//        Order order = sellGoods(user, "cl1g0xo7a153969215s6ucsde6r8", "FUEL", 1);
//        System.out.println(order);
    }


}
