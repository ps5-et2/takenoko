package fr.unice.polytech.ps5.takenoko.et2.commandline;

import fr.unice.polytech.ps5.takenoko.et2.commandline.exceptions.InvalidBotParameterCountException;
import fr.unice.polytech.ps5.takenoko.et2.commandline.exceptions.InvalidBotParameterException;
import fr.unice.polytech.ps5.takenoko.et2.commandline.exceptions.InvalidBotTypeException;
import fr.unice.polytech.ps5.takenoko.et2.commandline.exceptions.InvalidBotTypeSyntaxException;
import fr.unice.polytech.ps5.takenoko.et2.decision.DecisionMaker;
import fr.unice.polytech.ps5.takenoko.et2.decision.DecisionMakerBuilder;
import org.reflections.Reflections;
import picocli.CommandLine;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles the command-line parsing and completion for bot names and parameters
 */
public class DecisionMakerHandler extends ArrayList<String> implements CommandLine.ITypeConverter<DecisionMakerBuilder>
{
    private static final Map<String, Method> types = new HashMap<>();
    private static final List<String> botDisplayNames = new ArrayList<>();

    static
    {
        var bots = new Reflections("fr.unice.polytech.ps5.takenoko.et2")
            .getTypesAnnotatedWith(Bot.class);
        for (Class<?> bot : bots)
        {
            //noinspection unchecked
            registerBot(bot.getAnnotation(Bot.class).key(), (Class<? extends DecisionMaker>) bot);
        }
    }

    DecisionMakerHandler()
    {
        super(botDisplayNames);
    }

    private static void registerBot(String name, Class<? extends DecisionMaker> clazz)
    {
        var meth = getBuilder(clazz);
        types.put(name, meth);
        if (meth.getParameterCount() != 0)
        {
            name += "(" + Arrays.stream(meth.getParameters()).map(Parameter::getName).collect(Collectors.joining(", ")) + ")";
        }
        botDisplayNames.add(name);
    }

    private static final Pattern namePattern = Pattern.compile("^(\\w+)(?:\\(([^,]+(?:,[^,]+)*)?\\))?$");

    private static Method getBuilder(Class<? extends DecisionMaker> cl)
    {
        return Arrays.stream(cl.getMethods()).filter(m -> m.getName().equals("getBuilder")).findFirst().get();
    }
    
    @Override
    public DecisionMakerBuilder convert(String s) throws IllegalArgumentException
    {
        s = s.replace(" ", "");
        var matcher = namePattern.matcher(s);
        if (!matcher.find())
        {
            throw new InvalidBotTypeSyntaxException();
        }
        var name = matcher.group(1);
        var meth = types.getOrDefault(name, null);
        if (meth == null)
        {
            throw new InvalidBotTypeException(name);
        }
        var params = meth.getParameters();
        try
        {
            var paramsGroup = matcher.group(2);
            String[] groupstr;
            if (paramsGroup == null)
            {
                groupstr = new String[0];
            }
            else
            {
                groupstr = paramsGroup.split(",");
            }
            if (groupstr.length != params.length)
            {
                throw new InvalidBotParameterCountException(params.length, groupstr.length);
            }
            return (DecisionMakerBuilder) meth.invoke(null, IntStream.range(0, params.length).mapToObj(i ->
            {
                try
                {
                    return Integer.parseInt(groupstr[i]);
                }
                catch (Exception e)
                {
                    throw new InvalidBotParameterException(e);
                }
            }).toArray());
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}