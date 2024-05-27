package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Player2 extends Agent {
    private boolean gameOver = false;

    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        addBehaviour(new WaitForMessages());
    }

    private class WaitForMessages extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String content = msg.getContent();
                if (content.equals("start")) {
                    System.out.println(getLocalName() + " received start message from Player1.");
                    sendStartConfirmation();
                } else if (content.equals("win")) {
                    System.out.println(getLocalName() + " received win message from Player1. Player1 wins!");
                    gameOver = true; // Mark the game as over
                } else if (content.equals("lose")) {
                    System.out.println(getLocalName() + " received lose message from Player1. Player2 wins!");
                    gameOver = true; // Mark the game as over
                }
            } else {
                block();
            }
        }

        private void sendStartConfirmation() {
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(getAID("Player1"));
            reply.setContent("start-confirm");
            send(reply);
        }
    }
}
