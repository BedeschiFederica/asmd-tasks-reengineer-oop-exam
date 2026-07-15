package oop2023.a01c.sol2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    private final Logger logger;

    public GUI(final int size) {
        this.logic = new LogicImpl(size);
        this.logger = new ConsoleLogger();
        this.init(size);
    }

    GUI(final int size, final Logic logic, final Logger logger) {
        this.logic = logic;
        this.logger = logger;
        this.init(size);
    }

    private void init(final int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.logger.cellClicked(this.cells.get(jb));
            final Optional<Integer> edgeNumber = this.logic.hit(this.cells.get(jb));
            for (var entry: this.cells.entrySet()){
                entry.getKey().setText(
                    this.logic
                        .getMark(entry.getValue())
                        .map(String::valueOf)
                        .orElse(" "));
            }
            if (this.logic.isOver()){
                this.logger.gameEnded();
                this.dispose();
            } else {
                edgeNumber.ifPresentOrElse(this.logger::edgeMarked, this.logger::rectangleUpdated);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final JButton jb = new JButton();
                this.cells.put(jb, new Position(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
        this.logger.gameStarted();
    }

    Map<Position, JButton> getGrid() {
        return this.cells.entrySet().stream()
                .collect(HashMap::new, (grid,entry) -> grid.put(entry.getValue(), entry.getKey()), HashMap::putAll);
    }
}
