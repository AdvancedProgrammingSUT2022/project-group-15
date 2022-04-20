package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public String run() {
        String output;
        while (true) {
            output = checkCommand(scanner.nextLine());
            if (!output.equals("continue")) return output;
        }
    }

    protected abstract String checkCommand(String command);

    /**
     * check the command with a regex and return the output matcher
     *
     * @param command the command that we want to check
     * @param regex   the regex that we want to check command with
     * @return null if doesn't match and else a matcher
     * @author Parsa
     */
    protected Matcher getMatcher(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
