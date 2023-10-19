package plugin.panhabu.PrisonFunctions;

import java.util.ArrayList;
import java.util.List;

public class PrisonCommand {

   public static List<String> numberArgs() {
      List<String> numbers = new ArrayList<>();
      for (int i = 1; i <= 100; i++) {
         numbers.add(Integer.toString(i));
      }
      return numbers;
   }

   public static List<String> stringArgs() {
      List<String> strings = new ArrayList<>();
      strings.add("seconds");
      strings.add("minutes");
      strings.add("hours");
      strings.add("days");
      return strings;
   }

   public static int cooldown(String args1, String args2) {
      int number = 3600;
      try {
         number = Integer.parseInt(args1);
      } catch (NumberFormatException exception) {
         return number;
      }

      switch (args2.toLowerCase()) {
         case "seconds":
            return number;
         case "minutes":
            return number * 60;
         case "hours":
            return number * 60 * 60;
         case "days":
            return number * 60 * 60 * 24;
         default:
            return 3600;
      }
   }

}
