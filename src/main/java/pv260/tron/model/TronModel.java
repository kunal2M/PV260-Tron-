package pv260.tron.model;


import pv260.tron.Player;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class TronModel {

    private List<Player> players = new ArrayList<>();

    private PublishSubject<Event> eventSubject = PublishSubject.create();

    public TronModel() {
        Player player1 = initPlayer1();
        Player player2 = initPlayer2();
        Player player3 = initPlayer3();

        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    public void update(long timePassed, int width, int height) {
        players.forEach(player -> player.move(width, height));
        detectCollisions();
    }

    private void detectCollisions() {
        players.forEach(player1 -> {
            if (player1.isAlive()) {
                players.forEach(player2 -> {
                    player2.getPath().forEach(pathPosition -> {
                        if (player1.getPosition().equals(pathPosition)) {
                            player1.collide();
                        }
                    });
                });
            }
        });
        if (players.stream().filter(Player::isAlive).count() <= 1) {
            eventSubject.onNext(Event.GAME_ENDED);
        }
    }

    public void inputEvent(int event) {
        players.forEach(player -> player.changeDirection(event));
    }

    public Observable<Event> getEventsObservable() {
        return eventSubject;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private Player initPlayer1() {
        Point position = new Point(40, 40);

        Player.MoveAction moveAction = (event, currentDirection) -> {
            Player.Direction newDirection = currentDirection;
            switch (event) {
                case KeyEvent.VK_W:
                    newDirection = Player.Direction.UP;
                    break;
                case KeyEvent.VK_A:
                    newDirection = Player.Direction.LEFT;
                    break;
                case KeyEvent.VK_D:
                    newDirection = Player.Direction.RIGHT;
                    break;
                case KeyEvent.VK_S:
                    newDirection = Player.Direction.DOWN;
                    break;
                default:
            }
            if (currentDirection.getOpposite() != newDirection) {
                return newDirection;
            }
            return currentDirection;
        };

        return new Player(
                position,
                Player.Direction.RIGHT,
                Color.green,
                moveAction
        );
    }

    private Player initPlayer2() {
        Point position = new Point(600, 440);
     //   Map<Integer, Player.Direction> movement = new HashMap<>();

        Player.MoveAction moveAction = (event, currentDirection) -> {
            Player.Direction newDirection = currentDirection;
            switch (event) {
                case KeyEvent.VK_UP:
                    newDirection = Player.Direction.UP;
                    break;
                case KeyEvent.VK_LEFT:
                    newDirection = Player.Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    newDirection = Player.Direction.RIGHT;
                    break;
                case KeyEvent.VK_DOWN:
                    newDirection = Player.Direction.DOWN;
                    break;
                default:
            }
            if (currentDirection.getOpposite() != newDirection) {
                return newDirection;
            }
            return currentDirection;
        };

        return new Player(
                position,
                Player.Direction.LEFT,
                Color.red,
                moveAction
        );
    }

    private Player initPlayer3() {
        Point position = new Point(300, 300);
        Player.MoveAction moveAction = new Player.MoveAction() {
            @Override
            public Player.Direction move(int event, Player.Direction currentDirection) {
                return getDirectionOnClick(event, currentDirection);
            }
        };

        return new Player(
                position,
                Player.Direction.DOWN,
                Color.blue,
                moveAction
        );
    }

    private Player.Direction getDirectionOnClick(int event, Player.Direction currentDirection) {
        if (event == InputEvent.BUTTON1_DOWN_MASK) {
            return currentDirection.left();
        } else if (event == InputEvent.BUTTON3_DOWN_MASK) {
            return currentDirection.right();
        }
        return currentDirection;
    }


    public enum Event {
        GAME_ENDED
    }
}
