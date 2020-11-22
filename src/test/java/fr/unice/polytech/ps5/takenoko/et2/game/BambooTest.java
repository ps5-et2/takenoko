package fr.unice.polytech.ps5.takenoko.et2.game;

import fr.unice.polytech.ps5.takenoko.et2.*;
import fr.unice.polytech.ps5.takenoko.et2.board.Board;
import fr.unice.polytech.ps5.takenoko.et2.board.LandTile;
import fr.unice.polytech.ps5.takenoko.et2.board.TilePosition;
import fr.unice.polytech.ps5.takenoko.et2.decision.DecisionMakerBuilder;
import fr.unice.polytech.ps5.takenoko.et2.decision.bots.RandomBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
class BambooTest
{

    Game game;
    Player p;
    Board board;

    @BeforeEach
    void init()
    {
        try
        {
            game = GameData.getStandardGame();
            DecisionMakerBuilder dm = RandomBot::new;
            p = game.addPlayer(dm);
            board = game.getBoard();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addBambooSectionToTileWithFirstIrrigationTest()
    {
        LandTile l1 = new LandTile(Color.GREEN);
        LandTile l2 = new LandTile(Color.YELLOW);
        LandTile l3 = new LandTile(Color.PINK);
        LandTile l4 = new LandTile(Color.PINK);
        LandTile l5 = new LandTile(Color.GREEN);
        LandTile l6 = new LandTile(Color.YELLOW);
        board.addTile(l1, new TilePosition(0, 1), game.getBambooReserve());
        board.addTile(l2, new TilePosition(1, 0), game.getBambooReserve());
        board.addTile(l3, new TilePosition(1, -1), game.getBambooReserve());
        board.addTile(l4, new TilePosition(0, -1), game.getBambooReserve());
        board.addTile(l5, new TilePosition(-1, 0), game.getBambooReserve());
        board.addTile(l6, new TilePosition(-1, 1), game.getBambooReserve());
        LandTile l7 = new LandTile(Color.GREEN);
        LandTile l8 = new LandTile(Color.YELLOW);
        LandTile l9 = new LandTile(Color.PINK);
        LandTile l10 = new LandTile(Color.PINK);
        LandTile l11 = new LandTile(Color.GREEN);
        LandTile l12 = new LandTile(Color.YELLOW);
        board.addTile(l7, new TilePosition(1, 1), game.getBambooReserve());
        board.addTile(l8, new TilePosition(2, -1), game.getBambooReserve());
        board.addTile(l9, new TilePosition(1, -2), game.getBambooReserve());
        board.addTile(l10, new TilePosition(-1, -1), game.getBambooReserve());
        board.addTile(l11, new TilePosition(-2, 1), game.getBambooReserve());
        board.addTile(l12, new TilePosition(-1, 2), game.getBambooReserve());

        assertEquals(1, l1.getBambooSize());
        assertEquals(0, l7.getBambooSize());
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        game.pickIrrigation(p);
        p.irrigateEdge(l1.getEdge(2));
        p.irrigateEdge(l1.getEdge(1));
        assertEquals(1, l1.getBambooSize());
        assertEquals(1, l7.getBambooSize());
        p.irrigateEdge(l2.getEdge(0));
        assertEquals(1, l7.getBambooSize());
        p.irrigateEdge(l2.getEdge(1));
        assertEquals(0, l8.getBambooSize());
        p.irrigateEdge(l8.getEdge(0));
        assertEquals(1, l8.getBambooSize());
        assertThrows(NullPointerException.class, () -> game.addBambooSectionToTile(null));
    }


    @Test
    void bambooReserveDecrementsWhenIrrigationPlacedTest()
    {
        LandTile l1 = new LandTile(Color.GREEN);
        LandTile l2 = new LandTile(Color.YELLOW);
        LandTile l3 = new LandTile(Color.PINK);
        LandTile l4 = new LandTile(Color.PINK);
        LandTile l5 = new LandTile(Color.GREEN);
        LandTile l6 = new LandTile(Color.YELLOW);
        board.addTile(l1, new TilePosition(0, 1), game.getBambooReserve());
        board.addTile(l2, new TilePosition(1, 0), game.getBambooReserve());
        board.addTile(l3, new TilePosition(1, -1), game.getBambooReserve());
        board.addTile(l4, new TilePosition(0, -1), game.getBambooReserve());
        board.addTile(l5, new TilePosition(-1, 0), game.getBambooReserve());
        board.addTile(l6, new TilePosition(-1, 1), game.getBambooReserve());
        LandTile l7 = new LandTile(Color.GREEN);
        LandTile l8 = new LandTile(Color.YELLOW);
        LandTile l9 = new LandTile(Color.PINK);
        LandTile l10 = new LandTile(Color.PINK);
        LandTile l11 = new LandTile(Color.GREEN);
        LandTile l12 = new LandTile(Color.YELLOW);
        board.addTile(l7, new TilePosition(1, 1), game.getBambooReserve());
        board.addTile(l8, new TilePosition(2, -1), game.getBambooReserve());
        board.addTile(l9, new TilePosition(1, -2), game.getBambooReserve());
        board.addTile(l10, new TilePosition(-1, -1), game.getBambooReserve());
        board.addTile(l11, new TilePosition(-2, 1), game.getBambooReserve());
        board.addTile(l12, new TilePosition(-1, 2), game.getBambooReserve());

        game.pickIrrigation(p);
        game.pickIrrigation(p);

        p.irrigateEdge(l6.getEdge(3));
        p.irrigateEdge(l6.getEdge(4));
        var actualBambooStock = game.getBambooReserve().stream().collect(Collectors.groupingBy(BambooSection::getColor));
        assertEquals(41, actualBambooStock.get(Color.GREEN).size());
        assertEquals(26, actualBambooStock.get(Color.PINK).size());
        assertEquals(34, actualBambooStock.get(Color.YELLOW).size());
        //TODO change expected values when real number of bambooSection implemented
    }

    @Test
    void bambooReserveDecreasesWhenAddBambooSectionAddedTest() throws Exception
    {
        LandTile l1 = new LandTile(Color.GREEN);
        LandTile l2 = new LandTile(Color.YELLOW);
        LandTile l3 = new LandTile(Color.PINK);
        LandTile l4 = new LandTile(Color.PINK);
        LandTile l5 = new LandTile(Color.GREEN);
        LandTile l6 = new LandTile(Color.YELLOW);
        board.addTile(l1, new TilePosition(0, 1), game.getBambooReserve());
        board.addTile(l2, new TilePosition(1, 0), game.getBambooReserve());
        board.addTile(l3, new TilePosition(1, -1), game.getBambooReserve());
        board.addTile(l4, new TilePosition(0, -1), game.getBambooReserve());
        board.addTile(l5, new TilePosition(-1, 0), game.getBambooReserve());
        board.addTile(l6, new TilePosition(-1, 1), game.getBambooReserve());
        LandTile l7 = new LandTile(Color.GREEN);
        LandTile l8 = new LandTile(Color.YELLOW);
        LandTile l9 = new LandTile(Color.PINK);
        LandTile l10 = new LandTile(Color.PINK);
        LandTile l11 = new LandTile(Color.GREEN);
        LandTile l12 = new LandTile(Color.YELLOW);
        board.addTile(l7, new TilePosition(1, 1), game.getBambooReserve());
        board.addTile(l8, new TilePosition(2, -1), game.getBambooReserve());
        board.addTile(l9, new TilePosition(1, -2), game.getBambooReserve());
        board.addTile(l10, new TilePosition(-1, -1), game.getBambooReserve());
        board.addTile(l11, new TilePosition(-2, 1), game.getBambooReserve());
        board.addTile(l12, new TilePosition(-1, 2), game.getBambooReserve());

        for (int i = 0; i < 3; i++)
        {
            game.addBambooSectionToTile(l1);
        }

        var actualBambooStock = game.getBambooReserve().stream().collect(Collectors.groupingBy(BambooSection::getColor));
        assertEquals(39, actualBambooStock.get(Color.GREEN).size());
        assertEquals(26, actualBambooStock.get(Color.PINK).size());
        assertEquals(34, actualBambooStock.get(Color.YELLOW).size());
        //TODO change expected values when real number of bambooSection implemented

        game.addBambooSectionToTile(l1);
        actualBambooStock = game.getBambooReserve().stream().collect(Collectors.groupingBy(BambooSection::getColor));
        assertEquals(39, actualBambooStock.get(Color.GREEN).size());
        assertEquals(26, actualBambooStock.get(Color.PINK).size());
        assertEquals(34, actualBambooStock.get(Color.YELLOW).size());
        //TODO change expected values when real number of bambooSection implemented

        game.addBambooSectionToTile(l8);
        actualBambooStock = game.getBambooReserve().stream().collect(Collectors.groupingBy(BambooSection::getColor));
        assertEquals(39, actualBambooStock.get(Color.GREEN).size());
        assertEquals(26, actualBambooStock.get(Color.PINK).size());
        assertEquals(34, actualBambooStock.get(Color.YELLOW).size());
        //TODO change expected values when real number of bambooSection implemented
    }

}