package tests;

import agents.Player1;
import agents.Player2;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class DiceGameLauncher {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 7700, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController ac1, ac2;
        try {
            ac1 = mc.createNewAgent("Player1", Player1.class.getName(), null);
            ac2 = mc.createNewAgent("Player2", Player2.class.getName(), null);
            ac1.start();
            ac2.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}