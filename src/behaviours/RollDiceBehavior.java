package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import agents.Player1;

public class RollDiceBehavior extends OneShotBehaviour {
    private Player1 playerAgent;
    private int roundsPlayed;
    private boolean gameOver;

    public RollDiceBehavior(Player1 agent, int roundsPlayed, boolean gameOver) {
        super(agent);
        this.playerAgent = agent;
        this.roundsPlayed = roundsPlayed;
        this.gameOver = gameOver;
    }

    public void action() {
        if (!gameOver && roundsPlayed < playerAgent.getMaxAttempts()) {
            roundsPlayed++;
            int die1 = rollDie();
            int die2 = rollDie();
            System.out.println(playerAgent.getLocalName() + " rolled: " + die1 + " and " + die2);

            if (die1 == 6 && die2 == 6) {
                System.out.println(playerAgent.getLocalName() + " wins after " + roundsPlayed + " rounds!");
                gameOver = true;
                sendGameOverMessage("win");
            } else if (roundsPlayed >= playerAgent.getMaxAttempts()) {
                System.out.println(playerAgent.getLocalName() + " loses after " + roundsPlayed + " rounds.");
                gameOver = true;
                sendGameOverMessage("lose");
            } else {
                System.out.println("Continue playing...");
                playerAgent.updateState(roundsPlayed, gameOver);
                playerAgent.addBehaviour(new RollDiceBehavior(playerAgent, roundsPlayed, gameOver));
            }
        }
    }

    private int rollDie() {
        return (int) (Math.random() * 6) + 1;
    }

    private void sendGameOverMessage(String result) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(playerAgent.getAID("Player2"));
        msg.setContent(result);
        playerAgent.send(msg);
    }
}
