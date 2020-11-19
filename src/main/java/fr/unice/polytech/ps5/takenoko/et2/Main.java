package fr.unice.polytech.ps5.takenoko.et2;

import fr.unice.polytech.ps5.takenoko.et2.board.LandTile;
import fr.unice.polytech.ps5.takenoko.et2.decision.DecisionMakerBuilder;
import fr.unice.polytech.ps5.takenoko.et2.decision.bots.MinMaxBot;
import fr.unice.polytech.ps5.takenoko.et2.decision.bots.RandomBot;
import fr.unice.polytech.ps5.takenoko.et2.objective.PlotObjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Main
{
    public static void main(String... args) throws Exception
    {
        // console log format
        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tF %1$tT %4$s %3$s : %5$s%6$s%n");

        // only show warnings
        Arrays.stream(LogManager.getLogManager().getLogger("").getHandlers())
            .forEach(h -> h.setLevel(Level.WARNING));

        var land = new ArrayList<LandTile>();
        for (var i = 0; i < 11; i++)
        {
            land.add(new LandTile(Color.GREEN));
        }
        for (var i = 0; i < 9; i++)
        {
            land.add(new LandTile(Color.YELLOW));
        }
        for (var i = 0; i < 7; i++)
        {
            land.add(new LandTile(Color.PINK));
        }
        var objectives = List.of(
            new PlotObjective(
                2, List.of(Color.GREEN, Color.GREEN, Color.GREEN), List.of(0, 2)
            ),
            new PlotObjective(
                2, List.of(Color.GREEN, Color.GREEN, Color.GREEN), List.of(5, 0)
            ),
            new PlotObjective(
                2, List.of(Color.GREEN, Color.GREEN, Color.GREEN), List.of(5, 5)
            ),
            new PlotObjective(
                3, List.of(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN), List.of(0, 2, 3)
            ),
            new PlotObjective(
                3, List.of(Color.GREEN, Color.GREEN, Color.YELLOW, Color.YELLOW), List.of(2, 3, 5)
            ),
            new PlotObjective(
                3, List.of(Color.YELLOW, Color.YELLOW, Color.YELLOW), List.of(0, 2)
            ),
            new PlotObjective(
                3, List.of(Color.YELLOW, Color.YELLOW, Color.YELLOW), List.of(5, 0)
            ),
            new PlotObjective(
                3, List.of(Color.YELLOW, Color.YELLOW, Color.YELLOW), List.of(5, 5)
            ),
            new PlotObjective(
                4, List.of(Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW), List.of(0, 2, 3)
            ),
            new PlotObjective(
                4, List.of(Color.GREEN, Color.GREEN, Color.PINK, Color.PINK), List.of(2, 3, 5)
            ),
            new PlotObjective(
                4, List.of(Color.PINK, Color.PINK, Color.PINK), List.of(0, 2)
            ),
            new PlotObjective(
                4, List.of(Color.PINK, Color.PINK, Color.PINK), List.of(5, 0)
            ),
            new PlotObjective(
                4, List.of(Color.PINK, Color.PINK, Color.PINK), List.of(5, 5)
            ),
            new PlotObjective(
                5, List.of(Color.PINK, Color.PINK, Color.PINK, Color.PINK), List.of(0, 2, 3)
            ),
            new PlotObjective(
                5, List.of(Color.PINK, Color.PINK, Color.YELLOW, Color.YELLOW), List.of(2, 3, 5)
            )
        );
        var players = List.<DecisionMakerBuilder>of(
            MinMaxBot::new,
            //MinMaxBot::new
            RandomBot::new
            //RandomBot::new
        );
        var freq = new int[players.size()];
        final var N = 1000;
        var Nempty = 0;
        for (var i = 0; i < N; i++)
        {
            if (i % (N / 10) == 0)
            {
                System.out.println(i);
            }
            var game = new Game(objectives, land);
            for (DecisionMakerBuilder player : players)
            {
                game.addPlayer(player);
            }
            var res = game.gameProcessing();
            if (res.isEmpty())
            {
                Nempty++;
            }
            else
            {
                for (Integer re : res)
                {
                    freq[re]++;
                }
            }
        }
        System.out.println(Arrays.toString(Arrays.stream(freq).asDoubleStream().map(d -> d / N).toArray()));
        System.out.printf("%.2f%% d'impasses%n", Nempty * 100.0 / N);
    }
}
