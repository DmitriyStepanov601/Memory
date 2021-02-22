import javax.swing.*;
import java.awt.*;

public class Memory extends JFrame {
    public CardBoard Cards;
    public static Image windowIcon;
    private JFrame moreInform;
    private String nameFolder = "/starWars/";
    private String hiddenCell = "/starWars/33.png";

    private static final String TITLE_HELP = "Help";
    private static final String TXTHELP = "<html><center><H2>Помощь</H2></center><br><center>The game starts with a " +
            "demonstration of a set of cards. They lie face up (respectively, the image is down). " +
            "When you click on any one, the image opens for a few seconds.< br> The player's task is to find all the " +
            "cards with the same pictures. If, after opening the first card, you turn over the second one and the " +
            "pictures match, both cards remain open. If they don't match,the cards are closed again. Task — " +
            "open all.</center></html>";
    private static final String TITLE_ABOUT = "About";
    private static final String TXTABOUT = "<html><center><H2>Об игре</H2></center><br><center>Memory is one of the " +
            "most common board games for memory development. The origins of the game lead us to the Land of the rising " +
            "sun in the Heian period (794-1185 years). The famous entertainment of the Japanese nobility is awase (awase), in the " +
            "translation of connection. Among these amusements there was also kai-awase (kai-awase) – a game of shells. 360 pairs of " +
            "shells (from 2.5 to 3 inches) make up the complete set of kai-awase. Outside, the shell is left with its natural " +
            "the view, and the inside is cleaned and painted. Each pair of shells shares a common character. Inside can be " +
            "drawn nature, theatrical clothing, literary characters, artistic images, poetic forms. " +
            "Shells were placed on a special tablecloth-stand, which is called kaioke. The idea of the game is that the " +
            "is won by the participant who collects the greatest match of pairs according to a certain instruction.<br>" +
            "</center></html>";

    /**
     * Constructor - creating a new memory game
     * @see Memory#Memory()
     */
    public Memory() {
        setTitle("Memory Cards");
        setSize(1220, 725);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        windowIcon = CardBoard.loadImage("/memory.png");
        setIconImage(windowIcon);
        Cards = new CardBoard(4, 8, nameFolder, hiddenCell);
        add(Cards, BorderLayout.CENTER);

        JMenuBar gameMenu = new JMenuBar();
        JMenu game = new JMenu("Game");
        gameMenu.add(game);

        JMenuItem newGame = new JMenuItem("New Game");
        KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
        newGame.setAccelerator(ctrlNKeyStroke);
        game.add(newGame);
        newGame.addActionListener(e -> Cards.start());

        JMenuItem replay = new JMenuItem("Replay");
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        replay.setAccelerator(ctrlRKeyStroke);
        game.add(replay);
        replay.addActionListener(e -> Cards.restart());

        JMenuItem helpGame = new JMenuItem("Help");
        KeyStroke ctrlHKeyStroke = KeyStroke.getKeyStroke("control H");
        helpGame.setAccelerator(ctrlHKeyStroke);
        game.add(helpGame);
        helpGame.addActionListener(e -> {
            informGame(TITLE_HELP, TXTHELP, 350, 450);
            addPicture(moreInform);
        });

        JMenuItem aboutGame = new JMenuItem("About");
        KeyStroke ctrlAKeyStroke = KeyStroke.getKeyStroke("control A");
        aboutGame.setAccelerator(ctrlAKeyStroke);
        game.add(aboutGame);
        game.addSeparator();
        aboutGame.addActionListener(e -> informGame(TITLE_ABOUT, TXTABOUT, 350, 420));

        JMenuItem exitGame = new JMenuItem("Exit");
        KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
        exitGame.setAccelerator(ctrlQKeyStroke);
        game.add(exitGame);
        exitGame.addActionListener(e -> System.exit(0));
        setJMenuBar(gameMenu);
    }

    // Creating a form template for help and about the game
    private void informGame(String name, String text, int width, int height){
        moreInform = new JFrame(name);
        JLabel txtMessage = new JLabel(text);
        txtMessage.setHorizontalAlignment(SwingConstants.CENTER);
        txtMessage.setVerticalAlignment(SwingConstants.TOP);
        txtMessage.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        moreInform.setIconImage(windowIcon);
        moreInform.setSize(width, height);
        moreInform.setLocationRelativeTo(null);
        moreInform.setVisible(true);
        moreInform.add(txtMessage);
    }

    private void addPicture(JFrame MoreInform){
        JLabel jlImage = new JLabel(new ImageIcon(CardBoard.loadImage("/cards.jpg")));
        MoreInform.add(jlImage);
        MoreInform.setLayout(new GridLayout(2, 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Memory::new);
    }
}
