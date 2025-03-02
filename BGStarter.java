public class BGStarter {
    public static void main(String[] args) {
        Player p1 = new Player();
        Player p2 = new Player();
        Inspector inspector = new BGLogic();
        new BGAssistant().BG(p1, p2, new BGFrame(inspector));
    }
    
}