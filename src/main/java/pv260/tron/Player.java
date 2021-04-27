package pv260.tron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private static final int MOVE_AMOUNT = 5;

    private Point position;
    private  Direction currentDirection;

    private Color color;

    private MoveAction moveAction;

    private boolean alive = true;

    private List<Point> path = new ArrayList<>();

    public Player(Point position, Direction currentDirection, Color color, MoveAction moveAction) {
        this.position = position;
        this.currentDirection = currentDirection;
        this.color = color;
        this.moveAction = moveAction;
    }

    public void changeDirection(int event) {
        currentDirection = moveAction.move(event, currentDirection);
    }

    public void move(int width, int height) {
        if (!isAlive()) return;

        path.add((Point) position.clone());

        currentDirection.move(position, width, height);
    }

    public Point getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public List<Point> getPath() {
        return path;
    }

    public boolean isAlive() {
        return alive;
    }

    public void collide() {
        alive = false;
    }

    public enum Direction {
        LEFT {
            @Override
            public void move(Point point, int width, int height) {
                if (point.x > 0){
                    point.x -= MOVE_AMOUNT;
                } else {
                    point.x = width;
                }
            }

            @Override
            public Direction getOpposite() {
                return Direction.RIGHT;
            }

            @Override
            public Direction left() {
                return DOWN;
            }

            @Override
            public Direction right() {
                return UP;
            }
        },
        RIGHT {
            @Override
            public void move(Point point, int width, int height) {
                if (point.x < width){
                    point.x += MOVE_AMOUNT;
                } else {
                    point.x = 0;
                }
            }

            @Override
            public Direction getOpposite() {
                return Direction.LEFT;
            }

            @Override
            public Direction left() {
                return UP;
            }

            @Override
            public Direction right() {
                return DOWN;
            }
        },
        UP {
            @Override
            public void move(Point point, int width, int height) {
                if (point.y > 0){
                    point.y -= MOVE_AMOUNT;
                } else {
                    point.y = height;
                }
            }

            @Override
            public Direction getOpposite() {
                return Direction.DOWN;
            }
 
            @Override
            public Direction left() {
                return LEFT;
            }

            @Override
            public Direction right() {
                return RIGHT;
            }
        },
        DOWN {
            @Override
            public void move(Point point, int width, int height) {
                if (point.y < height){
                    point.y += MOVE_AMOUNT;
                } else {
                    point.y = 0;
                }
            }

            @Override
            public Direction getOpposite() {
                return Direction.UP;
            }

            @Override
            public Direction left() {
                return RIGHT;
            }

            @Override
            public Direction right() {
                return LEFT;
            }
        };

        public abstract void move(Point point, int width, int height);
        public abstract Direction getOpposite();
        public abstract Direction left();
        public abstract Direction right();
    }

    public interface MoveAction {
        Direction move(int event, Direction currentDirection);
    }


}
