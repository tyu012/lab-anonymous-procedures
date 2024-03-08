package labs.lambdas;

import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * JTLambdaExperiments.java
 *
 * A variety of experiments for working with lambdas, based on the Java Tutorial on lambda
 * expressions, available at
 * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
 *
 * @author Samuel A. Rebelsky
 * @author William Pitchford
 * @author Tim Yu
 */
public class JTLambdaExperiments {

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run our experiments.
   */
  public static void main(String[] args) {
    List<Person> persons = Person.createRoster();
    PrintWriter pen = new PrintWriter(System.out, true);
    printPersonsOlderThan(persons, 0);
    pen.println("");
    printPersonsWithinAgeRange(persons, 30, 40);
    pen.println("");
    printPersons(persons, new CheckPersonEligibleForSelectiveService());
    pen.println("");
    printPersons(persons, new CheckPerson() {
      public boolean test(Person p) {
        return p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25;
      }
    });
    pen.println("");
    printPersons(persons,
        (Person p) -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25);
    pen.println("");
    printPersonsWithPredicate(persons,
        p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25);
    pen.println("");
    processPersons(persons,
        p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25,
        p -> p.printPerson());
    pen.println("");
    processPersonsWithFunction(persons,
        p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25,
        p -> p.getEmailAddress(), email -> System.out.println(email));
    pen.println("");
    processElements(persons,
        p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25,
        p -> p.getEmailAddress(), email -> System.out.println(email));
    pen.println("");
    persons
        .stream()
        .filter(p -> p.getGender() == Person.Sex.MALE && p.getAge() >= 18 && p.getAge() <= 25)
        .map(p -> p.getEmailAddress())
        .forEach(email -> System.out.println(email));
    
  } // main(String[])

  // +--------------------------------+------------------------------
  // | Methods from the Java Tutorial |
  // +--------------------------------+

  public static void printPersonsOlderThan(List<Person> roster, int age) {
    for (Person p : roster) {
      if (p.getAge() >= age) {
        p.printPerson();
      }
    }
  }

  public static void printPersonsWithinAgeRange(List<Person> roster, int low, int high) {
    for (Person p : roster) {
      if (low <= p.getAge() && p.getAge() < high) {
        p.printPerson();
      }
    }
  }

  public static void printPersons(List<Person> roster, CheckPerson tester) {
    for (Person p : roster) {
      if (tester.test(p)) {
        p.printPerson();
      }
    }
  }

  public static void printPersonsWithPredicate(List<Person> roster, Predicate<Person> tester) {
    for (Person p : roster) {
      if (tester.test(p)) {
        p.printPerson();
      }
    }
  }

  public static void processPersons(List<Person> roster, Predicate<Person> tester,
      Consumer<Person> block) {
    for (Person p : roster) {
      if (tester.test(p)) {
        block.accept(p);
      }
    }
  }

  public static void processPersonsWithFunction(List<Person> roster, Predicate<Person> tester,
      Function<Person, String> mapper, Consumer<String> block) {
    for (Person p : roster) {
      if (tester.test(p)) {
        String data = mapper.apply(p);
        block.accept(data);
      }
    }
  }

  public static <X, Y> void processElements(Iterable<X> source, Predicate<X> tester,
      Function<X, Y> mapper, Consumer<Y> block) {
    for (X p : source) {
      if (tester.test(p)) {
        Y data = mapper.apply(p);
        block.accept(data);
      }
    }
  }
} // class JTLambdaExperiments
