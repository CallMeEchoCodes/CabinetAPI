package dev.callmeecho.cabinetapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A utility class attempting to recreate Java 21's pattern matching switch syntax.
 * Note that this isn't actually a switch and therefor doesn't have the same performance benefit.
 *
 * <blockquote><pre>
 *     {@code patternSwitch(anObject,}
 *         {@code     ccase(Byte.class,    b -> System.out.println("Byte")),}
 *         {@code     ccase(Short.class,   s -> System.out.println("Short")),}
 *         {@code     ccase(Integer.class, i -> System.out.println("Integer")),}
 *         {@code     ccase(Long.class,    l -> System.out.println("Long")),}
 *         {@code     ccase(Float.class,   f -> System.out.println("Float")),}
 *         {@code     ccase(Double.class,  d -> System.out.println("Double"))}
 *     {@code );}
 * </pre></blockquote>
 * @see <a href="https://openjdk.org/jeps/441">JEP 441: Pattern Matching for switch</a>
 */
public final class PatternSwitch implements Consumer<Object> {
    /**
     * Runs a pattern switch on the provided object with the provided cases.
     *
     * @param object The object to test against the cases
     * @param cases The cases to use in the pattern switch
     */
    public static void patternSwitch(Object object, Case<?>... cases) {
        if (cases == null) { return; }

        for (Case<?> c : cases) {
            if (c.test(object)) {
                c.accept(object);
                return;
            }
        }
    }

    /**
     * Creates a new case for the pattern switch.
     *
     * @param type The type to test against
     * @param consumer The consumer to run if the type matches
     * @param <T> The type of the case
     * @return A new case for the pattern switch
     */
    public static <T> Case<T> ccase(Class<T> type, Consumer<? super T> consumer) {
        return new Case<>(type, consumer);
    }

    private final List<Case<?>> caseList;

    /**
     * Creates a new pattern switch with the provided cases.
     * You probably want to use {@link #patternSwitch(Object, Case[])} instead for nicer syntax.
     * This constructor is here just in case you want to use the object multiple times.
     *
     * @param cases The cases to use in the pattern switch
     */
    public PatternSwitch(Case<?>... cases) {
        if (cases == null) {
            this.caseList = Collections.emptyList();
            return;
        }

        List<Case<?>> newCaseList = new ArrayList<>(cases.length);
        for (Case<?> c : cases) { newCaseList.add(Objects.requireNonNull(c, "case")); }
        this.caseList = Collections.unmodifiableList(newCaseList);
    }

    /**
     * Accepts an object and runs the appropriate case.
     * You probably want to use {@link #patternSwitch(Object, Case[])} instead for nicer syntax.
     *
     * @param object The object to test against the cases
     */
    @Override
    public void accept(Object object) {
        for (Case<?> c : caseList) {
            if (c.test(object)) {
                c.accept(object);
                return;
            }
        }
    }

    /**
     * A case for the pattern switch.
     *
     * @param <T> The type of the case
     */
    public static final class Case<T> implements Predicate<Object>, Consumer<Object> {
        private final Class<T> type;
        private final Consumer<? super T> consumer;

        public Case(Class<T> type, Consumer<? super T> consumer) {
            this.type = Objects.requireNonNull(type, "type");
            this.consumer = Objects.requireNonNull(consumer, "consumer");
        }

        @Override
        public void accept(Object object) { consumer.accept(type.cast(object)); }
        @Override
        public boolean test(Object object) { return type.isInstance(object); }
    }
}
