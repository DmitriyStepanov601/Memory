import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Class describing the board
 * @author Dmitriy Stepanov
 */
public class CardBoard extends JPanel {
    private static final ArrayList<Card> chosenCards = new ArrayList<>();
    private static int numOfMatchedPairs = 0;
    private static int numOfPoints = 0;
    private static int selectedCards = 0;

    private final Card[][] Cards;
    private String[] mCardStorage = initCardStorage();
    private final Card[] mCardChecker = new Card[2];

    private final int rowGrid;
    private final int colGrid;
    private final int allCards;
    private final int numPair;
    private final String NameFolder;
    private final String HiddenCell;
    private static BufferedImage def;

    /**
     * Constructor - creating a new board with cards
     * @param rowGrid - number of rows
     * @param colGrid - number of columns
     * @param NameFolder - the name of the folder where the images are located
     * @param HiddenCell - reverse side of the card
     * @see CardBoard#CardBoard(int,int,String,String)
     */
    public CardBoard(int rowGrid, int colGrid, String NameFolder, String HiddenCell) {
        this.rowGrid = rowGrid;
        this.colGrid = colGrid;
        this.NameFolder = NameFolder;
        this.HiddenCell = HiddenCell;

        setPreferredSize(new Dimension(1000, 660));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new GridLayout(rowGrid, colGrid, 5,5));
        allCards = rowGrid * colGrid;
        numPair = allCards / 2;
        Cards = new Card[rowGrid][colGrid];
        def = loadImage(HiddenCell);

        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                Cards[row][column] = new Card();
                Cards[row][column].setPreferredSize(new Dimension(162,162));
                Cards[row][column].setIcon(new ImageIcon(def));
                Cards[row][column].addActionListener(e -> {
                    if (!(e.getSource() instanceof Card)) return;
                    if (!isCheckRepeatChoice((Card) e.getSource())) return;
                    ++selectedCards;
                    if (selectedCards <= 2) {
                        Point gridLoc = getCardLocation((Card) e.getSource());
                        setCardToVisible(gridLoc.x, gridLoc.y);
                        mCardChecker[selectedCards - 1] = getCardAtLocation(gridLoc);
                        addChosenCard(getCardAtLocation(gridLoc));
                    }
                    if (selectedCards == 2) {
                        if (!matchCardsPosition(mCardChecker[0].getLocation(),
                                mCardChecker[1].getLocation())) {
                            setSelectedCards(mCardChecker[0], mCardChecker[1]);
                        } else {
                            --selectedCards;
                        }
                    }
                });
                add(Cards[row][column]);
            }
        }
        start();
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Memory.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static URL loadFileImage(String path) {
        return CardBoard.class.getResource(path);
    }

    public void start() {
        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                if (Cards[row][column].isMatched()) {
                    Cards[row][column].setMatched(false);
                }
            }
        }

        numOfMatchedPairs = 0;
        numOfPoints = 0;
        mCardStorage = initCardStorage();
        loadCardImages();
    }

    public void restart() {
        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                if (Cards[row][column].isMatched()) {
                    Cards[row][column].setMatched(false);
                }
            }
        }

        numOfMatchedPairs = 0;
        numOfPoints = 0;
        loadCardImages();
    }

    private String[] initCardStorage() {
        String[] cardStorage = new String[allCards];
        String[] firstPair = new String[numPair];
        String[] secondPair = new String[numPair];
        int difference = allCards - numPair;
        ArrayList<String> randomCards = new ArrayList<>();

        for (int i = 0; i < numPair; i++) {
            while (true) {
                Random random = new Random();
                int aNumber = (1 + random.nextInt(allCards));
                String next = Integer.toString(aNumber);
                if (!randomCards.contains(next)) {
                    randomCards.add(next);
                    firstPair[i] = randomCards.get(i);
                    break;
                }
            }
        }

        System.arraycopy(firstPair, 0, cardStorage, 0, numPair);
        Collections.shuffle(Arrays.asList(firstPair));
        System.arraycopy(firstPair, 0, secondPair, 0, numPair);
        System.arraycopy(secondPair, 0, cardStorage, numPair, difference);
        return cardStorage;
    }

    private void loadCardImages() {
        ImageIcon anImage;
        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                URL file = loadFileImage(NameFolder
                        + mCardStorage[column + (colGrid * row)] + ".png");
                if (file == null) {
                    System.err.println("File can't be found!");
                    System.exit(-1);
                }

                anImage = new ImageIcon(file);
                Cards[row][column].setIcon(anImage);

                if(Cards[row][column].isEnabled()){
                    Cards[row][column].setIcon(new ImageIcon(def));
                }
            }
        }
    }

    private void addChosenCard(Card aCard) {
        if (aCard != null) {
            if (!chosenCards.contains(aCard)) {
                chosenCards.add(aCard);
            }
        }
    }

    private boolean isCheckRepeatChoice(Card aCard) {
        if (aCard == null) {
            System.err.println("The card was selected!");
            return false;
        }
        return !aCard.isMatched();
    }

    private Point getCardLocation(Card aCard) {
        if (aCard == null) {
            System.err.println("It isn't possible to return the location of the card on the Board!");
            return null;
        }

        Point p = new Point();
        for (int column = 0; column < rowGrid; column++) {
            for (int row = 0; row < colGrid; row++) {
                if (Cards[column][row] == aCard) {
                    p.setLocation(column, row);
                    return p;
                }
            }
        }
        return null;
    }

    private void setCardToVisible(int x, int y) {
        Cards[x][y].setSelected(true);
        showCardImages();
    }

    // the return of the location of the card on the Board
    private Card getCardAtLocation(Point point) {
        if (point == null) {
            System.err.println("It isn't possible to get the location of the card on the Board!");
            return null;
        }
        return Cards[point.x][point.y];
    }

    private void showImage(int x, int y) {
        URL file = loadFileImage(NameFolder
                + mCardStorage[y + (colGrid * x)] + ".png");

        if (file == null) {
            System.err.println("File can't be found!");
            System.exit(-1);
        }

        ImageIcon anImage = new ImageIcon(file);
        Cards[x][y].setIcon(anImage);
    }

    private void showCardImages() {
        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                if (!Cards[row][column].isSelected()) {          // Is the card selected?
                    if (!Cards[row][column].isMatched()) {       // does not match - we turn it over
                        Cards[row][column].setIcon(new ImageIcon(loadImage(HiddenCell)));
                    }
                } else {                                        // The card was not selected
                    showImage(row, column);
                    String number = mCardStorage[column + (colGrid * row)];
                    int parsedNumber = Integer.parseInt(number);
                    Cards[row][column].setNumberCard(parsedNumber);
                }
            }
        }
    }

    private boolean matchCardsPosition(Point firstCard, Point secondCard) {
        if (firstCard == null || secondCard == null) {
            if (secondCard == firstCard) {
                return true;
            }
            if (firstCard == null) {
                System.err.println("The position of the card on the Board doesn't match!");
            }
            if (secondCard == null) {
                System.err.println("The position of the card on the Board doesn't match!");
            }
            return false;
        }
        return firstCard.equals(secondCard);
    }

    public boolean isSolved() {
        for (int row = 0; row < rowGrid; row++) {
            for (int column = 0; column < colGrid; column++) {
                if (!Cards[row][column].isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void Message() {
        ImageIcon win = new ImageIcon(loadImage("/win.png"));
        Timer timer = new Timer(2000, e -> {
            if (isSolved()) {
                JOptionPane.showMessageDialog(null,
                        "Congratulations!\n You found all the pairs of cards!\n Your results:\n" +
                                "Total points in the game 160\n Points: " + numOfPoints, "Victory",
                        JOptionPane.INFORMATION_MESSAGE, win);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void setSelectedCards(Card firstCard, Card secondCard) {
        if (firstCard == null || secondCard == null) {
            if (firstCard == null) {
                System.err.println("The position of the card on the Board doesn't match!");
            }
            if (secondCard == null) {
                System.err.println("The position of the card on the Board doesn't match!");
            }
            return;
        }
        if (firstCard.sameNumberCard(secondCard)) {
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            firstCard.setSelected(false);
            secondCard.setSelected(false);

            showImage(Objects.requireNonNull(getCardLocation(secondCard)).x,
                    Objects.requireNonNull(getCardLocation(secondCard)).y);
            numOfMatchedPairs++;
            numOfPoints += 10;

            if(isSolved()){
                Message();
            }
        } else {
            firstCard.setMatched(false);
            secondCard.setMatched(false);
            firstCard.setSelected(false);
            secondCard.setSelected(false);

            showImage(Objects.requireNonNull(getCardLocation(secondCard)).x,
                    Objects.requireNonNull(getCardLocation(secondCard)).y);
            numOfPoints--;
        }
        selectedCards = 0;
    }
}