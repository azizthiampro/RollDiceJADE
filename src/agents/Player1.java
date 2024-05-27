package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import behaviours.RollDiceBehavior;

public class Player1 extends Agent {
    private int roundsPlayed = 0;
    private boolean gameOver = false;
    private final int maxAttempts = 50;

    protected void setup() {
        System.out.println(getLocalName() + " is ready.");
        // Send initial message to Player2 to start the game
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(getAID("Player2"));
        msg.setContent("start");
        send(msg);

        addBehaviour(new WaitForStartConfirmation());
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void updateState(int roundsPlayed, boolean gameOver) {
        this.roundsPlayed = roundsPlayed;
        this.gameOver = gameOver;
    }

    private class WaitForStartConfirmation extends Behaviour {
        private boolean startConfirmed = false;

        public void action() {
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            if (msg != null && msg.getContent().equals("start-confirm")) {
                startConfirmed = true;
                addBehaviour(new RollDiceBehavior(Player1.this, roundsPlayed, gameOver));
            } else {
                block();
            }
        }

        public boolean done() {
            return startConfirmed;
        }
    }
}
