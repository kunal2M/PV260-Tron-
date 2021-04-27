package pv260.general;

import java.awt.*;

public interface Engine {

    void game_start();
    void game_update(long timePassed);
    void draw(Graphics2D g);
    void game_stop();

}
