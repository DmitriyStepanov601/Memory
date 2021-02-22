import javax.swing.*;
import java.awt.*;

public class Memory extends JFrame {
    public CardBoard Cards;
    public static Image windowIcon;
    private JFrame moreInform;
    private String nameFolder = "/starWars/";
    private String hiddenCell = "/starWars/33.png";

    private static final String TITLE_HELP = "Help";
    private static final String TXTHELP = "<html><center><H2>Помощь</H2></center><br><center>Игра начинается с " +
            "демонстрации набора карточек. Они лежат «рубашкой» вверх (соответственно, изображением вниз). " +
            "Когда вы кликнете по любой,на несколько секунд открывается изображение.<br> Задача игрока — найти все " +
            "карточки с одинаковыми картинками. Если после открытия первой карты вы переворачиваете вторую и картинки " +
            "совпадают, обе карточки остаются открытыми. Если не совпадают,карточки снова закрываются. Задача — " +
            "открыть все.</center></html>";
    private static final String TITLE_ABOUT = "About";
    private static final String TXTABOUT = "<html><center><H2>Об игре</H2></center><br><center>Мемори - одна из " +
            "самых распространённых настольных игр на развитие памяти. Истоки игры ведут нас в Страну восходящего " +
            "солнца в период Хэйан (794-1185 годы). Именитое развлечение японской знати — awase (авасе), в " +
            "переводе «соединение». Среди этих забав существовало и kai-awase (каи-авасе) – игра в ракушки. 360 пар " +
            "ракушек (от 2,5 до 3 дюймов) составляет полный набор каи-авасе. Снаружи ракушке оставляют её природный " +
            "вид, а внутри вычищают и окрашивают. Каждую пару ракушек объединяет общий характер. Внутри могут быть " +
            "нарисованы природа, театральные одеяния, литературные герои, художественные образы, поэтические формы. " +
            "Ракушки ставили на специальную скатерть-подставку, которая называется kaioke. Идея игры такова, что " +
            "выигрывает тот участник, который собирает по определенной инструкции наибольшее соответствие пар.<br>" +
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