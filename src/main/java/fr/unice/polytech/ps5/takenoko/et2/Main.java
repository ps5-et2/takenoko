package fr.unice.polytech.ps5.takenoko.et2;

import fr.unice.polytech.ps5.takenoko.et2.decision.DecisionMakerBuilder;
import fr.unice.polytech.ps5.takenoko.et2.decision.bots.MinMaxBot;
import fr.unice.polytech.ps5.takenoko.et2.decision.bots.RandomBot;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main
{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    public static void main(String... args)
    {
        // console log format
        System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tF %1$tT %4$s %3$s : %5$s%6$s%n");

        // only show warnings
        var level = Level.FINEST;
        var root = LogManager.getLogManager().getLogger("");
        root.setLevel(level);
        Arrays.stream(root.getHandlers()).forEach(h -> h.setLevel(level));

        var players = List.<DecisionMakerBuilder>of(
            //MinMaxBot::new,
            //MinMaxBot.getBuilder(2),
            //MinMaxBot.getBuilder(2),
            //MinMaxBot.getBuilder(2),
            //MinMaxBot.getBuilder(2),
            MinMaxBot.getBuilder(1),
            //MinMaxBot.getBuilder(1)
            //RandomBot::new,
            //RandomBot::new
            //RandomBot::new,
            //RandomBot::new,
            RandomBot::new
        );
        var freq = players.stream().map(p -> new AtomicInteger()).toArray(AtomicInteger[]::new);
        final var N = 1;
        AtomicInteger Nempty = new AtomicInteger();
        AtomicInteger Nlimit = new AtomicInteger();
        var start = Instant.now();
        IntStream.range(0, N).parallel().mapToObj(i ->
        {
            try
            {
                //var game = GameData.getStandardGame();
                var game = new Game(GameData.getStandardObjectives(), GameData.getLandTiles(11, 9, 7));
                for (DecisionMakerBuilder player : players)
                {
                    game.addPlayer(player);
                }
                LOGGER.info("start " + i);
                var res = game.gameProcessing(true);
                LOGGER.info("end " + i);
                return res;
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }).forEach(res ->
        {
            if (res == null)
            {
                Nempty.getAndIncrement();
            }
            else if (res.isEmpty())
            {
                Nlimit.getAndIncrement();
            }
            else
            {
                for (Integer re : res)
                {
                    freq[re].getAndIncrement();
                }
            }
        });

        var duration = Duration.between(start, Instant.now());
        System.out.printf("Total time elapsed: %d.%03ds (%.2f games/sec)%n",
            duration.getSeconds(),
            duration.getNano() / 1000000,
            N * 1000000000d / duration.toNanos());
        System.out.println("Percentage of games");
        System.out.println("- won per player: " +
            Arrays.stream(freq).mapToInt(AtomicInteger::get).mapToObj(d -> String.format("%.2f%%", (double) d * 100 / N)).collect(Collectors.joining(" ; ")));
        System.out.printf("- that reached max turn count limit: %.2f%%%n", Nlimit.get() * 100.0 / N);
        System.out.printf("- that were deadlocked: %.2f%%%n", Nempty.get() * 100.0 / N);
    }
}
