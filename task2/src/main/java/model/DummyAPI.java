package model;

public class DummyAPI {
    public boolean isAvailable() {
        return true;
    }

    public String signUp(){
        return "DummyToken";
    }

    public boolean logIn(String token){
        return true;
    }

    public String getUserInfo(){
        return "username: dummy\n" +
                "credits: 100\n" +
                "ships:[]";
    }

    public String listAvailableLoans(){
        return "loans:\n" +
                "  - type: startup\n" +
                "    amount: 100\n" +
                "    repayment: 200\n" +
                "    due: 2022-4-10\n";
    }

    public String obtainLoan(){
        return "loan:\n" +
                "  type: startup\n" +
                "  amount: 100\n" +
                "  repayment: 200\n" +
                "  due: 2022-4-10\n";
    }

    public String listActiveLoan(){
        return "loan:\n" +
                "  type: startup\n" +
                "  amount: 100\n" +
                "  repayment: 200\n" +
                "  due: 2022-4-10\n" +
                "---------------------------------\n" +
                "loan: company\n" +
                "amount: 200\n" +
                "repayment: 250\n" +
                "due: 2022-4-10\n";
    }

    public String listAvailableShips(){
        return "ships:\n" +
                "  - name: MK-I\n" +
                "    speed: 100\n" +
                "    maxCargo: 100\n" +
                "    plating: 100\n";
    }

    public String purchaseShip(){
        return "ship:\n" +
                "  name: MK-I\n" +
                "  speed: 100\n" +
                "  maxCargo: 100\n" +
                "  plating: 100\n";
    }

    public String listMyShips(){
        return "ships:\n" +
                "  - name: MK-I\n" +
                "    speed: 100\n" +
                "    maxCargo: 100\n" +
                "    plating: 100\n" +
                "  - name: MK-II\n" +
                "    speed: 50\n" +
                "    maxCargo: 100\n" +
                "    plating: 20\n";
    }

    public String purchaseFUEL(){
        return "added fuel";
    }

    public String listMarketplace(String location){
        return "marketplace:\n" +
                "  - name: FUEL\n" +
                "    price: 100\n" +
                "    amount: 100\n" +
                "  - name: METAL\n" +
                "    price: 100\n" +
                "    amount: 100\n";

    }

    public String purchaseGoods(String goods){
        return "bought " + goods;
    }

    public String listNearbyLocations(){
        return "locations:\n" +
                "  - name: A\n" +
                "    distance: 100\n" +
                "  - name: B\n" +
                "    distance: 200\n";
    }

    public String createFlightPlan(String location){
        return "added a flight plan to " + location;
    }

    public String viewMyFlightPlan(){
        return "flight plan:\n" +
                "  departure: A\n" +
                "  destination: B\n";
    }

    public String sellGoods(String goods){
        return "sold " + goods;
    }
}
