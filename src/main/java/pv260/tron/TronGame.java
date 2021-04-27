  package pv260.tron;

import pv260.general.Core;
import pv260.general.Engine;
import pv260.tron.model.TronModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TronGame extends Core implements Engine {

    private TronModel model = new TronModel();

    @Override
    public void init() {
        super.init();
        model.getEventsObservable()
                .subscribe(new Observer<TronModel.Event>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(TronModel.Event event) {
                        if (event == TronModel.Event.GAME_ENDED) {
                            System.exit(0);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void game_update(long timePassed) {
        model.update(timePassed, sm.getWidth(), sm.getHeight());
    }

    @Override
    public void draw(Graphics2D g) {
        clearGraphics(g);
        model.getPlayers().forEach(player -> drawPath(g, player));
    }

    private void drawPath(Graphics2D g, Player player) {
        player.getPath().forEach(point -> {
            g.setColor(player.getColor());
            g.fillRect(point.x, point.y, 10, 10);
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        model.inputEvent(e.getKeyCode());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.inputEvent(e.getModifiersEx());
    }
}
